package com.obras.presupuesto.service;

import com.obras.presupuesto.dto.ClienteRequest;
import com.obras.presupuesto.dto.ClienteResponse;
import com.obras.presupuesto.exception.NotFoundException;
import com.obras.presupuesto.model.Cliente;
import com.obras.presupuesto.model.Presupuesto;
import com.obras.presupuesto.repository.ClienteRepository;
import com.obras.presupuesto.repository.PresupuestoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PresupuestoRepository presupuestoRepository;

    public ClienteService(ClienteRepository clienteRepository,
                          PresupuestoRepository presupuestoRepository) {
        this.clienteRepository = clienteRepository;
        this.presupuestoRepository = presupuestoRepository;
    }

    @Transactional
    public ClienteResponse crear(Long presupuestoId, ClienteRequest request) {
        Presupuesto presupuesto = presupuestoRepository.findById(presupuestoId)
                .orElseThrow(() -> new NotFoundException("No existe el presupuesto con id " + presupuestoId));

        if (clienteRepository.existsByPresupuestoId(presupuestoId)) {
            throw new IllegalArgumentException("El presupuesto " + presupuestoId + " ya tiene cliente asociado");
        }

        Cliente cliente = new Cliente();
        cliente.setPresupuesto(presupuesto);
        mapRequest(request, cliente);

        return toResponse(clienteRepository.save(cliente));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> listar() {
        return clienteRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponse obtenerPorPresupuesto(Long presupuestoId) {
        Cliente cliente = clienteRepository.findByPresupuestoId(presupuestoId)
                .orElseThrow(() -> new NotFoundException("No existe cliente para el presupuesto " + presupuestoId));
        return toResponse(cliente);
    }

    @Transactional
    public ClienteResponse actualizar(Long presupuestoId, ClienteRequest request) {
        Cliente cliente = clienteRepository.findByPresupuestoId(presupuestoId)
                .orElseThrow(() -> new NotFoundException("No existe cliente para el presupuesto " + presupuestoId));
        mapRequest(request, cliente);
        return toResponse(clienteRepository.save(cliente));
    }

    @Transactional
    public void eliminar(Long presupuestoId) {
        Cliente cliente = clienteRepository.findByPresupuestoId(presupuestoId)
                .orElseThrow(() -> new NotFoundException("No existe cliente para el presupuesto " + presupuestoId));
        clienteRepository.delete(cliente);
    }

    private void mapRequest(ClienteRequest request, Cliente cliente) {
        cliente.setNombreCliente(request.nombreCliente());
        cliente.setDocumento(request.documento());
        cliente.setDireccion(request.direccion());
        cliente.setTelefono(request.telefono());
        cliente.setEmail(request.email());
    }

    private ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getPresupuesto().getId(),
                cliente.getNombreCliente(),
                cliente.getDocumento(),
                cliente.getDireccion(),
                cliente.getTelefono(),
                cliente.getEmail()
        );
    }
}
