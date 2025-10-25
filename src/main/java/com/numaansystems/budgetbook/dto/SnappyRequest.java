package com.numaansystems.budgetbook.dto;

import com.numaansystems.budgetbook.model.Snappy.DecisionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class SnappyRequest {

    @NotNull(message = "Agency ID is required")
    private Long agencyId;

    @NotNull(message = "Budget code ID is required")
    private Long budgetCodeId;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Fiscal year is required")
    private Integer fiscalYear;

    @NotNull(message = "Decision status is required")
    private DecisionStatus decisionStatus;

    private Integer sortOrder;

    @NotBlank(message = "Title is required")
    private String title;

    private String shortDescription;

    private BigDecimal year1RecurringRequirements;
    private BigDecimal year1NonrecurringRequirements;
    private BigDecimal year1RecurringReceipts;
    private BigDecimal year1NonrecurringReceipts;
    private BigDecimal year1RecurringFte;
    private BigDecimal year1NonrecurringFte;

    private BigDecimal year2RecurringRequirements;
    private BigDecimal year2NonrecurringRequirements;
    private BigDecimal year2RecurringReceipts;
    private BigDecimal year2NonrecurringReceipts;
    private BigDecimal year2RecurringFte;
    private BigDecimal year2NonrecurringFte;
}
