package com.numaansystems.budgetbook.repository;

import com.numaansystems.budgetbook.model.BudgetCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BudgetCodeRepository extends JpaRepository<BudgetCode, Long> {
    List<BudgetCode> findByAgencyId(Long agencyId);
}
