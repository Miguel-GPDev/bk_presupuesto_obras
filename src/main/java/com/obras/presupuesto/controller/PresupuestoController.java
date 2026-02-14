package com.obras.presupuesto.controller;

import com.obras.presupuesto.dto.CapituloResponse;
import com.obras.presupuesto.dto.CrearCapituloRequest;
import com.obras.presupuesto.dto.CrearPresupuestoRequest;
import com.obras.presupuesto.dto.PartidaRequest;
import com.obras.presupuesto.dto.PartidaResponse;
import com.obras.presupuesto.dto.PresupuestoListadoResponse;
import com.obras.presupuesto.dto.PresupuestoResponse;
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


    @PostMapping("/{presupuestoId}/capitulos")
    @ResponseStatus(HttpStatus.CREATED)
    public CapituloResponse agregarCapitulo(@PathVariable Long presupuestoId,
                                            @Valid @RequestBody CrearCapituloRequest request) {
        return presupuestoService.agregarCapitulo(presupuestoId, request);
    }

    @PostMapping("/capitulos/{capituloId}/partidas")
    @ResponseStatus(HttpStatus.CREATED)
    public PartidaResponse agregarPartida(@PathVariable Long capituloId,
                                          @Valid @RequestBody PartidaRequest request) {
        return presupuestoService.agregarPartida(capituloId, request);
    }

    @GetMapping
    public List<PresupuestoListadoResponse> listar() {
        return presupuestoService.listarPresupuestos();
    }
}
