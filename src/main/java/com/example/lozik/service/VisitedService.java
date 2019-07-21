package com.example.lozik.service;

import com.example.lozik.entity.Fact;
import com.example.lozik.entity.User;
import com.example.lozik.exception.ResourceNotFoundException;
import com.example.lozik.payload.request.VisitRequest;
import com.example.lozik.payload.response.FactResponse;
import com.example.lozik.payload.response.VisitedFactsResponse;
import com.example.lozik.payload.response.VisitedFactResponse;
import com.example.lozik.repository.FactRepository;
import com.example.lozik.repository.UserRepository;
import com.example.lozik.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VisitedService {

    @Autowired
    private FactRepository factRepository;

    @Autowired
    private UserRepository userRepository;

    public VisitedFactResponse setFactVisitedByUser(UserPrincipal currentUser, VisitRequest visitRequest) {

        Fact fact = factRepository.findByUuid(visitRequest.getFactUuid()).orElseThrow(
                () -> new ResourceNotFoundException("Fact", "uuid", visitRequest.getFactUuid())
        );

        User user = userRepository.findById(currentUser.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", currentUser.getId())
        );

        Set<Fact> visited = user.getFacts();
        if(visited == null) {
            visited = new HashSet<>();
        }
        visited.add(fact);

        user.setFacts(visited);

        userRepository.save(user);

        VisitedFactResponse response = new VisitedFactResponse();
        response.setFact(fact);
        response.setVisitedCount(visited.size());
        response.setToVisitCount(factRepository.count());

        return response;
    }

    public VisitedFactsResponse getVisitedFacts(UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", currentUser.getId())
        );

        Set<Fact> visited = user.getFacts();

        VisitedFactsResponse response = new VisitedFactsResponse();
        response.setFacts(visited.stream().map(fact -> {
            FactResponse factResponse = new FactResponse();
            factResponse.setUuid(fact.getUuid());
            factResponse.setDescription(fact.getDescription());
            factResponse.setText(fact.getText());
            return factResponse;
        }).collect(Collectors.toSet()));
        response.setVisitedCount(visited.size());
        response.setToVisitCount(factRepository.count());
        return response;
    }
}
