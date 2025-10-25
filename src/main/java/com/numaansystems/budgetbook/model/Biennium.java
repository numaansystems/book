package com.numaansystems.budgetbook.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "biennium")
public class Biennium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_year", nullable = false, unique = true)
    private Integer startYear;

    @Column(name = "end_year", nullable = false)
    private Integer endYear;

    @Column(name = "current_fiscal_year", nullable = false)
    private Integer currentFiscalYear;

    @Column(nullable = false)
    private boolean active = true;

    public boolean isFirstYear() {
        return currentFiscalYear.equals(startYear);
    }

    public boolean isSecondYear() {
        return currentFiscalYear.equals(endYear);
    }
}
