package com.example.budgetbook.config;

import com.example.budgetbook.model.*;
import com.example.budgetbook.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer {
    private final AgencyRepository agencyRepository;
    private final BudgetCodeRepository budgetCodeRepository;
    private final CategoryRepository categoryRepository;
    private final SnappyRepository snappyRepository;

    public DataInitializer(AgencyRepository agencyRepository, BudgetCodeRepository budgetCodeRepository, CategoryRepository categoryRepository, SnappyRepository snappyRepository) {
        this.agencyRepository = agencyRepository;
        this.budgetCodeRepository = budgetCodeRepository;
        this.categoryRepository = categoryRepository;
        this.snappyRepository = snappyRepository;
    }

    @PostConstruct
    public void init() {
        Agency a1 = new Agency("Agency One");
        agencyRepository.save(a1);

        BudgetCode b1 = new BudgetCode(); b1.setCode("1001"); b1.setDescription("General"); b1.setAgency(a1);
        BudgetCode b2 = new BudgetCode(); b2.setCode("2001"); b2.setDescription("Capital"); b2.setAgency(a1);
        budgetCodeRepository.save(b1); budgetCodeRepository.save(b2);

        Category c1 = new Category(); c1.setTitle("Personnel"); c1.setDescription("Personnel costs"); c1.setAgency(a1); c1.setCreatedBy("system");
        Category c2 = new Category(); c2.setTitle("Operations"); c2.setDescription("Ops"); c2.setAgency(a1); c2.setCreatedBy("system");
        categoryRepository.save(c1); categoryRepository.save(c2);

        Snappy s = new Snappy();
        s.setAgency(a1);
        s.setBudgetCode(b1);
        s.setCategory(c1);
        s.setTitle("Add 2 FTE");
        s.setY1RecurringFte(new BigDecimal("2.000"));
        s.setY2RecurringFte(new BigDecimal("0.000"));
        s.setDecisionStatus("in");
        snappyRepository.save(s);
    }
}
