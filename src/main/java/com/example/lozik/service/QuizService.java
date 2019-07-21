package com.example.lozik.service;

import com.example.lozik.entity.Answer;
import com.example.lozik.entity.Choice;
import com.example.lozik.entity.Question;
import com.example.lozik.entity.User;
import com.example.lozik.exception.ResourceNotFoundException;
import com.example.lozik.payload.request.ChoiceRequest;
import com.example.lozik.payload.response.AnswerResponse;
import com.example.lozik.payload.response.ChoiceResponse;
import com.example.lozik.payload.response.QuestionResponse;
import com.example.lozik.payload.response.QuizResponse;
import com.example.lozik.repository.AnswerRepository;
import com.example.lozik.repository.ChoiceRepository;
import com.example.lozik.repository.QuestionRepository;
import com.example.lozik.repository.UserRepository;
import com.example.lozik.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    public QuestionResponse getNextQuestion(UserPrincipal currentUser) {

        User user = userRepository.findById(currentUser.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", currentUser.getId())
        );

        List<Choice> choices = choiceRepository.findByUser(user);

        List<Question> questions = questionRepository.findAll();

        QuestionResponse questionResponse;
        if(choices.size() < 3) {
            Question question = questions.get(choices.size());
            questionResponse = QuestionResponse.createResponse(question);
            return questionResponse;
        }

        return null;
    }

    public void createChoice(UserPrincipal currentUser, ChoiceRequest choiceRequest) {
        Choice choice = new Choice();

        User user = userRepository.findById(currentUser.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", currentUser.getId())
        );

        Question question = questionRepository.findById(choiceRequest.getQuestionId()).orElseThrow(
                () -> new ResourceNotFoundException("Question", "id", choiceRequest.getQuestionId())
        );

        Answer answer = answerRepository.findById(choiceRequest.getAnswerId()).orElseThrow(
                () -> new ResourceNotFoundException("Answer", "id", choiceRequest.getAnswerId())
        );

        choice.setUser(user);
        choice.setQuestion(question);
        choice.setAnswer(answer);

        choiceRepository.save(choice);
    }

    public QuizResponse getChoices(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "email", email)
        );

        QuizResponse quizResponse = new QuizResponse();

        quizResponse.setEmail(user.getEmail());
        quizResponse.setName(user.getName());

        List<ChoiceResponse> choices = choiceRepository.findByUser(user).stream().map(choice -> {
            AnswerResponse answerResponse = new AnswerResponse();
            Answer answer = choice.getAnswer();
            answerResponse.setId(answer.getId());
            answerResponse.setText(answer.getText());
            answerResponse.setIsCorrect(answer.getIsCorrect());

            Question question = choice.getQuestion();
            QuestionResponse questionResponse = QuestionResponse.createResponse(question);

            ChoiceResponse choiceResponse = new ChoiceResponse();

            choiceResponse.setAnswerResponse(answerResponse);
            choiceResponse.setQuestionResponse(questionResponse);
            choiceResponse.setIsCorrect(answer.getIsCorrect());

            return choiceResponse;
        }).collect(Collectors.toList());

        quizResponse.setChoices(choices);

        quizResponse.setScore(choices.stream().filter(ChoiceResponse::getIsCorrect).count());

        return quizResponse;
    }
}
