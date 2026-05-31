package com.example.bootcamp.infrastructure.adapters.persistenceadapter;

import com.example.bootcamp.domain.model.Person;
import com.example.bootcamp.domain.spi.PersonPersistencePort;
import com.example.bootcamp.infrastructure.adapters.persistenceadapter.entity.PersonBootcampEntity;
import com.example.bootcamp.infrastructure.adapters.persistenceadapter.mapper.PersonEntityMapper;
import com.example.bootcamp.infrastructure.adapters.persistenceadapter.repository.PersonRepository;
import com.example.bootcamp.infrastructure.adapters.persistenceadapter.repository.PersonBootcampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonPersistenceAdapter implements PersonPersistencePort {

    private final PersonRepository personRepository;
    private final PersonBootcampRepository personBootcampRepository;
    private final PersonEntityMapper personEntityMapper;

    @Override
    @Transactional
    public Mono<Person> save(Person person) {
        return personRepository.save(personEntityMapper.toEntity(person))
                .flatMap(savedEntity -> {
                    List<PersonBootcampEntity> relations = person.bootcampIds().stream()
                            .map(bootcampId -> new PersonBootcampEntity(null, savedEntity.getId(), bootcampId))
                            .toList();

                    return personBootcampRepository.saveAll(relations)
                            .then(Mono.just(personEntityMapper.toModel(savedEntity, person.bootcampIds())));
                });
    }

    @Override
    public Mono<Boolean> existByIdentification(String identification) {
        return personRepository.findByIdentification(identification)
                .map(entity -> true)
                .defaultIfEmpty(false);
    }

    @Override
    public Mono<Boolean> existByEmail(String email) {
        return personRepository.findByEmail(email)
                .map(entity -> true)
                .defaultIfEmpty(false);
    }
}