package com.example.lozik.payload.response;

import com.example.lozik.entity.Question;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class QuestionResponse {

    private Long id;
    private String text;
    private List<AnswerResponse> answers;

    public static QuestionResponse createResponse(Question question) {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setId(question.getId());
        questionResponse.setText(question.getText());
        List<AnswerResponse> answers = question.getAnswers().stream().map(answer -> {
            AnswerResponse answerResponse = new AnswerResponse();
            answerResponse.setId(answer.getId());
            answerResponse.setText(answer.getText());
            answerResponse.setIsCorrect(answer.getIsCorrect());
            return answerResponse;
        }).collect(Collectors.toList());
        questionResponse.setAnswers(answers);
        return questionResponse;
    }
}
