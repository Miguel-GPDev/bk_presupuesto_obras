package com.obras.presupuesto.repository;

import com.obras.presupuesto.model.EncabezadoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EncabezadoEmpresaRepository extends JpaRepository<EncabezadoEmpresa, Long> {

    Optional<EncabezadoEmpresa> findByPresupuestoId(Long presupuestoId);

    boolean existsByPresupuestoId(Long presupuestoId);
}
