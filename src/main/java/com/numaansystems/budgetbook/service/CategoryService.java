package com.numaansystems.budgetbook.service;

import com.numaansystems.budgetbook.dto.CategoryRequest;
import com.numaansystems.budgetbook.dto.CategoryResponse;
import com.numaansystems.budgetbook.model.Agency;
import com.numaansystems.budgetbook.model.Category;
import com.numaansystems.budgetbook.repository.AgencyRepository;
import com.numaansystems.budgetbook.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AgencyRepository agencyRepository;

    public List<CategoryResponse> getCategoriesByAgency(Long agencyId) {
        return categoryRepository.findByAgencyId(agencyId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return mapToResponse(category);
    }

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        // Check for duplicate title within agency
        if (categoryRepository.findByAgencyIdAndTitle(request.getAgencyId(), request.getTitle()).isPresent()) {
            throw new RuntimeException("Category with this title already exists for the agency");
        }

        Agency agency = agencyRepository.findById(request.getAgencyId())
                .orElseThrow(() -> new RuntimeException("Agency not found"));

        Category category = new Category();
        category.setTitle(request.getTitle());
        category.setDescription(request.getDescription());
        category.setAgency(agency);
        category.setCreatedBy(request.getCreatedBy());
        category.setUpdatedBy(request.getUpdatedBy());

        category = categoryRepository.save(category);
        return mapToResponse(category);
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Check for duplicate title within agency (excluding current category)
        categoryRepository.findByAgencyIdAndTitle(request.getAgencyId(), request.getTitle())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new RuntimeException("Category with this title already exists for the agency");
                    }
                });

        category.setTitle(request.getTitle());
        category.setDescription(request.getDescription());
        category.setUpdatedBy(request.getUpdatedBy());

        category = categoryRepository.save(category);
        return mapToResponse(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setTitle(category.getTitle());
        response.setDescription(category.getDescription());
        response.setAgencyId(category.getAgency().getId());
        response.setAgencyName(category.getAgency().getName());
        response.setCreatedBy(category.getCreatedBy());
        response.setUpdatedBy(category.getUpdatedBy());
        response.setCreatedAt(category.getCreatedAt());
        response.setUpdatedAt(category.getUpdatedAt());
        return response;
    }
}
