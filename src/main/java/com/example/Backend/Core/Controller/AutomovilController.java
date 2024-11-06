package com.example.Backend.Core.Controller;

import com.example.Backend.Core.Models.Automovil;
import com.example.Backend.Core.Service.AutomovilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/automovil")
public class AutomovilController {
    @Autowired
    private final AutomovilService automovilService;

    public AutomovilController(AutomovilService automovilService) {
        this.automovilService = automovilService;
    }

    @GetMapping("/info")
    public String index(){
        return "Conectado a la tabla Automoviles";
    }

    @GetMapping("")
    public List<Automovil> getAllAutomoviles(){
        return automovilService.findAll();
    }

    @GetMapping("/{placa}")
    public ResponseEntity<Automovil> getAutomovilByPlaca(@PathVariable String placa){
        Automovil automovil = automovilService.findByIdAutomovil(placa);
        return automovil != null ? ResponseEntity.ok(automovil) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public  ResponseEntity<Automovil> registarAutomovil(@RequestBody Automovil automovil) throws Exception {
        return new ResponseEntity<>(automovilService.registrarAutomovil(automovil), HttpStatus.CREATED);
    }

    @PutMapping("/{placa}")
    public ResponseEntity<Automovil> updateAutomovil(@PathVariable String placa, @RequestBody Automovil automovil) throws Exception{
        Automovil actualizado = automovilService.updateAutomovil(placa,automovil);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<Void> deleteAutomovil (@PathVariable String placa) throws Exception{
        return automovilService.deleteAutomovil(placa) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/filtrar/similares")
    public List<Automovil> listarAutomovilesSimilares(@RequestParam int anio, @RequestParam String marca
            , @RequestParam String modelo, @RequestParam String usoDestinado){

        return automovilService.findBySimilares(anio,marca,modelo,usoDestinado);
    }
}
