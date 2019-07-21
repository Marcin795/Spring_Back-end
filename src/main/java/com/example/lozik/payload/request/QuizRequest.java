package com.example.lozik.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QuizRequest {

    @NotNull
    String email;
}
