package com.example.lozik.repository;

import com.example.lozik.entity.Choice;
import com.example.lozik.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    List<Choice> findByUser(User user);
}
