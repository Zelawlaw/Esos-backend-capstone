package com.example.esos.models.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidentCreate {

    @NotEmpty(message = "Message should not be empty")
    @NotNull(message = "Message should not be null")
    private String message;

}
