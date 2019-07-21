package com.example.lozik.repository;

import com.example.lozik.entity.Fact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FactRepository extends JpaRepository<Fact, Long> {

    Optional<Fact> findById(Long factId);

    long count();

    Optional<Fact> findByUuid(String uuid);

    void deleteById(Long id);
}
