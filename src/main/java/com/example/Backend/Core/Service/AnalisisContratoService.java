package com.example.Backend.Core.Service;

import com.example.Backend.Core.Models.AnalisisContrato;
import com.example.Backend.Core.Models.Automovil;
import com.example.Backend.Core.Models.Contrato;
import com.example.Backend.Core.Models.UsoSeguro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalisisContratoService {
    @Autowired
    private final ContratoService contratoService;
    private final AutomovilService automovilService;
    private final UsoSeguroService usoSeguroService;

    public AnalisisContratoService(ContratoService contratoService, AutomovilService automovilService, UsoSeguroService usoSeguroService, PlanService planService) {
        this.contratoService = contratoService;
        this.automovilService = automovilService;
        this.usoSeguroService = usoSeguroService;
    }

    private AnalisisContrato evaluacionAutomovil(Contrato contrato, AnalisisContrato analisisContrato){
        Automovil automovil = contrato.getAutomovil();

        //Analisis del año
        if (automovil.getAnio() <= 2024 && automovil.getAnio() > 2020){
            analisisContrato.setCalificacionAnioAutomovil(0);
        }else if (automovil.getAnio() <= 2019 && automovil.getAnio() > 2010){
            analisisContrato.setCalificacionAnioAutomovil(10);
        } else if (automovil.getAnio() <= 2009 && automovil.getAnio() > 2000){
            analisisContrato.setCalificacionAnioAutomovil(20);
        } else if (automovil.getAnio() <= 2000){
            analisisContrato.setCalificacionAnioAutomovil(30);
        }

        //Analisis del tipo de automovil
        if (automovil.getTipo().equals("Automóvil")){
            analisisContrato.setCalificacionTipoAutomovil(0);
        } else if (automovil.getTipo().equals("Jeep / Caminoneta")){
            analisisContrato.setCalificacionTipoAutomovil(10);
        }  else if (automovil.getTipo().equals("Deportivo")){
            analisisContrato.setCalificacionTipoAutomovil(20);
        }

        //Ciudad de Circulación
        if (automovil.getCiudadCirculacion().equals("Quito")){
            analisisContrato.setCalificacionCiudadCirculacionAutomovil(10);
        } else if (automovil.getCiudadCirculacion().equals("Guayaquil")){
            analisisContrato.setCalificacionCiudadCirculacionAutomovil(20);
        }

        //Uso destinado
        if (automovil.getUsoDestinado().equals("Familiar")){
            analisisContrato.setCalificacionUsoDestinadoAutomovil(10);
        } else if (automovil.getUsoDestinado().equals("Trabajo")){
            analisisContrato.setCalificacionUsoDestinadoAutomovil(20);
        } else if (automovil.getUsoDestinado().equals("Carga / Comercio")){
            analisisContrato.setCalificacionUsoDestinadoAutomovil(30);
        }

        //Evaluacion del Automovil
        int evaluacionAutomovil= analisisContrato.getCalificacionAnioAutomovil() + analisisContrato.getCalificacionTipoAutomovil()
                + analisisContrato.getCalificacionCiudadCirculacionAutomovil() + analisisContrato.getCalificacionUsoDestinadoAutomovil();
        analisisContrato.setEvaluacionAutomovil(evaluacionAutomovil);
        return analisisContrato;
    }

    private AnalisisContrato evaluacionHistorialUsos (Contrato contrato, AnalisisContrato analisisContrato){
        List<UsoSeguro> usos = usoSeguroService.findByContrato(contrato.getIdContrato());

        int frecuenciaUso = 0;
        double montoTotal = 0.0;
        double tPromedio = 0.0;
        double pesoTotal = 0.0;
        List<UsoSeguro> usosRelevantes = new ArrayList<>();

        //Filtrar los usos de seguro aprobados
        for (UsoSeguro usoSeguro : usos){
            if (!usoSeguro.getTipoUso().equals("Mantenimiento") && !usoSeguro.getTipoUso().equals("Auxilio mecanico") && usoSeguro.getEstadoReclamo().equals("Aprobado")){
                frecuenciaUso++;
                montoTotal += usoSeguro.getMontoAprobado();
                usosRelevantes.add(usoSeguro);
            }
        }
        usosRelevantes.sort(Comparator.comparing(UsoSeguro::getFecha));

        //Frecuencias de Uso
        analisisContrato.setFrecuenciaTotalUsos(frecuenciaUso);
        if (frecuenciaUso == 0){
            analisisContrato.setCalificacionFrecuenciaUsoSeguro(-10);
        } else if (frecuenciaUso >= 1 && frecuenciaUso <= 2){
            analisisContrato.setCalificacionFrecuenciaUsoSeguro(0);
        } else if (frecuenciaUso >= 3 && frecuenciaUso <= 4){
            analisisContrato.setCalificacionFrecuenciaUsoSeguro(15);
        } else if (frecuenciaUso > 4){
            analisisContrato.setCalificacionFrecuenciaUsoSeguro(30);
        }

        //Monto total Aprobado
        analisisContrato.setMontoTotal(montoTotal);
        if (montoTotal < 1000){
            analisisContrato.setCalificacionMontoTotalAprobado(0);
        } else if (montoTotal >= 1000 && montoTotal <= 5000){
            analisisContrato.setCalificacionMontoTotalAprobado(15);
        } if (montoTotal > 5000){
            analisisContrato.setCalificacionMontoTotalAprobado(30);
        }

        //Promedio de Tiempo entre usos del seguro
        if(usosRelevantes.size() > 1){
            Date fechaActual = new Date();

            for (int i = 0; i < usosRelevantes.size()-1; i++){
                UsoSeguro actual = usosRelevantes.get(i);
                UsoSeguro siguiente = usosRelevantes.get(i+1);

                long diferenciaEnMilis = Math.abs(siguiente.getFecha().getTime() - actual.getFecha().getTime());
                double tiempoMeses = (double) diferenciaEnMilis / (1000.0 * 60 * 60 * 24 * 30);

                double peso = switch (actual.getTipoUso()){
                    case "Choque grave" -> 2.0;
                    case "Choque leve" -> 1.5;
                    case "Daño electrico", "Daño mecanico no grave" -> 1.0;
                    default -> 0.0;
                };

                tPromedio += tiempoMeses * peso;
                pesoTotal += peso;

                System.out.println("Tiempo entre usos (meses): " + tiempoMeses);
                System.out.println("Peso del uso actual: " + peso);
                System.out.println("Acumulado tPromedio: " + tPromedio);
            }

            // Cálculo entre el último uso y la fecha actual
            UsoSeguro ultimoUso = usosRelevantes.get(usosRelevantes.size() - 1);
            long diferenciaUltimoUso = Math.abs(fechaActual.getTime() - ultimoUso.getFecha().getTime());
            double tiempoMesesUltimoUso = (double) diferenciaUltimoUso / (1000.0 * 60 * 60 * 24 * 30);

            double pesoUltimoUso = switch (ultimoUso.getTipoUso()) {
                case "Choque grave" -> 2.0;
                case "Choque leve" -> 1.5;
                case "Daño electrico", "Daño mecanico no grave" -> 1.0;
                default -> 0.0;
            };

            tPromedio += tiempoMesesUltimoUso * pesoUltimoUso;
            pesoTotal += pesoUltimoUso;

            System.out.println("Tiempo desde el último uso a la fecha actual (meses): " + tiempoMesesUltimoUso);
            System.out.println("Peso del último uso: " + pesoUltimoUso);
            System.out.println("Acumulado tPromedio (incluido último uso): " + tPromedio);


            tPromedio = (pesoTotal > 0) ? tPromedio / pesoTotal : 0;
        }

        analisisContrato.setTiempoMesesPromedio(tPromedio);
        if (tPromedio <= 3) {
            analisisContrato.setCalificacionPromedioTiempoEntreUso(40);
        } else if (tPromedio > 3 && tPromedio <= 6) {
            analisisContrato.setCalificacionPromedioTiempoEntreUso(20);
        } else if (tPromedio > 6 && tPromedio <= 12) {
            analisisContrato.setCalificacionPromedioTiempoEntreUso(10);
        } else if (tPromedio > 12) {
            analisisContrato.setCalificacionPromedioTiempoEntreUso(0);
        }

        //Evaluación del Historial Usos Seguro
        int evaluaciohistorialUsos= analisisContrato.getCalificacionFrecuenciaUsoSeguro()
                + analisisContrato.getCalificacionMontoTotalAprobado()
                + analisisContrato.getCalificacionPromedioTiempoEntreUso();

        analisisContrato.setEvaluacionHistorialUsos(evaluaciohistorialUsos);
        return analisisContrato;

    }

    private AnalisisContrato evaluacionClientesSimilares(Contrato contrato, AnalisisContrato analisisContrato) {
        // Obtener el automóvil asociado al contrato actual
        Automovil automovilActual = contrato.getAutomovil();

        // Encontrar automóviles similares excluyendo el actual
        List<Automovil> automovilesSimilares = automovilService.findBySimilares(
                        automovilActual.getAnio(),
                        automovilActual.getMarca(),
                        automovilActual.getModelo(),
                        automovilActual.getUsoDestinado()
                ).stream()
                .filter(auto -> !auto.getIdAutomovil().equals(automovilActual.getIdAutomovil()))
                .collect(Collectors.toList());

        // Crear un map para almacenar contratos similares y sus usos relevantes
        Map<Long, List<UsoSeguro>> contratosConUsos = new HashMap<>();

        // Recorrer automóviles similares y obtener contratos asociados
        for (Automovil autoSimilar : automovilesSimilares) {
            // Obtener el contrato asociado al automóvil similar
            Contrato contratoSimilar = contratoService.findByAutomovil(autoSimilar.getIdAutomovil());

            if (contratoSimilar != null && contratoSimilar.getPlanSeguro().getIdPlan() == contrato.getPlanSeguro().getIdPlan()) {
                // Obtener usos relevantes
                List<UsoSeguro> usosRelevantes = usoSeguroService.findByContrato(contratoSimilar.getIdContrato())
                        .stream()
                        .filter(uso -> (!uso.getTipoUso().equals("Mantenimiento") &&
                                !uso.getTipoUso().equals("Auxilio mecanico")) && uso.getEstadoReclamo().equals("Aprobado"))
                        .collect(Collectors.toList());

                // Agregar al map
                contratosConUsos.put(contratoSimilar.getIdContrato(), usosRelevantes);
            }
        }

        // Calcular totales y promedios para contratos similares
        int totalUsosSimilares = 0;
        double totalMontoAprobadoSimilares = 0.0;
        int totalContratosConUsos = 0;

        for (Map.Entry<Long, List<UsoSeguro>> entry : contratosConUsos.entrySet()) {
            List<UsoSeguro> usosRelevantes = entry.getValue();

            // Sumar totales
            totalUsosSimilares += usosRelevantes.size();
            totalMontoAprobadoSimilares += usosRelevantes.stream().mapToDouble(UsoSeguro::getMontoAprobado).sum();
            if (!usosRelevantes.isEmpty()) {
                totalContratosConUsos++;
            }
        }

        // Calcular promedios
        double promedioUsosSimilares = totalContratosConUsos > 0 ? (double) totalUsosSimilares / totalContratosConUsos : 0;
        double promedioMontoAprobadoSimilares = totalContratosConUsos > 0 ? totalMontoAprobadoSimilares / totalContratosConUsos : 0;

        // Usos del cliente actual
        List<UsoSeguro> usosClienteActual = usoSeguroService.findByContrato(contrato.getIdContrato())
                .stream()
                .filter(uso -> (!uso.getTipoUso().equals("Mantenimiento") && !uso.getTipoUso().equals("Auxilio mecanico")) && uso.getEstadoReclamo().equals("Aprobado"))
                .collect(Collectors.toList());

        int totalUsosCliente = usosClienteActual.size();
        double montoAprobadoCliente = usosClienteActual.stream().mapToDouble(UsoSeguro::getMontoAprobado).sum();

        // Evaluar promedios
        int evaluacionUsos = totalUsosCliente > promedioUsosSimilares ? 50 : 0;
        int evaluacionMontos = montoAprobadoCliente > promedioMontoAprobadoSimilares ? 50 : 0;

        // Asignar resultados al análisis
        analisisContrato.setCalificacionPromedioUsosSimilares(evaluacionUsos);
        analisisContrato.setUsosCliente(totalUsosCliente);
        analisisContrato.setUsosSimilares(promedioUsosSimilares);
        analisisContrato.setCalificacionPromedioMontoAprobadoSimilares(evaluacionMontos);
        analisisContrato.setMontoAprobadoCliente(montoAprobadoCliente);
        analisisContrato.setMontoAprobadoSimilares(promedioMontoAprobadoSimilares);
        // Asignar el Map al análisis de contrato
        analisisContrato.setContratosSimilares(contratosConUsos);
        analisisContrato.setEvaluacionClientesSimilares(evaluacionUsos + evaluacionMontos);


        return analisisContrato;
    }

    private AnalisisContrato calcularFactorExcelencia(Contrato contrato, AnalisisContrato analisisContrato) {
        // Obtener los usos del seguro asociados al contrato
        List<UsoSeguro> usos = usoSeguroService.findByContrato(contrato.getIdContrato());

        // Filtrar los usos relevantes (excluyendo mantenimientos y auxilios mecánicos)
        List<UsoSeguro> usosRelevantes = usos.stream()
                .filter(uso -> (!uso.getTipoUso().equals("Mantenimiento") && !uso.getTipoUso().equals("Auxilio mecanico")) && uso.getEstadoReclamo().equals("Aprobado"))
                .collect(Collectors.toList());

        // Ordenar los usos relevantes por fecha
        usosRelevantes.sort(Comparator.comparing(UsoSeguro::getFecha));

        // Determinar el periodo de tiempo desde el inicio del contrato
        Date fechaInicioContrato = contrato.getFechaInicio();
        Date fechaActual = new Date();

        long mesesSinSiniestros = 0;

        if (!usosRelevantes.isEmpty()) {
            // Determinar los periodos entre los usos relevantes
            Date ultimaFechaUso = fechaInicioContrato;

            for (UsoSeguro uso : usosRelevantes) {
                long diferenciaEnMilis = uso.getFecha().getTime() - ultimaFechaUso.getTime();
                mesesSinSiniestros += (diferenciaEnMilis / (1000L * 60 * 60 * 24 * 30));
                ultimaFechaUso = uso.getFecha();
            }

            // Contar el tiempo desde el último uso hasta la fecha actual
            long diferenciaDesdeUltimoUso = fechaActual.getTime() - usosRelevantes.get(usosRelevantes.size() - 1).getFecha().getTime();
            mesesSinSiniestros += (diferenciaDesdeUltimoUso / (1000L * 60 * 60 * 24 * 30));
        } else {
            // Si no hay usos relevantes, todo el tiempo desde el inicio del contrato cuenta
            long diferenciaTotal = fechaActual.getTime() - fechaInicioContrato.getTime();
            mesesSinSiniestros = diferenciaTotal / (1000L * 60 * 60 * 24 * 30);
        }

        // Convertir meses acumulados a años completos
        int añosSinSiniestros = (int) (mesesSinSiniestros / 12);

        // Calcular el factor de excelencia (2% por cada año sin siniestros importantes)
        int factorExcelencia = añosSinSiniestros * 2;

        // Asignar el factor de excelencia al análisis
        analisisContrato.setMesesAcumuladosSinUsos(mesesSinSiniestros);
        analisisContrato.setCalificacionFactorExcelencia(factorExcelencia);

        return analisisContrato;
    }

    public AnalisisContrato realizarAnalisisContrato(Contrato contrato) {
        // Crear una nueva instancia del modelo de análisis
        AnalisisContrato analisisContrato = new AnalisisContrato();

        // Paso 1: Evaluación del Automóvil
        evaluacionAutomovil(contrato, analisisContrato);

        // Paso 2: Evaluación del Historial de Usos
        evaluacionHistorialUsos(contrato, analisisContrato);

        // Paso 3: Evaluación con Clientes Similares
        evaluacionClientesSimilares(contrato, analisisContrato);

        // Paso 4: Calcular Factor de Excelencia
        calcularFactorExcelencia(contrato, analisisContrato);

        // Paso 5: Calcular el Valor Total de U
        double valorU = (0.30 * analisisContrato.getEvaluacionAutomovil()) +
                (0.40 * analisisContrato.getEvaluacionHistorialUsos()) +
                (0.20 * analisisContrato.getEvaluacionClientesSimilares()) -
                analisisContrato.getCalificacionFactorExcelencia();

        analisisContrato.setValorTotalU(valorU);

        // Paso 6: Determinar el Porcentaje de Penalidad o Bonificación
        double umbralAceptacion = 35.0; // Umbral predeterminado, se puede ajustar
        analisisContrato.setUmbralAceptacion(umbralAceptacion);

        double porcentajePenalidad;
        if (valorU > umbralAceptacion + 10) {
            porcentajePenalidad = 1.5; // Penalidad del 1.5%
        } else if (valorU > umbralAceptacion && valorU <= umbralAceptacion + 10) {
            porcentajePenalidad = 1.1; // Penalidad del 1.1%
        } else if (valorU < umbralAceptacion - 10) {
            porcentajePenalidad = -1.2; // Bonificación del 1.2%
        } else {
            porcentajePenalidad = 0.0; // Sin ajuste
        }

        analisisContrato.setPorcentajePenalidad(porcentajePenalidad);

        return analisisContrato;
    }

}
