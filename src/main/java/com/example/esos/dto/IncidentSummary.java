package com.example.esos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentSummary {

    private int all;
    private int active;
    private int pending;
    private int resolved;
}
