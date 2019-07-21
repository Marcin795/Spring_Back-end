package com.example.lozik.service;

import com.example.lozik.entity.Answer;
import com.example.lozik.entity.Question;
import com.example.lozik.exception.BadRequestException;
import com.example.lozik.payload.request.QuestionRequest;
import com.example.lozik.payload.response.PagedResponse;
import com.example.lozik.payload.response.QuestionResponse;
import com.example.lozik.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public PagedResponse<QuestionResponse> getAllQuestions(int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Page<Question> questions = questionRepository.findAll(pageable);

        if(questions.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), questions.getNumber(), questions.getSize(),
                    questions.getTotalElements(), questions.getTotalPages(), questions.isLast());
        }

        List<QuestionResponse> questionResponses = questions.map(QuestionResponse::createResponse).getContent();

        return new PagedResponse<>(questionResponses, questions.getNumber(), questions.getSize(),
                questions.getTotalElements(), questions.getTotalPages(), questions.isLast());
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
    }

    public Question createQuestion(QuestionRequest questionRequest) {

        Question question = new Question();

        question.setText(questionRequest.getText());

        questionRequest.getAnswers().forEach(answerRequest -> question.addAnswer(new Answer(answerRequest.getText(), answerRequest.getIsCorrect())));

        return questionRepository.save(question);
    }
}
