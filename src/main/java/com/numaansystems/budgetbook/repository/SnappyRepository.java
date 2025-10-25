package com.numaansystems.budgetbook.repository;

import com.numaansystems.budgetbook.model.Snappy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SnappyRepository extends JpaRepository<Snappy, Long> {
    List<Snappy> findByAgencyIdAndFiscalYear(Long agencyId, Integer fiscalYear);
    List<Snappy> findByAgencyIdAndBudgetCodeIdAndFiscalYear(Long agencyId, Long budgetCodeId, Integer fiscalYear);
}
