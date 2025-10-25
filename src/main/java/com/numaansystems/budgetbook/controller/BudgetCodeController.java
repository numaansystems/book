package com.numaansystems.budgetbook.controller;

import com.numaansystems.budgetbook.model.BudgetCode;
import com.numaansystems.budgetbook.repository.BudgetCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget-codes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BudgetCodeController {

    private final BudgetCodeRepository budgetCodeRepository;

    @GetMapping
    public ResponseEntity<List<BudgetCode>> getBudgetCodesByAgency(@RequestParam Long agencyId) {
        return ResponseEntity.ok(budgetCodeRepository.findByAgencyId(agencyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetCode> getBudgetCodeById(@PathVariable Long id) {
        return budgetCodeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
