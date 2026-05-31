package com.example.bootcamp.domain.spi;

import com.example.bootcamp.domain.model.Bootcamp;
import reactor.core.publisher.Flux;

import java.util.List;

public interface BootcampExternalService {
    Flux<Bootcamp> getBootcampsByIds(List<Long> ids);
}
