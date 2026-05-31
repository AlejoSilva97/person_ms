package com.example.bootcamp.infrastructure.adapters.httpadapter.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ExternalBootcampDTO(
        Long id,
        String name,
        String description,
        LocalDateTime launchDate,
        Integer duration,
        List<ExternalCapacityDTO> capacities
) {}