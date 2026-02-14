package com.obras.presupuesto.repository;

import com.obras.presupuesto.model.Presupuesto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {

    @EntityGraph(attributePaths = {"capitulos", "capitulos.partidas", "encabezadoEmpresa", "cliente"})
    Optional<Presupuesto> findWithDetailById(Long id);
}
