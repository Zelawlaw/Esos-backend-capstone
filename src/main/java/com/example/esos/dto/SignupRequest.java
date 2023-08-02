package com.example.esos.dto;


import com.example.esos.validation.ValidRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @NotBlank(message = "Username cannot be empty or null")
    private String username;

    @NotBlank(message = "Password cannot be empty or null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain a number")
    private String password;

    @NotBlank(message = "Role cannot be empty or null")
    @ValidRole
    private String role;

    @Min(value = 1, message = "Manager ID should be a number")
    private Integer managerId;

}
