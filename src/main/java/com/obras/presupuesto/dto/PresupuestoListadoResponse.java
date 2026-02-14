package com.obras.presupuesto.dto;

import java.time.LocalDateTime;

public record PresupuestoListadoResponse(Long id, String nombre, LocalDateTime fechaCreacion) {}
