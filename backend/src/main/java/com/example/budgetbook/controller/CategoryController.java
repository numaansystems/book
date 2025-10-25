package com.example.budgetbook.controller;

import com.example.budgetbook.model.Agency;
import com.example.budgetbook.model.Category;
import com.example.budgetbook.repository.AgencyRepository;
import com.example.budgetbook.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final AgencyRepository agencyRepository;

    public CategoryController(CategoryRepository categoryRepository, AgencyRepository agencyRepository) {
        this.categoryRepository = categoryRepository;
        this.agencyRepository = agencyRepository;
    }

    @GetMapping("/by-agency/{agencyId}")
    public List<Category> byAgency(@PathVariable Long agencyId) {
        return categoryRepository.findByAgencyId(agencyId);
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestParam Long agencyId, @RequestBody Category c) {
        Agency agency = agencyRepository.findById(agencyId).orElseThrow();
        if (categoryRepository.existsByAgencyAndTitle(agency, c.getTitle())) {
            return ResponseEntity.badRequest().build();
        }
        c.setAgency(agency);
        Category saved = categoryRepository.save(c);
        return ResponseEntity.created(URI.create("/api/categories/"+saved.getId())).body(saved);
    }
}
