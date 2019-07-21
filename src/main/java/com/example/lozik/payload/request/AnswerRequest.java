package com.example.lozik.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AnswerRequest {

    @NotBlank
    private String text;

    @NotBlank
    private Boolean isCorrect;
}
