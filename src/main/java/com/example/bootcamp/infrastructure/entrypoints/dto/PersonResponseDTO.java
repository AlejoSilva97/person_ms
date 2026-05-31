package com.example.bootcamp.infrastructure.entrypoints.dto;

import java.util.List;

public record PersonResponseDTO(
        Long id,
        String name,
        String email,
        String identification,
        List<Long> bootcampIds
) {}