package com.numaansystems.budgetbook.repository;

import com.numaansystems.budgetbook.model.Biennium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BienniumRepository extends JpaRepository<Biennium, Long> {
    Optional<Biennium> findByActiveTrue();
}
