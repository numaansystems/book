package com.numaansystems.budgetbook.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CategoryResponse {

    private Long id;
    private String title;
    private String description;
    private Long agencyId;
    private String agencyName;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
