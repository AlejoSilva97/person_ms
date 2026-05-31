package com.example.bootcamp.infrastructure.entrypoints;

import com.example.bootcamp.infrastructure.entrypoints.dto.PersonRequestDTO;
import com.example.bootcamp.infrastructure.entrypoints.dto.PersonResponseDTO;
import com.example.bootcamp.infrastructure.entrypoints.handler.PersonHandlerImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/persons",
                    method = RequestMethod.POST,
                    beanClass = PersonHandlerImpl.class,
                    beanMethod = "createPerson",
                    operation = @Operation(
                            summary = "Registrar una nueva persona e inscribirla a múltiples bootcamps",
                            description = "Valida la identificación única, formato de email, un límite máximo de hasta 5 bootcamps simultáneos sin solapamiento de horarios.",
                            operationId = "createPerson",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = PersonRequestDTO.class))
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Person registered successfully",
                                            content = @Content(schema = @Schema(implementation = PersonResponseDTO.class))
                                    ),
                                    @ApiResponse(responseCode = "400", description = "Campos inválidos, formato de correo erróneo o solapamiento de agendas"),
                                    @ApiResponse(responseCode = "404", description = "Uno o más de los bootcamps solicitados no existen en el sistema externo"),
                                    @ApiResponse(responseCode = "409", description = "Ya existe una persona registrada con la misma identificación")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(PersonHandlerImpl personHandler) {
        return route(POST("/persons"), personHandler::createPerson);
    }
}