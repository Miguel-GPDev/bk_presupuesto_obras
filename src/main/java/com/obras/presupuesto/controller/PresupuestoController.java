package com.obras.presupuesto.controller;

import com.obras.presupuesto.dto.CrearPresupuestoRequest;
import com.obras.presupuesto.dto.PresupuestoResponse;
import com.obras.presupuesto.dto.PresupuestoListadoResponse;
import com.obras.presupuesto.service.PresupuestoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presupuestos")
public class PresupuestoController {

    private final PresupuestoService presupuestoService;

    public PresupuestoController(PresupuestoService presupuestoService) {
        this.presupuestoService = presupuestoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PresupuestoResponse crear(@Valid @RequestBody CrearPresupuestoRequest request) {
        return presupuestoService.crearPresupuesto(request);
    }

    @GetMapping
    public List<PresupuestoListadoResponse> listar() {
        return presupuestoService.listarPresupuestos();
    }
}
