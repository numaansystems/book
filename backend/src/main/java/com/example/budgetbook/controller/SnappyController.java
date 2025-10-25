package com.example.budgetbook.controller;

import com.example.budgetbook.model.Snappy;
import com.example.budgetbook.repository.SnappyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/snappies")
public class SnappyController {
    private final SnappyRepository snappyRepository;

    public SnappyController(SnappyRepository snappyRepository) { this.snappyRepository = snappyRepository; }

    @GetMapping("/by-agency/{agencyId}")
    public List<Snappy> byAgency(@PathVariable Long agencyId) { return snappyRepository.findByAgencyId(agencyId); }

    @PostMapping
    public ResponseEntity<Snappy> create(@RequestBody Snappy s) {
        Snappy saved = snappyRepository.save(s);
        return ResponseEntity.created(URI.create("/api/snappies/"+saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Snappy> update(@PathVariable Long id, @RequestBody Snappy s) {
        return snappyRepository.findById(id).map(existing -> {
            s.setId(id);
            Snappy saved = snappyRepository.save(s);
            return ResponseEntity.ok(saved);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
