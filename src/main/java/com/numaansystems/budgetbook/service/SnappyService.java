package com.numaansystems.budgetbook.service;

import com.numaansystems.budgetbook.dto.SnappyRequest;
import com.numaansystems.budgetbook.dto.SnappyResponse;
import com.numaansystems.budgetbook.model.*;
import com.numaansystems.budgetbook.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SnappyService {

    private final SnappyRepository snappyRepository;
    private final AgencyRepository agencyRepository;
    private final BudgetCodeRepository budgetCodeRepository;
    private final CategoryRepository categoryRepository;
    private final BienniumRepository bienniumRepository;

    public List<SnappyResponse> getSnappiesByAgencyAndFiscalYear(Long agencyId, Integer fiscalYear) {
        return snappyRepository.findByAgencyIdAndFiscalYear(agencyId, fiscalYear).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public SnappyResponse getSnappyById(Long id) {
        Snappy snappy = snappyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Snappy not found"));
        return mapToResponse(snappy);
    }

    @Transactional
    public SnappyResponse createSnappy(SnappyRequest request) {
        validateYearAccess(request.getFiscalYear());

        Agency agency = agencyRepository.findById(request.getAgencyId())
                .orElseThrow(() -> new RuntimeException("Agency not found"));
        BudgetCode budgetCode = budgetCodeRepository.findById(request.getBudgetCodeId())
                .orElseThrow(() -> new RuntimeException("Budget code not found"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Snappy snappy = new Snappy();
        mapRequestToEntity(request, snappy);
        snappy.setAgency(agency);
        snappy.setBudgetCode(budgetCode);
        snappy.setCategory(category);

        snappy = snappyRepository.save(snappy);
        return mapToResponse(snappy);
    }

    @Transactional
    public SnappyResponse updateSnappy(Long id, SnappyRequest request) {
        Snappy snappy = snappyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Snappy not found"));

        validateYearAccess(request.getFiscalYear());

        BudgetCode budgetCode = budgetCodeRepository.findById(request.getBudgetCodeId())
                .orElseThrow(() -> new RuntimeException("Budget code not found"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        mapRequestToEntity(request, snappy);
        snappy.setBudgetCode(budgetCode);
        snappy.setCategory(category);

        snappy = snappyRepository.save(snappy);
        return mapToResponse(snappy);
    }

    @Transactional
    public void deleteSnappy(Long id) {
        snappyRepository.deleteById(id);
    }

    private void validateYearAccess(Integer fiscalYear) {
        Biennium biennium = bienniumRepository.findByActiveTrue()
                .orElseThrow(() -> new RuntimeException("No active biennium found"));

        // If in second year, cannot modify first year data
        if (biennium.isSecondYear() && fiscalYear.equals(biennium.getStartYear())) {
            throw new RuntimeException("Cannot modify first year data in second year");
        }
    }

    private void mapRequestToEntity(SnappyRequest request, Snappy snappy) {
        snappy.setFiscalYear(request.getFiscalYear());
        snappy.setDecisionStatus(request.getDecisionStatus());
        snappy.setSortOrder(request.getSortOrder());
        snappy.setTitle(request.getTitle());
        snappy.setShortDescription(request.getShortDescription());

        snappy.setYear1RecurringRequirements(request.getYear1RecurringRequirements());
        snappy.setYear1NonrecurringRequirements(request.getYear1NonrecurringRequirements());
        snappy.setYear1RecurringReceipts(request.getYear1RecurringReceipts());
        snappy.setYear1NonrecurringReceipts(request.getYear1NonrecurringReceipts());
        snappy.setYear1RecurringFte(request.getYear1RecurringFte());
        snappy.setYear1NonrecurringFte(request.getYear1NonrecurringFte());

        snappy.setYear2RecurringRequirements(request.getYear2RecurringRequirements());
        snappy.setYear2NonrecurringRequirements(request.getYear2NonrecurringRequirements());
        snappy.setYear2RecurringReceipts(request.getYear2RecurringReceipts());
        snappy.setYear2NonrecurringReceipts(request.getYear2NonrecurringReceipts());
        snappy.setYear2RecurringFte(request.getYear2RecurringFte());
        snappy.setYear2NonrecurringFte(request.getYear2NonrecurringFte());
    }

    private SnappyResponse mapToResponse(Snappy snappy) {
        SnappyResponse response = new SnappyResponse();
        response.setId(snappy.getId());
        response.setAgencyId(snappy.getAgency().getId());
        response.setAgencyName(snappy.getAgency().getName());
        response.setBudgetCodeId(snappy.getBudgetCode().getId());
        response.setBudgetCodeCode(snappy.getBudgetCode().getCode());
        response.setBudgetCodeDescription(snappy.getBudgetCode().getDescription());
        response.setCategoryId(snappy.getCategory().getId());
        response.setCategoryTitle(snappy.getCategory().getTitle());
        response.setFiscalYear(snappy.getFiscalYear());
        response.setDecisionStatus(snappy.getDecisionStatus());
        response.setSortOrder(snappy.getSortOrder());
        response.setTitle(snappy.getTitle());
        response.setShortDescription(snappy.getShortDescription());

        response.setYear1RecurringRequirements(snappy.getYear1RecurringRequirements());
        response.setYear1NonrecurringRequirements(snappy.getYear1NonrecurringRequirements());
        response.setYear1RecurringReceipts(snappy.getYear1RecurringReceipts());
        response.setYear1NonrecurringReceipts(snappy.getYear1NonrecurringReceipts());
        response.setYear1RecurringFte(snappy.getYear1RecurringFte());
        response.setYear1NonrecurringFte(snappy.getYear1NonrecurringFte());

        response.setYear2RecurringRequirements(snappy.getYear2RecurringRequirements());
        response.setYear2NonrecurringRequirements(snappy.getYear2NonrecurringRequirements());
        response.setYear2RecurringReceipts(snappy.getYear2RecurringReceipts());
        response.setYear2NonrecurringReceipts(snappy.getYear2NonrecurringReceipts());
        response.setYear2RecurringFte(snappy.getYear2RecurringFte());
        response.setYear2NonrecurringFte(snappy.getYear2NonrecurringFte());

        return response;
    }
}
