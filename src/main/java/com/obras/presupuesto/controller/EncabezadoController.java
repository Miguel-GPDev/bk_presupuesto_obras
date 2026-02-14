package com.obras.presupuesto.controller;

import com.obras.presupuesto.dto.EncabezadoRequest;
import com.obras.presupuesto.dto.EncabezadoResponse;
import com.obras.presupuesto.service.EncabezadoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/encabezados")
public class EncabezadoController {

    private final EncabezadoService encabezadoService;

    public EncabezadoController(EncabezadoService encabezadoService) {
        this.encabezadoService = encabezadoService;
    }

    @PostMapping("/{presupuestoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public EncabezadoResponse crear(@PathVariable @Positive Long presupuestoId,
                                    @Valid @RequestBody EncabezadoRequest request) {
        return encabezadoService.crear(presupuestoId, request);
    }

    @GetMapping
    public List<EncabezadoResponse> listar() {
        return encabezadoService.listar();
    }

    @GetMapping("/{presupuestoId}")
    public EncabezadoResponse obtener(@PathVariable @Positive Long presupuestoId) {
        return encabezadoService.obtenerPorPresupuesto(presupuestoId);
    }

    @PutMapping("/{presupuestoId}")
    public EncabezadoResponse actualizar(@PathVariable @Positive Long presupuestoId,
                                         @Valid @RequestBody EncabezadoRequest request) {
        return encabezadoService.actualizar(presupuestoId, request);
    }

    @DeleteMapping("/{presupuestoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable @Positive Long presupuestoId) {
        encabezadoService.eliminar(presupuestoId);
    }
}
