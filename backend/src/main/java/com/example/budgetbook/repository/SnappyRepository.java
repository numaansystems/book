package com.example.budgetbook.repository;

import com.example.budgetbook.model.Snappy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnappyRepository extends JpaRepository<Snappy, Long> {
    List<Snappy> findByAgencyId(Long agencyId);
}
