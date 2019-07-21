package com.example.lozik.payload.response;

import lombok.Data;

@Data
public class ChoiceResponse {

    private Boolean isCorrect;
    private QuestionResponse questionResponse;
    private AnswerResponse answerResponse;
}
