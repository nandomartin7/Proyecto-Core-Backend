package com.example.Backend.Core.Service;

import com.example.Backend.Core.Models.Contrato;
import com.example.Backend.Core.Models.UsoSeguro;
import com.example.Backend.Core.Repository.UsoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsoSeguroService {
    @Autowired
    private final UsoRepository usoRepository;
    private final ContratoService contratoService;

    public UsoSeguroService(UsoRepository usoRepository, ContratoService contratoService) {
        this.usoRepository = usoRepository;
        this.contratoService = contratoService;
    }

    public List<UsoSeguro> findAll(){
        return usoRepository.findAll();
    }

    public UsoSeguro findByIdUso (int idUso){
        return usoRepository.findByIdUso(idUso);
    }

    public UsoSeguro registrarUso(UsoSeguro usoSeguro) throws Exception{
        //Validaciones de Contrato
        Contrato contrato = contratoService.findByIdContrato(usoSeguro.getContrato().getIdContrato());
        if (contrato == null){
            throw new Exception("El uso del seguro no esta asociado a un contrato existente");
        }
        if (contrato.getFechaInicio().compareTo(new Date())>0 && contrato.getFechaFinalizacion().compareTo(new Date())<0){
            throw new Exception("El contrato ya no esta vigente");
        }

        //Validacion del tipo de uso
        List<String> allowedType = List.of("Choque grave", "Choque leve", "Daño electrico", "Daño mecanico no grave", "Mantenimiento", "Auxilio mecanico");
        if (!allowedType.contains(usoSeguro.getTipoUso())){
            throw new Exception("El tipo de uso "+usoSeguro.getTipoUso()+" no esta permitido");
        }

        //Validacion Monto Aprobado
        if ((usoSeguro.getMontoAprobado() > usoSeguro.getContrato().getPlanSeguro().getValorPerdidasTotales()) &&
                (usoSeguro.getMontoAprobado() > usoSeguro.getContrato().getPlanSeguro().getValorPerdidasParciales())){
            throw new Exception("El monto sobrepasa la cobertura del seguro");
        }

        usoSeguro.setEstadoReclamo("Pendiente");
        usoSeguro.setContrato(contrato);
        usoSeguro.setFecha(new Date());
        return usoRepository.save(usoSeguro);
    }

    public UsoSeguro updateUso(int idUso, UsoSeguro usoActualizar) throws Exception{
        UsoSeguro existe = findByIdUso(idUso);
        if (existe != null){
            //Validacion del tipo de uso
            List<String> allowedType = List.of("Choque grave", "Choque leve", "Daño electrico", "Daño mecanico no grave", "Mantenimiento", "Auxilio mecanico");
            if (!allowedType.contains(existe.getTipoUso())){
                throw new Exception("El tipo de uso "+usoActualizar.getTipoUso()+" no esta permitido");
            }

            //Validacion Monto Aprobado
            if ((usoActualizar.getMontoAprobado() > usoActualizar.getContrato().getPlanSeguro().getValorPerdidasTotales()) &&
                    (usoActualizar.getMontoAprobado() > usoActualizar.getContrato().getPlanSeguro().getValorPerdidasParciales())){
                throw new Exception("El monto sobrepasa la cobertura del seguro");
            }


            //Validaciones de estado de Reclamo
            List<String> ValidarEstado = List.of("Aprobado", "Pendiente", "Rechazado");
            if (!ValidarEstado.contains(usoActualizar.getEstadoReclamo())){
                throw new Exception("Estado de reclamo invalido "+usoActualizar.getEstadoReclamo());
            }


            existe.setTipoUso(usoActualizar.getTipoUso());
            existe.setDescripcion(usoActualizar.getDescripcion());
            existe.setMontoAprobado(usoActualizar.getMontoAprobado());
            existe.setEstadoReclamo(usoActualizar.getEstadoReclamo());
            return usoRepository.save(existe);
        } else {
            throw new Exception("El uso con el ID:"+idUso+" no existe");
        }
    }

    public boolean deleteUso (int idUso) throws Exception{
        UsoSeguro existe = findByIdUso(idUso);
        if (existe != null){
            usoRepository.delete(existe);
            return true;
        } else {
            throw new Exception("El uso con el ID:"+idUso+" no existe");
        }
    }

    public List<UsoSeguro> findByFechas(Date fechaInicio, Date fechaFin){
        List<UsoSeguro> usosEnRango = findAll().stream()
                .filter(usoSeguro -> usoSeguro.getFecha().compareTo(fechaInicio)>=0 &&
                        usoSeguro.getFecha().compareTo(fechaFin)<=0)
                .collect(Collectors.toList());
        return usosEnRango;
    }

    public List<UsoSeguro> findByContrato(Long idContrato){
        List<UsoSeguro> usosSeguro = findAll();
        List<UsoSeguro> usosContrato = new ArrayList<>();

        for (UsoSeguro usoSeguro: usosSeguro){
            if (usoSeguro.getContrato().getIdContrato() == idContrato){
                usosContrato.add(usoSeguro);
            }
        }
        return usosContrato;
    }

}
