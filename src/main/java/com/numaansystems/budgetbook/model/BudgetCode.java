package com.numaansystems.budgetbook.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "budget_codes")
public class BudgetCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency;
}
