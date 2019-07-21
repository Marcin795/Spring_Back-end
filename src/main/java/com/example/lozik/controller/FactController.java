package com.example.lozik.controller;

import com.example.lozik.entity.Fact;
import com.example.lozik.payload.request.FactRequest;
import com.example.lozik.payload.response.ApiResponse;
import com.example.lozik.payload.response.FactResponse;
import com.example.lozik.payload.response.PagedResponse;
import com.example.lozik.service.FactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/facts")
public class FactController {

    @Autowired
    FactService factService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PagedResponse<FactResponse> getFacts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "30") int size) {
        return factService.getAllFacts(page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createFact(@Valid @RequestBody FactRequest factRequest) {
        Fact fact = factService.createFact(factRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{factId}")
                .buildAndExpand(fact.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Fact created successfully"));
    }

    @DeleteMapping("/{factId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteFact(@PathVariable Long factId) {
        factService.deleteFact(factId);

        return ResponseEntity.ok().body(new ApiResponse(true, "Fact deleted successfully"));
    }

    @GetMapping("/{factId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public FactResponse getFactById(@PathVariable Long factId) {
        return factService.getFactById(factId);
    }

    @GetMapping("/uuid/{factUuid}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public FactResponse getFactByUuid(@PathVariable String factUuid) {
        return factService.getFactByUuid(factUuid);
    }
}
