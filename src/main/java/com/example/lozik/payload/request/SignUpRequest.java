package com.example.lozik.payload.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {

    @NotBlank
    @Size(min=2, max=40)
    private String name;

    @NotBlank
    @Size(min=6, max=40)
    @Email
    private String email;

    @NotBlank
    @Size(min=8, max=20)
    private String password;
}
