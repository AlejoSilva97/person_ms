package com.example.bootcamp.infrastructure.adapters.persistenceadapter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "person_bootcamp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonBootcampEntity {
    @Id
    private Long id;
    @Column("id_person")
    private Long idPerson;
    @Column("id_bootcamp")
    private Long idBootcamp;
}