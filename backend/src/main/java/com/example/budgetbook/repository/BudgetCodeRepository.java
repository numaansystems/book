package com.example.budgetbook.repository;

import com.example.budgetbook.model.BudgetCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetCodeRepository extends JpaRepository<BudgetCode, Long> {
    List<BudgetCode> findByAgencyId(Long agencyId);
}
