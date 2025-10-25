package com.numaansystems.budgetbook.repository;

import com.numaansystems.budgetbook.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByAgencyId(Long agencyId);
    Optional<Category> findByAgencyIdAndTitle(Long agencyId, String title);
}
