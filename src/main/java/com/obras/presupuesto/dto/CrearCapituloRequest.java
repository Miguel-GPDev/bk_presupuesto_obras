package com.obras.presupuesto.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearCapituloRequest(@NotBlank String nombre) {
}
