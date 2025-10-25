package com.example.budgetbook.repository;

import com.example.budgetbook.model.Category;
import com.example.budgetbook.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByAgencyId(Long agencyId);
    boolean existsByAgencyAndTitle(Agency agency, String title);
}
