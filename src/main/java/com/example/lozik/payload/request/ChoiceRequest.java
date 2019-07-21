package com.example.lozik.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChoiceRequest {

    @NotNull
    private Long answerId;

    @NotNull
    private Long questionId;
}
