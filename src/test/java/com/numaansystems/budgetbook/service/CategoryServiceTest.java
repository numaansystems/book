package com.numaansystems.budgetbook.service;

import com.numaansystems.budgetbook.dto.CategoryRequest;
import com.numaansystems.budgetbook.dto.CategoryResponse;
import com.numaansystems.budgetbook.model.Agency;
import com.numaansystems.budgetbook.repository.AgencyRepository;
import com.numaansystems.budgetbook.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Agency testAgency;

    @BeforeEach
    void setUp() {
        testAgency = new Agency();
        testAgency.setCode("TEST001");
        testAgency.setName("Test Agency");
        testAgency = agencyRepository.save(testAgency);
    }

    @Test
    void testCreateCategory() {
        CategoryRequest request = new CategoryRequest();
        request.setTitle("Test Category");
        request.setDescription("Test Description");
        request.setAgencyId(testAgency.getId());
        request.setCreatedBy("testuser");

        CategoryResponse response = categoryService.createCategory(request);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("Test Category", response.getTitle());
        assertEquals("Test Description", response.getDescription());
        assertEquals(testAgency.getId(), response.getAgencyId());
    }

    @Test
    void testCreateDuplicateCategoryThrowsException() {
        CategoryRequest request = new CategoryRequest();
        request.setTitle("Duplicate Category");
        request.setAgencyId(testAgency.getId());
        request.setCreatedBy("testuser");

        categoryService.createCategory(request);

        assertThrows(RuntimeException.class, () -> {
            categoryService.createCategory(request);
        });
    }

    @Test
    void testUpdateCategory() {
        CategoryRequest createRequest = new CategoryRequest();
        createRequest.setTitle("Original Title");
        createRequest.setAgencyId(testAgency.getId());
        createRequest.setCreatedBy("testuser");

        CategoryResponse created = categoryService.createCategory(createRequest);

        CategoryRequest updateRequest = new CategoryRequest();
        updateRequest.setTitle("Updated Title");
        updateRequest.setDescription("Updated Description");
        updateRequest.setAgencyId(testAgency.getId());
        updateRequest.setUpdatedBy("testuser2");

        CategoryResponse updated = categoryService.updateCategory(created.getId(), updateRequest);

        assertEquals("Updated Title", updated.getTitle());
        assertEquals("Updated Description", updated.getDescription());
        assertEquals("testuser2", updated.getUpdatedBy());
    }

    @Test
    void testDeleteCategory() {
        CategoryRequest request = new CategoryRequest();
        request.setTitle("To Delete");
        request.setAgencyId(testAgency.getId());
        request.setCreatedBy("testuser");

        CategoryResponse created = categoryService.createCategory(request);
        Long categoryId = created.getId();

        categoryService.deleteCategory(categoryId);

        assertFalse(categoryRepository.findById(categoryId).isPresent());
    }
}
