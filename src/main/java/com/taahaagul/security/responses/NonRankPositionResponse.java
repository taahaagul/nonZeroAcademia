package com.taahaagul.security.responses;

import lombok.Data;

@Data
public class NonRankPositionResponse {
    private Integer nonRank;
    private Integer position;

    public NonRankPositionResponse(Integer nonRank, Integer position) {
        this.nonRank = nonRank;
        this.position = position;
    }
}
