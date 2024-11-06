package com.example.Backend.Core.Service;

import com.example.Backend.Core.Models.Automovil;
import com.example.Backend.Core.Repository.AutomovilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AutomovilService {
    @Autowired
    private final AutomovilRepository automovilRepository;


    public AutomovilService(AutomovilRepository automovilRepository) {
        this.automovilRepository = automovilRepository;
    }

    public List<Automovil> findAll(){
        return automovilRepository.findAll();
    }

    public Automovil findByIdAutomovil (String placa){
        return automovilRepository.findByIdAutomovil(placa);
    }

    public Automovil registrarAutomovil (Automovil automovil) throws Exception{
        //Validacion del idAutomovil o Placa
        if (!automovil.getIdAutomovil().matches("^[ABCUHXRLOEGIMPVNQSWYJKTZ]{1}[A-Z]{2}-\\d{4}$")){
            throw new Exception("La placal del automovil "+automovil.getIdAutomovil()+" no esta permitido");
        }

        //Validacion de Placas unicas
        Optional<Automovil>  existe= Optional.ofNullable(findByIdAutomovil(automovil.getIdAutomovil()));
        if (existe.isPresent()){
            throw new Exception("El automóvil con placa"+automovil.getIdAutomovil()+" ya existe");
        }

        //Validacion año del vehículo
        int currentYear = java.time.Year.now().getValue();
        if (automovil.getAnio() > currentYear){
            throw new Exception("El año del automóvil debe ser menor a "+ currentYear);
        }

        //Validacion del tipo de automóvil
        List<String> allowedType = List.of("Automóvil", "Jeep / Caminoneta", "Deportivo");
        if (!allowedType.contains(automovil.getTipo())){
            throw new Exception("El tipo de automóvil "+automovil.getTipo()+" no esta permitida");
        }

        //Validacion de ciudad circulacion
        List<String> allowedCities = List.of("Quito", "Guayaquil");
        if (!allowedCities.contains(automovil.getCiudadCirculacion())){
            throw new Exception("La ciudad de circulacion "+automovil.getCiudadCirculacion()+" no esta permitida");
        }

        //Validacion del uso del automóvil
        List<String> allowedUse = List.of("Familiar", "Trabajo", "Carga / Comercio");
        if (!allowedUse.contains(automovil.getUsoDestinado())){
            throw new Exception("El uso destinado "+automovil.getUsoDestinado()+" no esta permitido");
        }
        automovilRepository.save(automovil);
        return automovil;
    }

    public Automovil updateAutomovil(String idAutomovil,Automovil automovilActualizar) throws Exception{
        Automovil existe = automovilRepository.findByIdAutomovil(idAutomovil);
        if (existe != null){
            //Validacion año del automovil
            int currentYear = java.time.Year.now().getValue();
            if (existe.getAnio() > currentYear){
                throw new Exception("El año del automóvil debe ser menor a "+ currentYear);
            }

            //Validacion del tipo de automóvil
            List<String> allowedType = List.of("Automóvil", "Jeep / Caminoneta", "Deportivo");
            if (!allowedType.contains(existe.getTipo())){
                throw new Exception("El tipo de automóvil "+existe.getTipo()+" no esta permitida");
            }

            //Validacion de la ciudad de circulacion
            List<String> allowedCities = List.of("Quito", "Guayaquil");
            if (!allowedCities.contains(existe.getCiudadCirculacion())){
                throw new Exception("La ciudad de circulacion "+existe.getCiudadCirculacion()+" no esta permitida");
            }

            //Validacion del uso del automóvil
            List<String> allowedUse = List.of("Familiar", "Trabajo", "Carga / Comercio");
            if (!allowedUse.contains(existe.getUsoDestinado())){
                throw new Exception("El uso destinado "+existe.getUsoDestinado()+" no esta permitido");
            }

            existe.setAnio(automovilActualizar.getAnio());
            existe.setTipo(automovilActualizar.getTipo());
            existe.setMarca(automovilActualizar.getMarca());
            existe.setModelo(automovilActualizar.getModelo());
            existe.setCiudadCirculacion(automovilActualizar.getCiudadCirculacion());
            existe.setUsoDestinado(automovilActualizar.getUsoDestinado());
            return automovilRepository.save(existe);
        } else {
            throw new Exception("El automovil con placa"+idAutomovil+" no existe");
        }
    }

    public boolean deleteAutomovil(String idAutomovil) throws Exception{
        Automovil existe = findByIdAutomovil(idAutomovil);
        if (existe != null){
            automovilRepository.delete(existe);
            return true;
        }else {
            throw new Exception("El automovil con placa "+idAutomovil+" no existe");
        }
    }

    public List<Automovil> findBySimilares(int anio, String marca, String modelo, String usoDestinado){
        List<Automovil> automovilesTotales = findAll();
        List<Automovil> automovilesSimilares = new ArrayList<>();

        for (Automovil auto: automovilesTotales){
            if (auto.getAnio() == anio &&
                auto.getMarca().equalsIgnoreCase(marca) &&
                auto.getModelo().equalsIgnoreCase(modelo) &&
                auto.getUsoDestinado().equalsIgnoreCase(usoDestinado)){
                automovilesSimilares.add(auto);
            }
        }

        return automovilesSimilares;
    }
}