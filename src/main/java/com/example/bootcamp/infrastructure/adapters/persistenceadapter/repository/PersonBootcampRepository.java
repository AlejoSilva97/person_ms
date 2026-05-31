package com.example.bootcamp.infrastructure.adapters.persistenceadapter.repository;

import com.example.bootcamp.infrastructure.adapters.persistenceadapter.entity.PersonBootcampEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonBootcampRepository extends ReactiveCrudRepository<PersonBootcampEntity, Long> { }