package com.example.lozik.controller;

import com.example.lozik.payload.request.ChoiceRequest;
import com.example.lozik.payload.response.ApiResponse;
import com.example.lozik.payload.response.QuestionResponse;
import com.example.lozik.payload.response.QuizResponse;
import com.example.lozik.security.CurrentUser;
import com.example.lozik.security.UserPrincipal;
import com.example.lozik.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public QuestionResponse getNextQuestion(@CurrentUser UserPrincipal currentUser) {
        return quizService.getNextQuestion(currentUser);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createChoice(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody ChoiceRequest choiceRequest) {
        quizService.createChoice(currentUser, choiceRequest);

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "Choice created successfully"));
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANIA')")
    public QuizResponse getAnswers(@PathVariable String email) {
        return quizService.getChoices(email);
    }

    @GetMapping("/score")
    @PreAuthorize("hasRole('USER')")
    public QuizResponse getCurrentUserAnswers(@CurrentUser UserPrincipal currentUser) {
        return quizService.getChoices(currentUser.getEmail());
    }
}
