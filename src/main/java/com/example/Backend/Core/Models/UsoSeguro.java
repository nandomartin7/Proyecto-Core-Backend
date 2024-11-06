package com.example.Backend.Core.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class UsoSeguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUso;

    @ManyToOne
    @JoinColumn(name = "idContrato", referencedColumnName = "idContrato")
    private Contrato contrato;

    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "Tipo de Uso")
    private String tipoUso;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "Monto Aprobado")
    private double montoAprobado;

    @Column(name = "estadoReclamo")
    private String estadoReclamo;

    public int getIdUso() {
        return idUso;
    }

    public void setIdUso(int idUso) {
        this.idUso = idUso;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoUso() {
        return tipoUso;
    }

    public void setTipoUso(String tipoUso) {
        this.tipoUso = tipoUso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMontoAprobado() {
        return montoAprobado;
    }

    public void setMontoAprobado(double montoAprobado) {
        this.montoAprobado = montoAprobado;
    }

    public String getEstadoReclamo() {
        return estadoReclamo;
    }

    public void setEstadoReclamo(String estadoReclamo) {
        this.estadoReclamo = estadoReclamo;
    }
}
