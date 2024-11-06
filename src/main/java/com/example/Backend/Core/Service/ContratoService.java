package com.example.Backend.Core.Service;

import com.example.Backend.Core.Models.Automovil;
import com.example.Backend.Core.Models.Cliente;
import com.example.Backend.Core.Models.Contrato;
import com.example.Backend.Core.Models.PlanSeguro;
import com.example.Backend.Core.Repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContratoService {
    @Autowired
    private final ContratoRepository contratoRepository;
    private final AutomovilService automovilService;
    private final ClienteService clienteService;
    private final PlanService planService;

    public ContratoService(ContratoRepository contratoRepository, AutomovilService automovilService, ClienteService clienteService, PlanService planService) {
        this.contratoRepository = contratoRepository;
        this.automovilService = automovilService;
        this.clienteService = clienteService;
        this.planService = planService;
    }

    public List<Contrato> findAll(){
        return contratoRepository.findAll();
    }

    public Contrato findByIdContrato (Long idContrato){
        return contratoRepository.findByIdContrato(idContrato);
    }

    public Contrato registrarContrato(Contrato contrato) throws Exception{
        //Ajustar fecha de inicio
        Date fechaInicioAjustada = ajustarFecha(contrato.getFechaInicio());
        contrato.setFechaInicio(fechaInicioAjustada);

        Date fechaFinalizacionAjustada = ajustarFecha(contrato.getFechaFinalizacion());
        contrato.setFechaFinalizacion(fechaFinalizacionAjustada);

        //Validacion del automovil
        Automovil automovil = automovilService.findByIdAutomovil(contrato.getAutomovil().getIdAutomovil());
        if (automovil == null){
            throw new Exception("El automóvil no existe");
        }

        //Validacion del cliente
        Cliente cliente = clienteService.findByIdCliente(contrato.getCliente().getIdCliente());
        if (cliente == null){
            throw new Exception("El cliente no existe");
        }

        //Validacion del Plan de Seguro
        PlanSeguro plan = planService.findByIdPlan(contrato.getPlanSeguro().getIdPlan());
        if (plan == null){
            throw new Exception("El plan de seguro no existe");
        }

        //Validacion FechaFinalizacion del contrato
        if (contrato.getFechaInicio().after(contrato.getFechaFinalizacion())){
            throw new Exception("La fecha de Finalizacion debe ser mayor que la fecha de Inicio");
        }

        contrato.setAutomovil(automovil);
        contrato.setCliente(cliente);
        contrato.setPlanSeguro(plan);
        contrato.setRenovacionContrato(false); // Si no se va a renovar, puedes definirlo como `false`
        contrato.setFechaRenovacion(null); // Si no se necesita la fecha de renovación, asigna `null`
        contrato.setValoresAgregados(0.0); // Asigna `0.0` si no hay valores agregados
        contrato.setMotivoAgregados("");
        contrato.setValorsubtotal(contrato.getPlanSeguro().getValorPlan());
        contrato.setValortotal(contrato.getValorsubtotal());
        return contratoRepository.save(contrato);
    }

    public Contrato updateContrato(Long idContrato, Contrato contratoActualizar) throws Exception{
        Contrato existe = findByIdContrato(idContrato);
        if (existe != null){
            System.out.println("Fecha de Finalizacion: " + contratoActualizar.getFechaFinalizacion());
            System.out.println("Fecha de Renovacion: " + contratoActualizar.getFechaRenovacion());
            
            Date fechaRenovacionActualizada = null;
            Date fechaFinalizacionAjustada = ajustarFecha(contratoActualizar.getFechaFinalizacion());
            contratoActualizar.setFechaFinalizacion(fechaFinalizacionAjustada);

            //Validacion Renovacion de Contrato y Fecha de renovacion
            if (contratoActualizar.isRenovacionContrato()){
                if(existe.getFechaRenovacion()!=null && existe.getFechaFinalizacion().equals(contratoActualizar.getFechaFinalizacion())){
                    fechaRenovacionActualizada = existe.getFechaRenovacion();
                    contratoActualizar.setFechaFinalizacion(existe.getFechaFinalizacion());
                }else {
                    fechaRenovacionActualizada = existe.getFechaFinalizacion();
                    if (contratoActualizar.getFechaFinalizacion().before(fechaRenovacionActualizada)) {
                        throw new Exception("la nueva fecha de finalizacion debe ser posterior a la fecha de renovacion: " + contratoActualizar.getFechaRenovacion());
                    }
                }
            }

            //Validacion valores agregados
            if (existe.getValoresAgregados()<0 || (existe.getValoresAgregados() * 100) % 1 != 0) {
                throw new Exception("El valor de valores agregados "+existe.getValoresAgregados()+" es incorrecto");
            }

            //Validacion valor Subtotal
            if (existe.getValorsubtotal()<=0 || (existe.getValorsubtotal() * 100) % 1 != 0) {
                throw new Exception("El valor de subtotal "+existe.getValorsubtotal()+" es incorrecto");
            }

            //Validacion Valor Total
            if (existe.getValortotal()<=0 || (existe.getValortotal() * 100) % 1 != 0) {
                throw new Exception("El valor total "+existe.getValortotal()+" es incorrecto");
            }

            Date fechaRenovacionAjustada = ajustarFecha(fechaRenovacionActualizada);
            contratoActualizar.setFechaRenovacion(fechaRenovacionAjustada);

            fechaFinalizacionAjustada = ajustarFecha(contratoActualizar.getFechaFinalizacion());
            contratoActualizar.setFechaFinalizacion(fechaFinalizacionAjustada);

            existe.setFechaFinalizacion(contratoActualizar.getFechaFinalizacion());
            existe.setRenovacionContrato(contratoActualizar.isRenovacionContrato());
            existe.setFechaRenovacion(contratoActualizar.getFechaRenovacion());
            existe.setValoresAgregados(contratoActualizar.getValoresAgregados());
            existe.setMotivoAgregados(contratoActualizar.getMotivoAgregados());
            existe.setValorsubtotal(contratoActualizar.getValorsubtotal());
            existe.setValortotal(existe.getValorsubtotal()+(existe.getValorsubtotal()*existe.getValoresAgregados()));
            return contratoRepository.save(existe);
        }else {
            throw new Exception("El contrato con ID:"+idContrato+" no existe");
        }
    }

    public boolean deleteContrato (Long idContrato) throws Exception{
        Contrato existe = findByIdContrato(idContrato);
        if (existe != null){
            contratoRepository.delete(existe);
            return true;
        }else {
            throw new Exception("El contrato con ID:"+idContrato+" no existe");
        }
    }

    public List<Contrato> findByPlan(int planSeguro){
        List<Contrato> contratos = findAll();
        List<Contrato> contratosPlan = new ArrayList<>();

        for (Contrato contrato: contratos){
            if (contrato.getPlanSeguro().getIdPlan() == planSeguro){
                contratosPlan.add(contrato);
            }
        }
        return contratosPlan;
    }

    public List<Contrato> findByFechas (Date fechaInicial, Date fechaFinal){
        List<Contrato> contratosEnRango = findAll().stream()
                .filter(contrato -> contrato.getFechaInicio().compareTo(fechaInicial)>= 0 &&
                        contrato.getFechaInicio().compareTo(fechaFinal)<=0)
                .collect(Collectors.toList());
        return contratosEnRango;
    }

    private Date ajustarFecha(Date fechaOriginal){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaOriginal);

        calendar.add(Calendar.HOUR_OF_DAY,5);
        return calendar.getTime();
    }
}
