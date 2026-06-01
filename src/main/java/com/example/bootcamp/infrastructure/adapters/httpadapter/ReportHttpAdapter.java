package com.example.bootcamp.infrastructure.adapters.httpadapter;

import com.example.bootcamp.domain.model.Person;
import com.example.bootcamp.domain.spi.ReportExternalService;
import com.example.bootcamp.infrastructure.adapters.httpadapter.dto.ReportEnrollmentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ReportHttpAdapter implements ReportExternalService {

    private final WebClient reportWebClient;

    public ReportHttpAdapter(WebClient reportWebClient) {
        this.reportWebClient = reportWebClient;
    }

    @Override
    public Mono<Void> sendEnrollmentToReport(Person person) {
        ReportEnrollmentDTO requestBody = new ReportEnrollmentDTO(
                person.id(),
                person.name(),
                person.identification(),
                person.email(),
                person.bootcampIds()
        );

        return reportWebClient.post()
                .uri("/reports/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .toBodilessEntity()
                .doOnSuccess(v -> log.info("Sincronización enviada a report-ms para la persona: {}", person.identification()))
                .doOnError(e -> log.error("No se pudo sincronizar con report-ms (pero el guardado local fue exitoso): {}", e.getMessage()))
                .then();
    }
}