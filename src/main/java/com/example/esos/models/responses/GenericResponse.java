package com.example.esos.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponse {

    private String status;
    private String message;

}
