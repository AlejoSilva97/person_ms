package com.example.bootcamp.infrastructure.adapters.httpadapter;

import com.example.bootcamp.domain.constants.Constants;
import com.example.bootcamp.domain.exceptions.BootcampNotFoundException;
import com.example.bootcamp.domain.model.Bootcamp;
import com.example.bootcamp.domain.model.Capacity;
import com.example.bootcamp.domain.model.Technology;
import com.example.bootcamp.domain.spi.BootcampExternalService;
import com.example.bootcamp.infrastructure.adapters.httpadapter.dto.ExternalBootcampDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BootcampHttpAdapter implements BootcampExternalService {

    private final WebClient bootcampWebClient;

    public BootcampHttpAdapter(WebClient bootcampWebClient) {
        this.bootcampWebClient = bootcampWebClient;
    }

    @Override
    public Flux<Bootcamp> getBootcampsByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Flux.empty();
        }

        String idsParam = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        return bootcampWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/bootcamps/bulk")
                        .queryParam("ids", idsParam)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new BootcampNotFoundException(Constants.BOOTCAMPS_NOT_FOUND)))
                .bodyToFlux(ExternalBootcampDTO.class)
                .map(this::mapToDomain);
    }

    private Bootcamp mapToDomain(ExternalBootcampDTO dto) {
        List<Capacity> domainCapacities = dto.capacities() == null
                ? List.of()
                : dto.capacities().stream()
                .map(capDto -> new Capacity(
                        capDto.id(),
                        capDto.name(),
                        capDto.techs() == null ? List.of() : capDto.techs().stream()
                                .map(techDto -> new Technology(techDto.id(), techDto.name()))
                                .toList()
                ))
                .toList();

        return new Bootcamp(
                dto.id(),
                dto.name(),
                dto.description(),
                dto.launchDate(),
                dto.duration(),
                domainCapacities
        );
    }
}