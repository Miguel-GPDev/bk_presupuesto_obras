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

    @GetMapping
    public List<PresupuestoListadoResponse> listar() {
        return presupuestoService.listarPresupuestos();
    }

    @PostMapping("/{presupuestoId}/capitulos")
    @ResponseStatus(HttpStatus.CREATED)
    public CapituloResponse agregarCapitulo(@PathVariable Long presupuestoId,
                                            @Valid @RequestBody CrearCapituloRequest request) {
        return presupuestoService.agregarCapitulo(presupuestoId, request);
    }

    @GetMapping("/{presupuestoId}/capitulos")
    public List<CapituloResponse> listarCapitulos(@PathVariable Long presupuestoId) {
        return presupuestoService.listarCapitulos(presupuestoId);
    }

    @GetMapping("/{presupuestoId}/capitulos/{capituloId}")
    public CapituloResponse obtenerCapitulo(@PathVariable Long presupuestoId, @PathVariable Long capituloId) {
        return presupuestoService.obtenerCapitulo(presupuestoId, capituloId);
    }

    @PutMapping("/{presupuestoId}/capitulos/{capituloId}")
    public CapituloResponse actualizarCapitulo(@PathVariable Long presupuestoId,
                                               @PathVariable Long capituloId,
                                               @Valid @RequestBody CrearCapituloRequest request) {
        return presupuestoService.actualizarCapitulo(presupuestoId, capituloId, request);
    }

    @DeleteMapping("/{presupuestoId}/capitulos/{capituloId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCapitulo(@PathVariable Long presupuestoId, @PathVariable Long capituloId) {
        presupuestoService.eliminarCapitulo(presupuestoId, capituloId);
    }

    @PostMapping("/capitulos/{capituloId}/partidas")
    @ResponseStatus(HttpStatus.CREATED)
    public PartidaResponse agregarPartida(@PathVariable Long capituloId,
                                          @Valid @RequestBody PartidaRequest request) {
        return presupuestoService.agregarPartida(capituloId, request);
    }

    @GetMapping("/capitulos/{capituloId}/partidas")
    public List<PartidaResponse> listarPartidas(@PathVariable Long capituloId) {
        return presupuestoService.listarPartidas(capituloId);
    }

    @GetMapping("/capitulos/{capituloId}/partidas/{partidaId}")
    public PartidaResponse obtenerPartida(@PathVariable Long capituloId, @PathVariable Long partidaId) {
        return presupuestoService.obtenerPartida(capituloId, partidaId);
    }

    @PutMapping("/capitulos/{capituloId}/partidas/{partidaId}")
    public PartidaResponse actualizarPartida(@PathVariable Long capituloId,
                                             @PathVariable Long partidaId,
                                             @Valid @RequestBody PartidaRequest request) {
        return presupuestoService.actualizarPartida(capituloId, partidaId, request);
    }

    @DeleteMapping("/capitulos/{capituloId}/partidas/{partidaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarPartida(@PathVariable Long capituloId, @PathVariable Long partidaId) {
        presupuestoService.eliminarPartida(capituloId, partidaId);
    }
}
