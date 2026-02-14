package com.obras.presupuesto.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PartidaRequest(
        @NotBlank @Size(max = 30) String unidadMedida,
        @NotBlank @Size(max = 80) String referencia,
        @NotNull @DecimalMin("0.0") @Digits(integer = 10, fraction = 2) BigDecimal cantidad,
        @NotNull @DecimalMin("0.0") @Digits(integer = 10, fraction = 2) BigDecimal precio
) {}
