package com.example.bootcamp.domain.constants;

public class Constants {
    public static final String BOOTCAMP_ALREADY_EXISTS_CODE = "E-001";
    public static final String BOOTCAMP_NOT_FOUND_CODE = "E-002";
    public static final String INVALID_FIELD_CODE = "E-003";
    public static final String INTERNAL_ERROR_CODE = "E-004";

    public static final String INTERNAL_ERROR = "Something went wrong, please try again";
    public static final String PERSON_CREATED = "Person successfully registered with identification: {}";
    public static final String PERSON_NAME_REQUIRED = "Person name is required";
    public static final String PERSON_IDENTIFICATION_REQUIRED = "Person identification is required";
    public static final String PERSON_EMAIL_REQUIRED = "Person email is required";
    public static final String PERSON_EMAIL_INVALID = "The email format is invalid";
    public static final String PERSON_ALREADY_EXISTS = "Person with identification %s already exists";
    public static final String PERSON_EMAIL_ALREADY_EXISTS = "Person with email %s already exists";
    public static final String BOOTCAMPS_NOT_FOUND = "One or more of the requested bootcamps do not exist.";

    public static final String MAX_BOOTCAMPS_EXCEEDED = "A person can register for a maximum of 5 bootcamps at the same time.";
    public static final String BOOTCAMPS_SCHEDULE_OVERLAP = "The selected bootcamps overlap in schedule (date and duration).";
    public static final String BOOTCAMPS_REQUIRED = "At least one bootcamp ID is required.";
}