package com.numaansystems.budgetbook.controller;

import com.numaansystems.budgetbook.model.Biennium;
import com.numaansystems.budgetbook.repository.BienniumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/biennium")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BienniumController {

    private final BienniumRepository bienniumRepository;

    @GetMapping("/active")
    public ResponseEntity<Biennium> getActiveBiennium() {
        return bienniumRepository.findByActiveTrue()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
