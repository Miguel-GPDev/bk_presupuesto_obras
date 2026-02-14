package com.obras.presupuesto.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CrearPresupuestoRequest(
        @NotBlank @Size(max = 120) String nombre,
        @NotEmpty List<@Valid CapituloRequest> capitulos,
        @NotNull @Valid EncabezadoRequest empresa,
        @NotNull @Valid ClienteRequest cliente
) {}
