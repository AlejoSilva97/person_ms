package com.example.bootcamp.infrastructure.entrypoints.handler;

import com.example.bootcamp.domain.api.PersonServicePort;
import com.example.bootcamp.domain.constants.Constants;
import com.example.bootcamp.infrastructure.entrypoints.dto.PersonRequestDTO;
import com.example.bootcamp.infrastructure.entrypoints.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class PersonHandlerImpl {

    private final PersonServicePort personServicePort;
    private final PersonMapper personMapper;

    public Mono<ServerResponse> createPerson(ServerRequest request) {
        return request.bodyToMono(PersonRequestDTO.class)
                .map(personMapper::toModel)
                .flatMap(personServicePort::registerPerson)
                .doOnSuccess(savedPerson -> log.info(Constants.PERSON_CREATED, savedPerson.identification()))
                .flatMap(savedPerson -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(personMapper.toResponseDTO(savedPerson)));
    }
}