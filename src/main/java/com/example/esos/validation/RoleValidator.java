package com.example.esos.validation;

import com.example.esos.models.Role;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class RoleValidator implements ConstraintValidator<ValidRole, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        log.info("role value :" + value);
        Arrays.stream(Role.values())
                .forEach(role -> log.info("role in enum " + role));
        return Arrays.stream(Role.values())
                .anyMatch(role -> role.name().equals(value));
    }
}

