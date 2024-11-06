package com.example.Backend.Core.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrato;

    @OneToOne
    @JoinColumn(name = "idAutomovil", referencedColumnName = "idAutomovil")
    private Automovil automovil;

    @ManyToOne
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idPlan", referencedColumnName = "idPlan")
    private PlanSeguro planSeguro;

    @Column(name = "Fecha Inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "Fecha Finalizacion")
    @Temporal(TemporalType.DATE)
    private Date fechaFinalizacion;

    @Column(name = "Renovar Contrato")
    private boolean renovacionContrato;

    @Column(name = "Fecha Renovacion")
    @Temporal(TemporalType.DATE)
    private Date fechaRenovacion;

    @Column(name = "Valores Agregados")
    private double valoresAgregados;

    @Column(name = "Motivo Agregados")
    private String motivoAgregados;

    @Column(name = "Subtotal")
    private double valorsubtotal;

    @Column(name = "Total")
    private double valortotal;

    public Long getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Long idContrato) {
        this.idContrato = idContrato;
    }

    public Automovil getAutomovil() {
        return automovil;
    }

    public void setAutomovil(Automovil automovil) {
        this.automovil = automovil;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public PlanSeguro getPlanSeguro() {
        return planSeguro;
    }

    public void setPlanSeguro(PlanSeguro planSeguro) {
        this.planSeguro = planSeguro;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public boolean isRenovacionContrato() {
        return renovacionContrato;
    }

    public void setRenovacionContrato(boolean renovacionContrato) {
        this.renovacionContrato = renovacionContrato;
    }

    public Date getFechaRenovacion() {
        return fechaRenovacion;
    }

    public void setFechaRenovacion(Date fechaRenovacion) {
        this.fechaRenovacion = fechaRenovacion;
    }

    public double getValoresAgregados() {
        return valoresAgregados;
    }

    public void setValoresAgregados(double valoresAgregados) {
        this.valoresAgregados = valoresAgregados;
    }

    public String getMotivoAgregados() {
        return motivoAgregados;
    }

    public void setMotivoAgregados(String motivoAgregados) {
        this.motivoAgregados = motivoAgregados;
    }

    public double getValorsubtotal() {
        return valorsubtotal;
    }

    public void setValorsubtotal(double valorsubtotal) {
        this.valorsubtotal = valorsubtotal;
    }

    public double getValortotal() {
        return valortotal;
    }

    public void setValortotal(double valortotal) {
        this.valortotal = valortotal;
    }
}
