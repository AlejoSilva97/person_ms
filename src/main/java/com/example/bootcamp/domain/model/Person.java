package com.example.bootcamp.domain.model;

import com.example.bootcamp.domain.constants.Constants;
import com.example.bootcamp.domain.exceptions.InvalidFieldException;
import java.util.List;
import java.util.regex.Pattern;

public record Person(
        Long id,
        String name,
        String identification,
        String email,
        List<Long> bootcampIds
) {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    public Person {
        if (name == null || name.isBlank()) {
            throw new InvalidFieldException(Constants.PERSON_NAME_REQUIRED);
        }

        if (identification == null || identification.isBlank()) {
            throw new InvalidFieldException(Constants.PERSON_IDENTIFICATION_REQUIRED);
        }

        if (email == null || email.isBlank()) {
            throw new InvalidFieldException(Constants.PERSON_EMAIL_REQUIRED);
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidFieldException(Constants.PERSON_EMAIL_INVALID);
        }

        if (bootcampIds == null || bootcampIds.isEmpty()) {
            throw new InvalidFieldException(Constants.BOOTCAMPS_REQUIRED);
        }

        if (bootcampIds.size() > 5) {
            throw new InvalidFieldException(Constants.MAX_BOOTCAMPS_EXCEEDED);
        }
    }
}