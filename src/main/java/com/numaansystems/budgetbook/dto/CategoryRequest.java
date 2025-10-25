package com.numaansystems.budgetbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Agency ID is required")
    private Long agencyId;

    private String createdBy;

    private String updatedBy;
}
