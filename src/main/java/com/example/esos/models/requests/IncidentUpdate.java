package com.example.esos.models.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidentUpdate {

    @NotEmpty(message = "update should not be empty")
    @NotNull(message = "update should not be null")
    String update;

    @NotEmpty(message = "incidentId should not be empty")
    @NotNull(message = "incidentId should not be null")
    String incidentId;

    String status;

   public IncidentUpdate( String update,String incidentId)
    {
        this.incidentId = incidentId;
        this.update = update;
    }
}
