package com.example.Backend.Core.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Automovil {
    @Id
    private String idAutomovil;

    @Column (name = "anio")
    private int anio;

    @Column(name = "Tipo de automovil")
    private String tipo;

    @Column(name = "Marca")
    private String marca;

    @Column(name = "Modelo")
    private String modelo;

    @Column(name = "Ciudad Circulacion")
    private String ciudadCirculacion;

    @Column(name = "Uso del Automovil")
    private String usoDestinado;

    public String getIdAutomovil() {
        return idAutomovil;
    }

    public void setIdAutomovil(String idAutomovil) {
        this.idAutomovil = idAutomovil;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCiudadCirculacion() {
        return ciudadCirculacion;
    }

    public void setCiudadCirculacion(String ciudadCirculacion) {
        this.ciudadCirculacion = ciudadCirculacion;
    }

    public String getUsoDestinado() {
        return usoDestinado;
    }

    public void setUsoDestinado(String usoDestinado) {
        this.usoDestinado = usoDestinado;
    }
}
