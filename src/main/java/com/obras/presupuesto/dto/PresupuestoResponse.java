package com.obras.presupuesto.dto;

import java.math.BigDecimal;

public record PresupuestoResponse(Long id, String nombre, String pdfGenerado, BigDecimal total) {}
