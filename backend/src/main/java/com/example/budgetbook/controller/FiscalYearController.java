package com.example.budgetbook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/fiscal-year")
public class FiscalYearController {

    /**
     * Check if the given fiscal year is the second year of the biennium.
     * 
     * In a biennium (2-year budget cycle):
     * - Year 1: Users can enter data for both Year 1 and Year 2
     * - Year 2: Users can only modify Year 2 data (Year 1 becomes read-only)
     * 
     * @param year The fiscal year to check ("year1" or "year2")
     * @return JSON response with isSecondYear boolean flag
     */
    @GetMapping("/is-second-year")
    public Map<String, Boolean> isSecondYear(@RequestParam String year) {
        Map<String, Boolean> response = new HashMap<>();
        
        // If the year parameter is "year2", then we are in the second year
        // In the second year, Year 1 data should be read-only
        boolean isSecondYear = "year2".equalsIgnoreCase(year);
        
        response.put("isSecondYear", isSecondYear);
        response.put("year1ReadOnly", isSecondYear);
        
        return response;
    }
}
