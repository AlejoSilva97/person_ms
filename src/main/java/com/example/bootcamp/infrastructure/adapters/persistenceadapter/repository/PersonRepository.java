package com.example.bootcamp.infrastructure.adapters.persistenceadapter.repository;

import com.example.bootcamp.infrastructure.adapters.persistenceadapter.entity.PersonEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<PersonEntity, Long> {
    Mono<PersonEntity> findByIdentification(String identification);
    Mono<PersonEntity> findByEmail(String email);
}