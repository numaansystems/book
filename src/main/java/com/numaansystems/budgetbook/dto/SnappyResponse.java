package com.numaansystems.budgetbook.dto;

import com.numaansystems.budgetbook.model.Snappy.DecisionStatus;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class SnappyResponse {

    private Long id;
    private Long agencyId;
    private String agencyName;
    private Long budgetCodeId;
    private String budgetCodeCode;
    private String budgetCodeDescription;
    private Long categoryId;
    private String categoryTitle;
    private Integer fiscalYear;
    private DecisionStatus decisionStatus;
    private Integer sortOrder;
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
