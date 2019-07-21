package com.example.lozik.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class VisitRequest {

    @NotBlank
    String factUuid;
}
