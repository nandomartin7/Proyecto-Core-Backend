package com.example.Backend.Core.Service;

import com.example.Backend.Core.Models.PlanSeguro;
import com.example.Backend.Core.Repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {
    @Autowired
    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }


    public List<PlanSeguro> findAll(){
        return planRepository.findAll();
    }

    public PlanSeguro findByIdPlan(int idPlan){
        return planRepository.findByIdPlan(idPlan);
    }

    public PlanSeguro registrarPlan(PlanSeguro planSeguro) throws Exception {
        //Validacion nombre
        if (!planSeguro.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{1,30}$")) {
            throw new Exception("El nombre contiene caracteres no permitidos: " + planSeguro.getNombre());
        }

        //Validacion Perdidas Parciales
        if(!planSeguro.isPerdidasParciales()){
            planSeguro.setValorPerdidasParciales(0.00);
        }else {
            if (planSeguro.getValorPerdidasParciales()<0 || (planSeguro.getValorPerdidasParciales() * 100) % 1 != 0) {
                throw new Exception("El valor de perdidas parciales "+planSeguro.getValorPerdidasParciales()+" es incorrecto");
            }
        }

        //Validacion Perdidas Totales
        if (!planSeguro.isPerdidasTotales()){
            planSeguro.setValorPerdidasTotales(0.00);
        }else {
            if (planSeguro.getValorPerdidasTotales()<0 || (planSeguro.getValorPerdidasTotales() * 100) % 1 != 0) {
                throw new Exception("El valor de perdidas totales "+planSeguro.getValorPerdidasTotales()+" es incorrecto");
            }
        }

        //Validacion Valor Plan
        if (planSeguro.getValorPlan()<=0 || (planSeguro.getValorPlan() * 100) % 1 != 0) {
            throw new Exception("El valor del plan "+planSeguro.getValorPlan()+" es incorrecto");
        }

        return planRepository.save(planSeguro);
    }

    public PlanSeguro updatePlan(int idPlan, PlanSeguro planSeguro) throws Exception{
        PlanSeguro existe = findByIdPlan(idPlan);
        if (existe != null){
            //Validacion nombre
            if (!planSeguro.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{1,30}$")) {
                throw new Exception("El nombre contiene caracteres no permitidos: " + planSeguro.getNombre());
            }

            //Validacion Perdidas Parciales
            if(!planSeguro.isPerdidasParciales()){
                planSeguro.setValorPerdidasParciales(0.00);
            }else {
                if (planSeguro.getValorPerdidasParciales()<0 || (existe.getValorPerdidasParciales() * 100) % 1 != 0) {
                    throw new Exception("El valor de perdidas parciales "+existe.getValorPerdidasParciales()+" es incorrecto");
                }
            }

            //Validacion Perdidas Totales
            if (!planSeguro.isPerdidasTotales()){
                planSeguro.setValorPerdidasTotales(0.00);
            }else {
                if (planSeguro.getValorPerdidasTotales()<0 || (existe.getValorPerdidasTotales() * 100) % 1 != 0) {
                    throw new Exception("El valor de perdidas totales "+existe.getValorPerdidasTotales()+" es incorrecto");
                }
            }

            //Validacion Valor Plan
            if (planSeguro.getValorPlan()<=0 || (existe.getValorPlan() * 100) % 1 != 0) {
                throw new Exception("El valor del plan "+existe.getValorPlan()+" es incorrecto");
            }

            existe.setNombre(planSeguro.getNombre());
            existe.setPerdidasParciales(planSeguro.isPerdidasParciales());
            existe.setValorPerdidasParciales(planSeguro.getValorPerdidasParciales());
            existe.setPerdidasTotales(planSeguro.isPerdidasTotales());
            existe.setValorPerdidasTotales(planSeguro.getValorPerdidasTotales());
            existe.setAuxilioMecanico(planSeguro.isAuxilioMecanico());
            existe.setMantenimientoVehicular(planSeguro.isMantenimientoVehicular());
            existe.setValorPlan(planSeguro.getValorPlan());
            return planRepository.save(existe);
        }else {
            throw new Exception("El plan con el ID"+idPlan+" no existe");
        }
    }

    public boolean deletePlan (int idPlan) throws  Exception{
        PlanSeguro existe = findByIdPlan(idPlan);
        if (existe != null){
            planRepository.delete(existe);
            return true;
        } else {
            throw new Exception("El plan con el ID"+idPlan+" no existe");
        }
    }
}
