package com.example.Backend.Core.Controller;

import com.example.Backend.Core.Models.Contrato;
import com.example.Backend.Core.Service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/contrato")
public class ContratoController {
    @Autowired
    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping("/info")
    public String index(){
        return "Conectado a la tabla Contrato";
    }

    @GetMapping()
    public List<Contrato> getAllContratos(){
        return contratoService.findAll();
    }

    @GetMapping("/{idContrato}")
    public ResponseEntity<Contrato> getByIdContrato(@PathVariable Long idContrato){
        Contrato contrato = contratoService.findByIdContrato(idContrato);
        return contrato != null ? ResponseEntity.ok(contrato) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Contrato> registarContrato(@RequestBody Contrato contrato) throws Exception{
        return new ResponseEntity<>(contratoService.registrarContrato(contrato), HttpStatus.CREATED);
    }

    @PutMapping("/{idContrato}")
    public ResponseEntity<Contrato> updateContrato(@PathVariable Long idContrato, @RequestBody Contrato contrato) throws Exception{
        Contrato actualizado = contratoService.updateContrato(idContrato, contrato);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idContrato}")
    public ResponseEntity<Void> deleteContrato(@PathVariable Long idContrato) throws Exception{
        return contratoService.deleteContrato(idContrato) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/filtrar/plan")
    public List<Contrato> filtrarByPlan (@RequestParam int planSeguro){
        return contratoService.findByPlan(planSeguro);
    }

    @GetMapping("/filtrar/fecha")
    public List<Contrato> filtrarByFecha (@RequestParam Date FechaInicio, @RequestParam Date FecahFin){
        return contratoService.findByFechas(FechaInicio,FecahFin);
    }
}
