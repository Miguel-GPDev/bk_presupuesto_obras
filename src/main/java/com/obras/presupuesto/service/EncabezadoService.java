package com.obras.presupuesto.service;

import com.obras.presupuesto.dto.EncabezadoRequest;
import com.obras.presupuesto.dto.EncabezadoResponse;
import com.obras.presupuesto.exception.NotFoundException;
import com.obras.presupuesto.model.EncabezadoEmpresa;
import com.obras.presupuesto.model.Presupuesto;
import com.obras.presupuesto.repository.EncabezadoEmpresaRepository;
import com.obras.presupuesto.repository.PresupuestoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EncabezadoService {

    private final EncabezadoEmpresaRepository encabezadoEmpresaRepository;
    private final PresupuestoRepository presupuestoRepository;

    public EncabezadoService(EncabezadoEmpresaRepository encabezadoEmpresaRepository,
                             PresupuestoRepository presupuestoRepository) {
        this.encabezadoEmpresaRepository = encabezadoEmpresaRepository;
        this.presupuestoRepository = presupuestoRepository;
    }

    @Transactional
    public EncabezadoResponse crear(Long presupuestoId, EncabezadoRequest request) {
        Presupuesto presupuesto = presupuestoRepository.findById(presupuestoId)
                .orElseThrow(() -> new NotFoundException("No existe el presupuesto con id " + presupuestoId));

        if (encabezadoEmpresaRepository.existsByPresupuestoId(presupuestoId)) {
            throw new IllegalArgumentException("El presupuesto " + presupuestoId + " ya tiene encabezado de empresa");
        }

        EncabezadoEmpresa encabezado = new EncabezadoEmpresa();
        encabezado.setPresupuesto(presupuesto);
        mapRequest(request, encabezado);

        return toResponse(encabezadoEmpresaRepository.save(encabezado));
    }

    @Transactional(readOnly = true)
    public EncabezadoResponse obtenerPorPresupuesto(Long presupuestoId) {
        EncabezadoEmpresa encabezado = encabezadoEmpresaRepository.findByPresupuestoId(presupuestoId)
                .orElseThrow(() -> new NotFoundException("No existe encabezado para el presupuesto " + presupuestoId));
        return toResponse(encabezado);
    }

    @Transactional(readOnly = true)
    public List<EncabezadoResponse> listar() {
        return encabezadoEmpresaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public EncabezadoResponse actualizar(Long presupuestoId, EncabezadoRequest request) {
        EncabezadoEmpresa encabezado = encabezadoEmpresaRepository.findByPresupuestoId(presupuestoId)
                .orElseThrow(() -> new NotFoundException("No existe encabezado para el presupuesto " + presupuestoId));
        mapRequest(request, encabezado);
        return toResponse(encabezadoEmpresaRepository.save(encabezado));
    }

    @Transactional
    public void eliminar(Long presupuestoId) {
        EncabezadoEmpresa encabezado = encabezadoEmpresaRepository.findByPresupuestoId(presupuestoId)
                .orElseThrow(() -> new NotFoundException("No existe encabezado para el presupuesto " + presupuestoId));
        encabezadoEmpresaRepository.delete(encabezado);
    }

    private void mapRequest(EncabezadoRequest request, EncabezadoEmpresa encabezado) {
        encabezado.setNombreEmpresa(request.nombreEmpresa());
        encabezado.setCif(request.cif());
        encabezado.setDireccion(request.direccion());
        encabezado.setTelefono(request.telefono());
        encabezado.setEmail(request.email());
    }

    private EncabezadoResponse toResponse(EncabezadoEmpresa encabezado) {
        return new EncabezadoResponse(
                encabezado.getId(),
                encabezado.getPresupuesto().getId(),
                encabezado.getNombreEmpresa(),
                encabezado.getCif(),
                encabezado.getDireccion(),
                encabezado.getTelefono(),
                encabezado.getEmail()
        );
    }
}
