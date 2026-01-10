package com.example.budgetbook.controller;

import com.example.budgetbook.model.Agency;
import com.example.budgetbook.repository.AgencyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/agencies")
public class AgencyController {
    private final AgencyRepository agencyRepository;

    public AgencyController(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    @GetMapping
    public List<Agency> getAllAgencies() {
        return agencyRepository.findAll();
    }
}
