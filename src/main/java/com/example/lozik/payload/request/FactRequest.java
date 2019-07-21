package com.example.lozik.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FactRequest {

    @NotBlank
    private String text;

    @NotBlank
    private String description;
}
