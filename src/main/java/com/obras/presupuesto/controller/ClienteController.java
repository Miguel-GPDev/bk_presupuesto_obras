package com.obras.presupuesto.controller;

import com.obras.presupuesto.dto.ClienteRequest;
import com.obras.presupuesto.dto.ClienteResponse;
import com.obras.presupuesto.service.ClienteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/{presupuestoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse crear(@PathVariable @Positive Long presupuestoId,
                                 @Valid @RequestBody ClienteRequest request) {
        return clienteService.crear(presupuestoId, request);
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        return clienteService.listar();
    }

    @GetMapping("/{presupuestoId}")
    public ClienteResponse obtener(@PathVariable @Positive Long presupuestoId) {
        return clienteService.obtenerPorPresupuesto(presupuestoId);
    }

    @PutMapping("/{presupuestoId}")
    public ClienteResponse actualizar(@PathVariable @Positive Long presupuestoId,
                                      @Valid @RequestBody ClienteRequest request) {
        return clienteService.actualizar(presupuestoId, request);
    }

    @DeleteMapping("/{presupuestoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable @Positive Long presupuestoId) {
        clienteService.eliminar(presupuestoId);
    }
}
