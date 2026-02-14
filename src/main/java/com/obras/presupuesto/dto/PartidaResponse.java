package com.obras.presupuesto.dto;

import java.math.BigDecimal;

public record PartidaResponse(Long id, String unidadMedida, String referencia, BigDecimal cantidad, BigDecimal precio, Long capituloId) {
}
