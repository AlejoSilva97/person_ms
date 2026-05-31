package com.example.bootcamp.infrastructure.adapters.persistenceadapter.mapper;

import com.example.bootcamp.domain.model.Person;
import com.example.bootcamp.infrastructure.adapters.persistenceadapter.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonEntityMapper {

    @Mapping(target = "id", source = "person.id")
    @Mapping(target = "name", source = "person.name")
    PersonEntity toEntity(Person person);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "bootcampIds", source = "bootcampIds")
    Person toModel(PersonEntity entity, List<Long> bootcampIds);
}