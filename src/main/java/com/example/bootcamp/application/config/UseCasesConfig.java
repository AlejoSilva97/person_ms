package com.example.bootcamp.application.config;

import com.example.bootcamp.domain.spi.PersonPersistencePort;
import com.example.bootcamp.domain.spi.BootcampExternalService;
import com.example.bootcamp.domain.usecase.PersonUseCase;
import com.example.bootcamp.domain.api.PersonServicePort;
import com.example.bootcamp.infrastructure.adapters.persistenceadapter.mapper.PersonEntityMapper;
import com.example.bootcamp.infrastructure.adapters.persistenceadapter.repository.PersonRepository;
import com.example.bootcamp.infrastructure.adapters.persistenceadapter.repository.PersonBootcampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {
        private final PersonRepository personRepository;
        private final PersonBootcampRepository personBootcampRepository;
        private final PersonEntityMapper personEntityMapper;

        @Bean
        public PersonServicePort bootcampServicePort(PersonPersistencePort personPersistencePort,
                                                     BootcampExternalService bootcampExternalService) {
            return new PersonUseCase(personPersistencePort, bootcampExternalService);
        }
}
