package com.example.lozik.payload.response;

import lombok.Data;

@Data
public class AnswerResponse {

    private Long id;
    private String text;
    private Boolean isCorrect;
}
