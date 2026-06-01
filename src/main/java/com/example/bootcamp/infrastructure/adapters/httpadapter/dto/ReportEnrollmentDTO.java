package com.example.bootcamp.infrastructure.adapters.httpadapter.dto;

import java.util.List;

public record ReportEnrollmentDTO(
        Long id,
        String name,
        String identification,
        String email,
        List<Long> bootcampIds
) {}