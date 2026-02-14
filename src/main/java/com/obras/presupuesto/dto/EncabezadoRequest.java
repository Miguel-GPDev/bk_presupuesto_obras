package com.obras.presupuesto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EncabezadoRequest(
        @NotBlank String nombreEmpresa,
        @NotBlank String cif,
        @NotBlank String direccion,
        @NotBlank String telefono,
        @NotBlank @Email String email
) {
}
