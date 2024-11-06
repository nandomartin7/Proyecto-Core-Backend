package com.example.Backend.Core.Models;

import jakarta.persistence.*;

@Entity
public class PlanSeguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPlan;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Perdidas Parciales")
    private boolean perdidasParciales;

    @Column(name = "Valor Perdidas Parciales")
    private double valorPerdidasParciales;

    @Column(name = "Perdidas Totales")
    private boolean perdidasTotales;

    @Column(name = "Valor Perdidas Totales")
    private double valorPerdidasTotales;

    @Column(name = "Auxilios Mecanicos")
    private boolean auxilioMecanico;

    @Column(name = "Mantenimiento Automovil")
    private boolean mantenimientoVehicular;

    @Column(name = "Valor Plan")
    private double valorPlan;

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isPerdidasParciales() {
        return perdidasParciales;
    }

    public void setPerdidasParciales(boolean perdidasParciales) {
        this.perdidasParciales = perdidasParciales;
    }

    public double getValorPerdidasParciales() {
        return valorPerdidasParciales;
    }

    public void setValorPerdidasParciales(double valorPerdidasParciales) {
        this.valorPerdidasParciales = valorPerdidasParciales;
    }

    public boolean isPerdidasTotales() {
        return perdidasTotales;
    }

    public void setPerdidasTotales(boolean perdidasTotales) {
        this.perdidasTotales = perdidasTotales;
    }

    public double getValorPerdidasTotales() {
        return valorPerdidasTotales;
    }

    public void setValorPerdidasTotales(double valorPerdidasTotales) {
        this.valorPerdidasTotales = valorPerdidasTotales;
    }

    public boolean isAuxilioMecanico() {
        return auxilioMecanico;
    }

    public void setAuxilioMecanico(boolean auxilioMecanico) {
        this.auxilioMecanico = auxilioMecanico;
    }

    public boolean isMantenimientoVehicular() {
        return mantenimientoVehicular;
    }

    public void setMantenimientoVehicular(boolean mantenimientoVehicular) {
        this.mantenimientoVehicular = mantenimientoVehicular;
    }

    public double getValorPlan() {
        return valorPlan;
    }

    public void setValorPlan(double valorPlan) {
        this.valorPlan = valorPlan;
    }
}
