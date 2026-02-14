package com.obras.presupuesto.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PartidaRequest(
        @NotBlank String unidadMedida,
        @NotBlank String referencia,
        @NotNull @DecimalMin("0.0") BigDecimal cantidad,
        @NotNull @DecimalMin("0.0") BigDecimal precio
) {}
