package com.llp.repository;

import com.llp.model.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Sessions, Integer> {
    Optional<Sessions> findById(int id);
}
