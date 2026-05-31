package com.example.bootcamp.domain.spi;

import com.example.bootcamp.domain.model.Person;
import reactor.core.publisher.Mono;

public interface PersonPersistencePort {
    Mono<Person> save(Person person);
    Mono<Boolean> existByIdentification(String identification);
    Mono<Boolean> existByEmail(String email);
}