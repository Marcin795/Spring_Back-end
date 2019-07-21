package com.example.lozik.payload.response;

import com.example.lozik.entity.Fact;
import lombok.Data;

@Data
public class VisitedFactResponse {

    private Fact fact;
    private Integer visitedCount;
    private Long toVisitCount;
}
