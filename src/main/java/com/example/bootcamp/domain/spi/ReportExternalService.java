package com.example.bootcamp.domain.spi;

import com.example.bootcamp.domain.model.Person;
import reactor.core.publisher.Mono;

public interface ReportExternalService {
    Mono<Void> sendEnrollmentToReport(Person person);
}