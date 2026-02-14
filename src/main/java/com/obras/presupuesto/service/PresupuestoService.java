package com.obras.presupuesto.service;

import com.obras.presupuesto.dto.CapituloRequest;
import com.obras.presupuesto.dto.CapituloResponse;
import com.obras.presupuesto.dto.CrearCapituloRequest;
import com.obras.presupuesto.dto.CrearPresupuestoRequest;
import com.obras.presupuesto.dto.PartidaRequest;
import com.obras.presupuesto.dto.PartidaResponse;
import com.obras.presupuesto.dto.PresupuestoListadoResponse;
import com.obras.presupuesto.dto.PresupuestoResponse;
import com.obras.presupuesto.exception.NotFoundException;
import com.obras.presupuesto.model.Capitulo;
import com.obras.presupuesto.model.Partida;
import com.obras.presupuesto.model.Presupuesto;
import com.obras.presupuesto.repository.CapituloRepository;
import com.obras.presupuesto.repository.PartidaRepository;
import com.obras.presupuesto.repository.PresupuestoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PresupuestoService {

    private final PresupuestoRepository presupuestoRepository;
    private final CapituloRepository capituloRepository;
    private final PartidaRepository partidaRepository;
    private final PdfService pdfService;

    public PresupuestoService(PresupuestoRepository presupuestoRepository,
                              CapituloRepository capituloRepository,
                              PartidaRepository partidaRepository,
                              PdfService pdfService) {
        this.presupuestoRepository = presupuestoRepository;
        this.capituloRepository = capituloRepository;
        this.partidaRepository = partidaRepository;
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

    @Transactional
    public CapituloResponse agregarCapitulo(Long presupuestoId, CrearCapituloRequest request) {
        Presupuesto presupuesto = getPresupuestoOrThrow(presupuestoId);

        Capitulo capitulo = new Capitulo();
        capitulo.setNombre(request.nombre());
        capitulo.setPresupuesto(presupuesto);

        Capitulo guardado = capituloRepository.save(capitulo);
        return toCapituloResponse(guardado);
    }

    @Transactional(readOnly = true)
    public List<CapituloResponse> listarCapitulos(Long presupuestoId) {
        getPresupuestoOrThrow(presupuestoId);
        return capituloRepository.findByPresupuestoId(presupuestoId).stream()
                .map(this::toCapituloResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CapituloResponse obtenerCapitulo(Long presupuestoId, Long capituloId) {
        Capitulo capitulo = capituloRepository.findByIdAndPresupuestoId(capituloId, presupuestoId)
                .orElseThrow(() -> new NotFoundException("No existe el capítulo con id " + capituloId + " para el presupuesto " + presupuestoId));
        return toCapituloResponse(capitulo);
    }

    @Transactional
    public CapituloResponse actualizarCapitulo(Long presupuestoId, Long capituloId, CrearCapituloRequest request) {
        Capitulo capitulo = capituloRepository.findByIdAndPresupuestoId(capituloId, presupuestoId)
                .orElseThrow(() -> new NotFoundException("No existe el capítulo con id " + capituloId + " para el presupuesto " + presupuestoId));
        capitulo.setNombre(request.nombre());
        return toCapituloResponse(capituloRepository.save(capitulo));
    }

    @Transactional
    public void eliminarCapitulo(Long presupuestoId, Long capituloId) {
        Capitulo capitulo = capituloRepository.findByIdAndPresupuestoId(capituloId, presupuestoId)
                .orElseThrow(() -> new NotFoundException("No existe el capítulo con id " + capituloId + " para el presupuesto " + presupuestoId));
        capituloRepository.delete(capitulo);
    }

    @Transactional
    public PartidaResponse agregarPartida(Long capituloId, PartidaRequest request) {
        Capitulo capitulo = getCapituloOrThrow(capituloId);

        Partida partida = new Partida();
        partida.setUnidadMedida(request.unidadMedida());
        partida.setReferencia(request.referencia());
        partida.setCantidad(request.cantidad());
        partida.setPrecio(request.precio());
        partida.setCapitulo(capitulo);

        Partida guardada = partidaRepository.save(partida);
        return toPartidaResponse(guardada);
    }

    @Transactional(readOnly = true)
    public List<PartidaResponse> listarPartidas(Long capituloId) {
        getCapituloOrThrow(capituloId);
        return partidaRepository.findByCapituloId(capituloId).stream()
                .map(this::toPartidaResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PartidaResponse obtenerPartida(Long capituloId, Long partidaId) {
        Partida partida = partidaRepository.findByIdAndCapituloId(partidaId, capituloId)
                .orElseThrow(() -> new NotFoundException("No existe la partida con id " + partidaId + " para el capítulo " + capituloId));
        return toPartidaResponse(partida);
    }

    @Transactional
    public PartidaResponse actualizarPartida(Long capituloId, Long partidaId, PartidaRequest request) {
        Partida partida = partidaRepository.findByIdAndCapituloId(partidaId, capituloId)
                .orElseThrow(() -> new NotFoundException("No existe la partida con id " + partidaId + " para el capítulo " + capituloId));

        partida.setUnidadMedida(request.unidadMedida());
        partida.setReferencia(request.referencia());
        partida.setCantidad(request.cantidad());
        partida.setPrecio(request.precio());

        return toPartidaResponse(partidaRepository.save(partida));
    }

    @Transactional
    public void eliminarPartida(Long capituloId, Long partidaId) {
        Partida partida = partidaRepository.findByIdAndCapituloId(partidaId, capituloId)
                .orElseThrow(() -> new NotFoundException("No existe la partida con id " + partidaId + " para el capítulo " + capituloId));
        partidaRepository.delete(partida);
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

    private Presupuesto getPresupuestoOrThrow(Long presupuestoId) {
        return presupuestoRepository.findById(presupuestoId)
                .orElseThrow(() -> new NotFoundException("No existe el presupuesto con id " + presupuestoId));
    }

    private Capitulo getCapituloOrThrow(Long capituloId) {
        return capituloRepository.findById(capituloId)
                .orElseThrow(() -> new NotFoundException("No existe el capítulo con id " + capituloId));
    }

    private CapituloResponse toCapituloResponse(Capitulo capitulo) {
        return new CapituloResponse(capitulo.getId(), capitulo.getNombre(), capitulo.getPresupuesto().getId());
    }

    private PartidaResponse toPartidaResponse(Partida partida) {
        return new PartidaResponse(
                partida.getId(),
                partida.getUnidadMedida(),
                partida.getReferencia(),
                partida.getCantidad(),
                partida.getPrecio(),
                partida.getCapitulo().getId()
        );
    }
}
