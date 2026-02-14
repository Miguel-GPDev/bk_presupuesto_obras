package com.obras.presupuesto.dto;

public record ClienteResponse(
        Long id,
        Long presupuestoId,
        String nombreCliente,
        String documento,
        String direccion,
        String telefono,
        String email
) {
}
