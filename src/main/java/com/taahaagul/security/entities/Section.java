package com.taahaagul.security.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private Integer sectionSequence;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capsul_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Capsul capsul;
}
