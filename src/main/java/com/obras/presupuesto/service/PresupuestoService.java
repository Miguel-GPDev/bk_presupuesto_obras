package com.obras.presupuesto.service;

import com.obras.presupuesto.dto.CapituloRequest;
import com.obras.presupuesto.dto.CrearPresupuestoRequest;
import com.obras.presupuesto.dto.PartidaRequest;
import com.obras.presupuesto.dto.PresupuestoListadoResponse;
import com.obras.presupuesto.dto.PresupuestoResponse;
import com.obras.presupuesto.model.Capitulo;
import com.obras.presupuesto.model.Partida;
import com.obras.presupuesto.model.Presupuesto;
import com.obras.presupuesto.repository.PresupuestoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PresupuestoService {

    private final PresupuestoRepository presupuestoRepository;
    private final PdfService pdfService;

    public PresupuestoService(PresupuestoRepository presupuestoRepository, PdfService pdfService) {
        this.presupuestoRepository = presupuestoRepository;
        this.pdfService = pdfService;
    }

    @Transactional
    public PresupuestoResponse crearPresupuesto(CrearPresupuestoRequest request) {
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setNombre(request.nombre());

        for (CapituloRequest capituloRequest : request.capitulos()) {
            Capitulo capitulo = new Capitulo();
            capitulo.setNombre(capituloRequest.nombre());

            for (PartidaRequest partidaRequest : capituloRequest.partidas()) {
                Partida partida = new Partida();
                partida.setUnidadMedida(partidaRequest.unidadMedida());
                partida.setReferencia(partidaRequest.referencia());
                partida.setCantidad(partidaRequest.cantidad());
                partida.setPrecio(partidaRequest.precio());
                capitulo.addPartida(partida);
            }
            presupuesto.addCapitulo(capitulo);
        }

        Presupuesto guardado = presupuestoRepository.save(presupuesto);
        Presupuesto completo = presupuestoRepository.findWithDetailById(guardado.getId())
                .orElseThrow(() -> new IllegalStateException("No se encontró el presupuesto recién creado"));

        String pdfPath = pdfService.generarPdf(completo);
        BigDecimal total = calcularTotal(completo);
        return new PresupuestoResponse(completo.getId(), completo.getNombre(), pdfPath, total);
    }

    @Transactional(readOnly = true)
    public List<PresupuestoListadoResponse> listarPresupuestos() {
        return presupuestoRepository.findAll().stream()
                .map(presupuesto -> new PresupuestoListadoResponse(
                        presupuesto.getId(), presupuesto.getNombre(), presupuesto.getFechaCreacion()))
                .toList();
    }

    private BigDecimal calcularTotal(Presupuesto presupuesto) {
        return presupuesto.getCapitulos().stream()
                .flatMap(capitulo -> capitulo.getPartidas().stream())
                .map(Partida::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
