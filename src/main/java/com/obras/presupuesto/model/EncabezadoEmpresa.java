package com.obras.presupuesto.model;

import jakarta.persistence.*;

@Entity
@Table(name = "encabezados_empresa")
public class EncabezadoEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_empresa", nullable = false)
    private String nombreEmpresa;

    @Column(nullable = false)
    private String cif;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "presupuesto_id", nullable = false, unique = true)
    private Presupuesto presupuesto;

    public Long getId() {
        return id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }
}
