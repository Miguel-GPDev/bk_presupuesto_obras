package com.obras.presupuesto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequest(
        @NotBlank @Size(max = 120) String nombreCliente,
        @NotBlank @Size(min = 5, max = 30) String documento,
        @NotBlank @Size(max = 200) String direccion,
        @NotBlank @Size(min = 7, max = 20) String telefono,
        @NotBlank @Email @Size(max = 120) String email
) {
}
