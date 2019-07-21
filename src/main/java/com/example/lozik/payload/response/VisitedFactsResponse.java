package com.example.lozik.payload.response;

import lombok.Data;

import java.util.Set;

@Data
public class VisitedFactsResponse {

    private Set<FactResponse> facts;
    private Integer visitedCount;
    private Long toVisitCount;
}
