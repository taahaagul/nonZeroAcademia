package com.taahaagul.security.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taahaagul.security.entities.*;
import com.taahaagul.security.exceptions.UserNotFoundException;
import com.taahaagul.security.repository.NonDaysRepository;
import com.taahaagul.security.repository.VerificationTokenRepository;
import com.taahaagul.security.requests.LoginRequest;
import com.taahaagul.security.responses.AuthenticationResponse;
import com.taahaagul.security.requests.RegisterRequest;
import com.taahaagul.security.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.taahaagul.security.repository.TokenRepository;
import com.taahaagul.security.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final NonDaysRepository nonDaysRepository;

    public void register(RegisterRequest request) {

        Optional<User> existingUserName = userRepository.findByUserName(request.getUserName());
        if(existingUserName.isPresent())
            throw new UserNotFoundException("Username already is exist");

        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new UserNotFoundException("Email already exist!");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .memberSince(LocalDate.now())
                .gitHub("https://github.com/")
                .linkedin("https://www.linkedin.com/")
                .role(Role.ZERO)
                .thumbnailUrl("https://taahaagul.s3.amazonaws.com/c7185a1b-4f26-4ad4-b5e8-11fa411dccdb.jpeg")
                .enabled(false)
                .nonRank(0)
                .build();
        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .created(new Date())
                .build();

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new UserNotFoundException("Invalid Token")));
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User  user = userRepository.findByEmail(username)
                .orElseThrow(()-> new UserNotFoundException("User not found with name -" + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        saveNonDays(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveNonDays(User user) {
        LocalDate currentDate = LocalDate.now();
        Optional<NonDays> existNonDays= nonDaysRepository.findByUserAndLoginDate(user, currentDate);
        if(!existNonDays.isPresent()) {
            NonDays nonDays = NonDays.builder()
                    .loginDate(currentDate)
                    .user(user)
                    .build();
            nonDaysRepository.save(nonDays);
            user.incrementNonRank();
            userRepository.save(user);
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusMinutes(1);
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .expiry(expiration)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        /*
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
         */
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        tokenRepository.deleteAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public User getCurrentUser() {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UserNotFoundException("Current user is not found"));
        return user;
    }
}
