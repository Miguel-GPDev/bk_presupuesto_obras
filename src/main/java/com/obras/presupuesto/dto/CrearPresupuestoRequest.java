package com.obras.presupuesto.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CrearPresupuestoRequest(
        @NotBlank String nombre,
        @NotEmpty List<@Valid CapituloRequest> capitulos,
        @Valid EncabezadoRequest empresa,
        @Valid ClienteRequest cliente
) {}
