package com.example.budgetbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    
    @GetMapping("/")
    public String index() {
        return "redirect:/snappies";
    }
    
    @GetMapping("/categories")
    public String categoriesPage() {
        return "categories-simple";
    }
    
    @GetMapping("/snappies")
    public String snappiesPage() {
        return "snappies";
    }
}
