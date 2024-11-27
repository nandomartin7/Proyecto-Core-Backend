package com.example.Backend.Core.Models;

import java.util.List;
import java.util.Map;

public class AnalisisContrato {
    // Evaluación del Automóvil
    private int calificacionAnioAutomovil;
    private int calificacionTipoAutomovil;
    private int calificacionCiudadCirculacionAutomovil;
    private int calificacionUsoDestinadoAutomovil;
    private int evaluacionAutomovil;

    //Evaluación del Historial de Usos
    private int frecuenciaTotalUsos;
    private int calificacionFrecuenciaUsoSeguro;
    private double montoTotal;
    private int calificacionMontoTotalAprobado;
    private double tiempoMesesPromedio;
    private int calificacionPromedioTiempoEntreUso;
    private int evaluacionHistorialUsos;

    //Evaluacion con clientes similares
    private double calificacionPromedioUsosSimilares;
    private int usosCliente;
    private double usosSimilares;
    private double calificacionPromedioMontoAprobadoSimilares;
    private double montoAprobadoCliente;
    private double montoAprobadoSimilares;
    private Map<Long, List<UsoSeguro>> contratosSimilares;
    private int evaluacionClientesSimilares;

    //Factor Excelencia
    private int calificacionFactorExcelencia;
    private double mesesAcumuladosSinUsos;

    //Calculo final
    private double valorTotalU;
    private double umbralAceptacion;
    private double porcentajePenalidad;

    public int getCalificacionAnioAutomovil() {
        return calificacionAnioAutomovil;
    }

    public void setCalificacionAnioAutomovil(int calificacionAnioAutomovil) {
        this.calificacionAnioAutomovil = calificacionAnioAutomovil;
    }

    public int getCalificacionTipoAutomovil() {
        return calificacionTipoAutomovil;
    }

    public void setCalificacionTipoAutomovil(int calificacionTipoAutomovil) {
        this.calificacionTipoAutomovil = calificacionTipoAutomovil;
    }

    public int getCalificacionCiudadCirculacionAutomovil() {
        return calificacionCiudadCirculacionAutomovil;
    }

    public void setCalificacionCiudadCirculacionAutomovil(int calificacionCiudadCirculacionAutomovil) {
        this.calificacionCiudadCirculacionAutomovil = calificacionCiudadCirculacionAutomovil;
    }

    public int getCalificacionUsoDestinadoAutomovil() {
        return calificacionUsoDestinadoAutomovil;
    }

    public void setCalificacionUsoDestinadoAutomovil(int calificacionUsoDestinadoAutomovil) {
        this.calificacionUsoDestinadoAutomovil = calificacionUsoDestinadoAutomovil;
    }

    public int getEvaluacionAutomovil() {
        return evaluacionAutomovil;
    }

    public void setEvaluacionAutomovil(int evaluacionAutomovil) {
        this.evaluacionAutomovil = evaluacionAutomovil;
    }

    public int getFrecuenciaTotalUsos() {
        return frecuenciaTotalUsos;
    }

    public void setFrecuenciaTotalUsos(int frecuenciaTotalUsos) {
        this.frecuenciaTotalUsos = frecuenciaTotalUsos;
    }

    public int getCalificacionFrecuenciaUsoSeguro() {
        return calificacionFrecuenciaUsoSeguro;
    }

    public void setCalificacionFrecuenciaUsoSeguro(int calificacionFrecuenciaUsoSeguro) {
        this.calificacionFrecuenciaUsoSeguro = calificacionFrecuenciaUsoSeguro;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public int getCalificacionMontoTotalAprobado() {
        return calificacionMontoTotalAprobado;
    }

    public void setCalificacionMontoTotalAprobado(int calificacionMontoTotalAprobado) {
        this.calificacionMontoTotalAprobado = calificacionMontoTotalAprobado;
    }

    public double getTiempoMesesPromedio() {
        return tiempoMesesPromedio;
    }

    public void setTiempoMesesPromedio(double tiempoMesesPromedio) {
        this.tiempoMesesPromedio = tiempoMesesPromedio;
    }

    public int getCalificacionPromedioTiempoEntreUso() {
        return calificacionPromedioTiempoEntreUso;
    }

    public void setCalificacionPromedioTiempoEntreUso(int calificacionPromedioTiempoEntreUso) {
        this.calificacionPromedioTiempoEntreUso = calificacionPromedioTiempoEntreUso;
    }

    public int getEvaluacionHistorialUsos() {
        return evaluacionHistorialUsos;
    }

    public void setEvaluacionHistorialUsos(int evaluacionHistorialUsos) {
        this.evaluacionHistorialUsos = evaluacionHistorialUsos;
    }

    public double getCalificacionPromedioUsosSimilares() {
        return calificacionPromedioUsosSimilares;
    }

    public void setCalificacionPromedioUsosSimilares(double calificacionPromedioUsosSimilares) {
        this.calificacionPromedioUsosSimilares = calificacionPromedioUsosSimilares;
    }

    public int getUsosCliente() {
        return usosCliente;
    }

    public void setUsosCliente(int usosCliente) {
        this.usosCliente = usosCliente;
    }

    public double getUsosSimilares() {
        return usosSimilares;
    }

    public void setUsosSimilares(double usosSimilares) {
        this.usosSimilares = usosSimilares;
    }

    public double getCalificacionPromedioMontoAprobadoSimilares() {
        return calificacionPromedioMontoAprobadoSimilares;
    }

    public void setCalificacionPromedioMontoAprobadoSimilares(double calificacionPromedioMontoAprobadoSimilares) {
        this.calificacionPromedioMontoAprobadoSimilares = calificacionPromedioMontoAprobadoSimilares;
    }

    public double getMontoAprobadoCliente() {
        return montoAprobadoCliente;
    }

    public void setMontoAprobadoCliente(double montoAprobadoCliente) {
        this.montoAprobadoCliente = montoAprobadoCliente;
    }

    public double getMontoAprobadoSimilares() {
        return montoAprobadoSimilares;
    }

    public void setMontoAprobadoSimilares(double montoAprobadoSimilares) {
        this.montoAprobadoSimilares = montoAprobadoSimilares;
    }

    public Map<Long, List<UsoSeguro>> getContratosSimilares() {
        return contratosSimilares;
    }

    public void setContratosSimilares(Map<Long, List<UsoSeguro>> contratosSimilares) {
        this.contratosSimilares = contratosSimilares;
    }

    public int getEvaluacionClientesSimilares() {
        return evaluacionClientesSimilares;
    }

    public void setEvaluacionClientesSimilares(int evaluacionClientesSimilares) {
        this.evaluacionClientesSimilares = evaluacionClientesSimilares;
    }

    public int getCalificacionFactorExcelencia() {
        return calificacionFactorExcelencia;
    }

    public void setCalificacionFactorExcelencia(int calificacionFactorExcelencia) {
        this.calificacionFactorExcelencia = calificacionFactorExcelencia;
    }

    public double getMesesAcumuladosSinUsos() {
        return mesesAcumuladosSinUsos;
    }

    public void setMesesAcumuladosSinUsos(double mesesAcumuladosSinUsos) {
        this.mesesAcumuladosSinUsos = mesesAcumuladosSinUsos;
    }

    public double getValorTotalU() {
        return valorTotalU;
    }

    public void setValorTotalU(double valorTotalU) {
        this.valorTotalU = valorTotalU;
    }

    public double getUmbralAceptacion() {
        return umbralAceptacion;
    }

    public void setUmbralAceptacion(double umbralAceptacion) {
        this.umbralAceptacion = umbralAceptacion;
    }

    public double getPorcentajePenalidad() {
        return porcentajePenalidad;
    }

    public void setPorcentajePenalidad(double porcentajePenalidad) {
        this.porcentajePenalidad = porcentajePenalidad;
    }
}
