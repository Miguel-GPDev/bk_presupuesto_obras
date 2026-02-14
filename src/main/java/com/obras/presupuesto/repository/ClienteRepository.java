package com.obras.presupuesto.repository;

import com.obras.presupuesto.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByPresupuestoId(Long presupuestoId);

    boolean existsByPresupuestoId(Long presupuestoId);
}
