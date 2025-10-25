package com.example.budgetbook.repository;

import com.example.budgetbook.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
}
