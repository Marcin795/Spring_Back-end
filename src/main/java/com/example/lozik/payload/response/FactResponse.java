package com.example.lozik.payload.response;

import lombok.Data;

@Data
public class FactResponse {

    private Long id;
    private String text;
    private String description;
    private String uuid;
}
