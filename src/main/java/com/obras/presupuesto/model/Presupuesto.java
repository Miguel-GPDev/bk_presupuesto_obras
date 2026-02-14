package com.obras.presupuesto.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "presupuestos")
public class Presupuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "presupuesto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Capitulo> capitulos = new ArrayList<>();

    @OneToOne(mappedBy = "presupuesto", cascade = CascadeType.ALL, orphanRemoval = true)
    private EncabezadoEmpresa encabezadoEmpresa;

    @OneToOne(mappedBy = "presupuesto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cliente cliente;

    @PrePersist
    public void prePersist() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }

    public void addCapitulo(Capitulo capitulo) {
        capitulos.add(capitulo);
        capitulo.setPresupuesto(this);
    }

    public void setEncabezadoEmpresa(EncabezadoEmpresa encabezadoEmpresa) {
        this.encabezadoEmpresa = encabezadoEmpresa;
        if (encabezadoEmpresa != null) {
            encabezadoEmpresa.setPresupuesto(this);
        }
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        if (cliente != null) {
            cliente.setPresupuesto(this);
        }
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public List<Capitulo> getCapitulos() {
        return capitulos;
    }

    public EncabezadoEmpresa getEncabezadoEmpresa() {
        return encabezadoEmpresa;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
