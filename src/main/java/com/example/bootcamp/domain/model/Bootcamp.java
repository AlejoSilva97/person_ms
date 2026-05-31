package com.example.bootcamp.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public record Bootcamp(
        Long id,
        String name,
        String description,
        LocalDateTime launchDate,
        Integer duration,
        List<Capacity> capacities
) {}
