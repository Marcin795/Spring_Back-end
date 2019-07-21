package com.example.lozik.service;

import com.example.lozik.entity.Fact;
import com.example.lozik.exception.BadRequestException;
import com.example.lozik.exception.ResourceNotFoundException;
import com.example.lozik.payload.request.FactRequest;
import com.example.lozik.payload.response.FactResponse;
import com.example.lozik.payload.response.PagedResponse;
import com.example.lozik.repository.FactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class FactService {

    @Autowired
    private FactRepository factRepository;

    public PagedResponse<FactResponse> getAllFacts(int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Page<Fact> facts = factRepository.findAll(pageable);

        if(facts.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), facts.getNumber(),
                    facts.getSize(), facts.getTotalElements(), facts.getTotalPages(), facts.isLast());
        }

        List<FactResponse> factResponses = facts.map(this::createResponse).getContent();

        return new PagedResponse<>(factResponses, facts.getNumber(), facts.getSize(),
                facts.getTotalElements(), facts.getTotalPages(), facts.isLast());
    }

    public Fact createFact(FactRequest factRequest) {
        Fact fact = new Fact();

        fact.setText(factRequest.getText());
        fact.setDescription(factRequest.getDescription());
        fact.setUuid(UUID.randomUUID().toString());

        return factRepository.save(fact);
    }

    public void deleteFact(Long factId) {
        factRepository.findById(factId).orElseThrow(
                () -> new ResourceNotFoundException("Fact", "id", factId)
        );
        factRepository.deleteById(factId);
    }

    public FactResponse getFactById(Long factId) {
        Fact fact = factRepository.findById(factId).orElseThrow(
                () -> new ResourceNotFoundException("Fact", "id", factId)
        );

        return createResponse(fact);
    }

    public FactResponse getFactByUuid(String factUuid) {
        Fact fact = factRepository.findByUuid(factUuid).orElseThrow(
                () -> new ResourceNotFoundException("Fact", "uuid", factUuid)
        );

        return createResponse(fact);
    }

    private FactResponse createResponse(Fact fact) {
        FactResponse response = new FactResponse();
        response.setId(fact.getId());
        response.setText(fact.getText());
        response.setDescription(fact.getDescription());
        response.setUuid(fact.getUuid());
        return response;
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
    }
}
