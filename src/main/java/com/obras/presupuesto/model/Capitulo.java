package com.obras.presupuesto.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "capitulos")
public class Capitulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "presupuesto_id", nullable = false)
    private Presupuesto presupuesto;

    @OneToMany(mappedBy = "capitulo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Partida> partidas = new ArrayList<>();

    public void addPartida(Partida partida) {
        partidas.add(partida);
        partida.setCapitulo(this);
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }
}
