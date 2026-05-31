package com.example.bootcamp.infrastructure.entrypoints.dto;

import java.util.List;

public record PersonRequestDTO(
        String name,
        String email,
        String identification,
        List<Long> bootcampIds
) {}