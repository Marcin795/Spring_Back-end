package com.example.lozik.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class QuestionRequest {

    @NotBlank
    private String text;

    @NotNull
    private List<AnswerRequest> answers;
}
