package com.obras.presupuesto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearCapituloRequest(@NotBlank @Size(max = 120) String nombre) {
}
