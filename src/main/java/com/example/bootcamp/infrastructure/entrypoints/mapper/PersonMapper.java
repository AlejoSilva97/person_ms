package com.example.bootcamp.infrastructure.entrypoints.mapper;

import com.example.bootcamp.domain.model.Person;
import com.example.bootcamp.infrastructure.entrypoints.dto.PersonRequestDTO;
import com.example.bootcamp.infrastructure.entrypoints.dto.PersonResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "id", ignore = true)
    Person toModel(PersonRequestDTO dto);

    PersonResponseDTO toResponseDTO(Person person);
}