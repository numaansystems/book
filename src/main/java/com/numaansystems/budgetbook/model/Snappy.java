package com.numaansystems.budgetbook.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "snappies")
public class Snappy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency;

    @ManyToOne
    @JoinColumn(name = "budget_code_id", nullable = false)
    private BudgetCode budgetCode;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "fiscal_year", nullable = false)
    private Integer fiscalYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "decision_status", nullable = false)
    private DecisionStatus decisionStatus;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(nullable = false)
    private String title;

    @Column(name = "short_description")
    private String shortDescription;

    // Year 1 amounts
    @Column(name = "year1_recurring_requirements", precision = 15, scale = 2)
    private BigDecimal year1RecurringRequirements;

    @Column(name = "year1_nonrecurring_requirements", precision = 15, scale = 2)
    private BigDecimal year1NonrecurringRequirements;

    @Column(name = "year1_recurring_receipts", precision = 15, scale = 2)
    private BigDecimal year1RecurringReceipts;

    @Column(name = "year1_nonrecurring_receipts", precision = 15, scale = 2)
    private BigDecimal year1NonrecurringReceipts;

    @Column(name = "year1_recurring_fte", precision = 10, scale = 3)
    private BigDecimal year1RecurringFte;

    @Column(name = "year1_nonrecurring_fte", precision = 10, scale = 3)
    private BigDecimal year1NonrecurringFte;

    // Year 2 amounts
    @Column(name = "year2_recurring_requirements", precision = 15, scale = 2)
    private BigDecimal year2RecurringRequirements;

    @Column(name = "year2_nonrecurring_requirements", precision = 15, scale = 2)
    private BigDecimal year2NonrecurringRequirements;

    @Column(name = "year2_recurring_receipts", precision = 15, scale = 2)
    private BigDecimal year2RecurringReceipts;

    @Column(name = "year2_nonrecurring_receipts", precision = 15, scale = 2)
    private BigDecimal year2NonrecurringReceipts;

    @Column(name = "year2_recurring_fte", precision = 10, scale = 3)
    private BigDecimal year2RecurringFte;

    @Column(name = "year2_nonrecurring_fte", precision = 10, scale = 3)
    private BigDecimal year2NonrecurringFte;

    public enum DecisionStatus {
        IN, OUT
    }
}
