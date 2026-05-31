package com.example.bootcamp.domain.usecase;

import com.example.bootcamp.domain.api.PersonServicePort;
import com.example.bootcamp.domain.constants.Constants;
import com.example.bootcamp.domain.exceptions.BootcampNotFoundException;
import com.example.bootcamp.domain.exceptions.PersonAlreadyExistsException;
import com.example.bootcamp.domain.exceptions.InvalidFieldException;
import com.example.bootcamp.domain.model.Bootcamp;
import com.example.bootcamp.domain.model.Person;
import com.example.bootcamp.domain.spi.BootcampExternalService;
import com.example.bootcamp.domain.spi.PersonPersistencePort;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

public class PersonUseCase implements PersonServicePort {

    private final PersonPersistencePort personPersistencePort;
    private final BootcampExternalService bootcampExternalService;

    public PersonUseCase(PersonPersistencePort personPersistencePort, BootcampExternalService bootcampExternalService) {
        this.personPersistencePort = personPersistencePort;
        this.bootcampExternalService = bootcampExternalService;
    }

    @Override
    public Mono<Person> registerPerson(Person person) {
        return Mono.zip(
                personPersistencePort.existByIdentification(person.identification()),
                personPersistencePort.existByEmail(person.email())
        ).flatMap(tuple -> {
            boolean idExists = tuple.getT1();
            boolean emailExists = tuple.getT2();
            if (idExists) {
                return Mono.error(new PersonAlreadyExistsException(
                        String.format(Constants.PERSON_ALREADY_EXISTS, person.identification())));
            }
            if (emailExists) {
                return Mono.error(new PersonAlreadyExistsException(
                        String.format(Constants.PERSON_EMAIL_ALREADY_EXISTS, person.email())));
            }
            return bootcampExternalService.getBootcampsByIds(person.bootcampIds()).collectList();
        }).flatMap(bootcamps -> {
            if (bootcamps.size() != person.bootcampIds().size()) {
                return Mono.error(new BootcampNotFoundException(Constants.BOOTCAMPS_NOT_FOUND));
            }
            if (hasScheduleOverlap(bootcamps)) {
                return Mono.error(new InvalidFieldException(Constants.BOOTCAMPS_SCHEDULE_OVERLAP));
            }
            return personPersistencePort.save(person);
        });
    }

    private boolean hasScheduleOverlap(List<Bootcamp> bootcamps) {
        for (int i = 0; i < bootcamps.size(); i++) {
            Bootcamp b1 = bootcamps.get(i);
            LocalDateTime start1 = b1.launchDate();
            LocalDateTime end1 = start1.plusHours(b1.duration());

            for (int j = i + 1; j < bootcamps.size(); j++) {
                Bootcamp b2 = bootcamps.get(j);
                LocalDateTime start2 = b2.launchDate();
                LocalDateTime end2 = start2.plusHours(b2.duration());

                if (start1.isBefore(end2) && start2.isBefore(end1)) {
                    return true;
                }
            }
        }
        return false;
    }
}