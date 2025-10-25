package com.numaansystems.budgetbook.controller;

import com.numaansystems.budgetbook.dto.SnappyRequest;
import com.numaansystems.budgetbook.dto.SnappyResponse;
import com.numaansystems.budgetbook.service.SnappyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/snappies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SnappyController {

    private final SnappyService snappyService;

    @GetMapping
    public ResponseEntity<List<SnappyResponse>> getSnappies(
            @RequestParam Long agencyId,
            @RequestParam Integer fiscalYear) {
        return ResponseEntity.ok(snappyService.getSnappiesByAgencyAndFiscalYear(agencyId, fiscalYear));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SnappyResponse> getSnappyById(@PathVariable Long id) {
        return ResponseEntity.ok(snappyService.getSnappyById(id));
    }

    @PostMapping
    public ResponseEntity<SnappyResponse> createSnappy(@Valid @RequestBody SnappyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(snappyService.createSnappy(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SnappyResponse> updateSnappy(@PathVariable Long id, @Valid @RequestBody SnappyRequest request) {
        return ResponseEntity.ok(snappyService.updateSnappy(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSnappy(@PathVariable Long id) {
        snappyService.deleteSnappy(id);
        return ResponseEntity.noContent().build();
    }
}
