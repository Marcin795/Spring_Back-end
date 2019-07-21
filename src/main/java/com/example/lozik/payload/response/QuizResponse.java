package com.example.lozik.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class QuizResponse {

    private String email;
    private String name;
    private Long score;
    private List<ChoiceResponse> choices;
}
