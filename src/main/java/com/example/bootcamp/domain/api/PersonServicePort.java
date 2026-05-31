package com.example.bootcamp.domain.api;

import com.example.bootcamp.domain.model.Person;
import reactor.core.publisher.Mono;

public interface PersonServicePort {
    Mono<Person> registerPerson(Person person);
}