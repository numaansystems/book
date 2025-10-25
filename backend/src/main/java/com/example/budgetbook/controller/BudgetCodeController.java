package com.example.budgetbook.controller;

import com.example.budgetbook.model.BudgetCode;
import com.example.budgetbook.repository.BudgetCodeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/budget-codes")
public class BudgetCodeController {
    private final BudgetCodeRepository budgetCodeRepository;
    public BudgetCodeController(BudgetCodeRepository budgetCodeRepository) { this.budgetCodeRepository = budgetCodeRepository; }

    @GetMapping("/by-agency/{agencyId}")
    public List<BudgetCode> byAgency(@PathVariable Long agencyId) { return budgetCodeRepository.findByAgencyId(agencyId); }
}
