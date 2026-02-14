package com.obras.presupuesto.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "partidas")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unidad_medida", nullable = false)
    private String unidadMedida;

    @Column(nullable = false)
    private String referencia;

    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal cantidad;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal precio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capitulo_id", nullable = false)
    private Capitulo capitulo;

    public Long getId() {
        return id;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Capitulo getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(Capitulo capitulo) {
        this.capitulo = capitulo;
    }

    @Transient
    public BigDecimal getSubtotal() {
        return precio.multiply(cantidad);
    }
}
