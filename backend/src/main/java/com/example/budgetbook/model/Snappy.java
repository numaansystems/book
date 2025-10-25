package com.example.budgetbook.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Snappy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Agency agency;

    @ManyToOne(optional = false)
    private BudgetCode budgetCode;

    @ManyToOne
    private Category category;

    private String decisionStatus; // in/out
    private Integer sortOrder;
    private String title;
    @Column(length = 2000)
    private String shortDescription;

    // amounts for year1 and year2
    private BigDecimal y1RecurringReq = BigDecimal.ZERO;
    private BigDecimal y1NonRecurringReq = BigDecimal.ZERO;
    private BigDecimal y1RecurringRec = BigDecimal.ZERO;
    private BigDecimal y1NonRecurringRec = BigDecimal.ZERO;
    private BigDecimal y1RecurringFte = BigDecimal.ZERO;
    private BigDecimal y1NonRecurringFte = BigDecimal.ZERO;

    private BigDecimal y2RecurringReq = BigDecimal.ZERO;
    private BigDecimal y2NonRecurringReq = BigDecimal.ZERO;
    private BigDecimal y2RecurringRec = BigDecimal.ZERO;
    private BigDecimal y2NonRecurringRec = BigDecimal.ZERO;
    private BigDecimal y2RecurringFte = BigDecimal.ZERO;
    private BigDecimal y2NonRecurringFte = BigDecimal.ZERO;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Agency getAgency() { return agency; }
    public void setAgency(Agency agency) { this.agency = agency; }
    public BudgetCode getBudgetCode() { return budgetCode; }
    public void setBudgetCode(BudgetCode budgetCode) { this.budgetCode = budgetCode; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public String getDecisionStatus() { return decisionStatus; }
    public void setDecisionStatus(String decisionStatus) { this.decisionStatus = decisionStatus; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
    public BigDecimal getY1RecurringReq() { return y1RecurringReq; }
    public void setY1RecurringReq(BigDecimal y1RecurringReq) { this.y1RecurringReq = y1RecurringReq; }
    public BigDecimal getY1NonRecurringReq() { return y1NonRecurringReq; }
    public void setY1NonRecurringReq(BigDecimal y1NonRecurringReq) { this.y1NonRecurringReq = y1NonRecurringReq; }
    public BigDecimal getY1RecurringRec() { return y1RecurringRec; }
    public void setY1RecurringRec(BigDecimal y1RecurringRec) { this.y1RecurringRec = y1RecurringRec; }
    public BigDecimal getY1NonRecurringRec() { return y1NonRecurringRec; }
    public void setY1NonRecurringRec(BigDecimal y1NonRecurringRec) { this.y1NonRecurringRec = y1NonRecurringRec; }
    public BigDecimal getY1RecurringFte() { return y1RecurringFte; }
    public void setY1RecurringFte(BigDecimal y1RecurringFte) { this.y1RecurringFte = y1RecurringFte; }
    public BigDecimal getY1NonRecurringFte() { return y1NonRecurringFte; }
    public void setY1NonRecurringFte(BigDecimal y1NonRecurringFte) { this.y1NonRecurringFte = y1NonRecurringFte; }
    public BigDecimal getY2RecurringReq() { return y2RecurringReq; }
    public void setY2RecurringReq(BigDecimal y2RecurringReq) { this.y2RecurringReq = y2RecurringReq; }
    public BigDecimal getY2NonRecurringReq() { return y2NonRecurringReq; }
    public void setY2NonRecurringReq(BigDecimal y2NonRecurringReq) { this.y2NonRecurringReq = y2NonRecurringReq; }
    public BigDecimal getY2RecurringRec() { return y2RecurringRec; }
    public void setY2RecurringRec(BigDecimal y2RecurringRec) { this.y2RecurringRec = y2RecurringRec; }
    public BigDecimal getY2NonRecurringRec() { return y2NonRecurringRec; }
    public void setY2NonRecurringRec(BigDecimal y2NonRecurringRec) { this.y2NonRecurringRec = y2NonRecurringRec; }
    public BigDecimal getY2RecurringFte() { return y2RecurringFte; }
    public void setY2RecurringFte(BigDecimal y2RecurringFte) { this.y2RecurringFte = y2RecurringFte; }
    public BigDecimal getY2NonRecurringFte() { return y2NonRecurringFte; }
    public void setY2NonRecurringFte(BigDecimal y2NonRecurringFte) { this.y2NonRecurringFte = y2NonRecurringFte; }
}
