package com.example.lozik.controller;

import com.example.lozik.payload.request.VisitRequest;
import com.example.lozik.payload.response.VisitedFactResponse;
import com.example.lozik.payload.response.VisitedFactsResponse;
import com.example.lozik.security.CurrentUser;
import com.example.lozik.security.UserPrincipal;
import com.example.lozik.service.VisitedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/visited")
public class VisitedController {

    @Autowired
    VisitedService visitedService;

    @PutMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public VisitedFactResponse setFactVisitedByUser(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody VisitRequest visitRequest) {
        return visitedService.setFactVisitedByUser(currentUser, visitRequest);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public VisitedFactsResponse getVisitedFacts(@CurrentUser UserPrincipal currentUser) {
        return visitedService.getVisitedFacts(currentUser);
    }
}
