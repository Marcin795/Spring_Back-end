package com.example.lozik.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiResponse {

    @NonNull
    private Boolean success;
    @NonNull
    private String message;
    private String accessToken;
}
