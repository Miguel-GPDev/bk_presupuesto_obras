package com.obras.presupuesto.dto;

public record EncabezadoResponse(
        Long id,
        Long presupuestoId,
        String nombreEmpresa,
        String cif,
        String direccion,
        String telefono,
        String email
) {
}
