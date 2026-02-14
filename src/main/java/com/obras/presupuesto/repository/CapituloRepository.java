package com.obras.presupuesto.repository;

import com.obras.presupuesto.model.Capitulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CapituloRepository extends JpaRepository<Capitulo, Long> {

    List<Capitulo> findByPresupuestoId(Long presupuestoId);

    Optional<Capitulo> findByIdAndPresupuestoId(Long id, Long presupuestoId);
}
