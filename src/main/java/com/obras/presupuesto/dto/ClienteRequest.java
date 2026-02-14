package com.obras.presupuesto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequest(
        @NotBlank String nombreCliente,
        @NotBlank String documento,
        @NotBlank String direccion,
        @NotBlank String telefono,
        @NotBlank @Email String email
) {
}
