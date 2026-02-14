package com.obras.presupuesto.repository;

import com.obras.presupuesto.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartidaRepository extends JpaRepository<Partida, Long> {

    List<Partida> findByCapituloId(Long capituloId);

    Optional<Partida> findByIdAndCapituloId(Long id, Long capituloId);
}
