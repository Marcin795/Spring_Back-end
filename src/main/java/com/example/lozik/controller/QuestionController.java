package com.example.lozik.controller;

import com.example.lozik.entity.Question;
import com.example.lozik.payload.request.QuestionRequest;
import com.example.lozik.payload.response.ApiResponse;
import com.example.lozik.payload.response.PagedResponse;
import com.example.lozik.payload.response.QuestionResponse;
import com.example.lozik.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {


    @Autowired
    private QuestionService questionService;

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PagedResponse<QuestionResponse> getQuestions(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "30") int size) {
        return questionService.getAllQuestions(page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionRequest questionRequest) {
        Question question = questionService.createQuestion(questionRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{questionId}")
                .buildAndExpand(question.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Question created successfully"));
    }
}
