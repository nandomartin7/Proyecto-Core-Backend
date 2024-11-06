package com.example.Backend.Core.Controller;

import com.example.Backend.Core.Models.Cliente;
import com.example.Backend.Core.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private final ClienteService clienteService;
    private final PasswordEncoder passwordEncoder;

    public ClienteController(ClienteService clienteService, PasswordEncoder passwordEncoder) {
        this.clienteService = clienteService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/info")
    public  String index(){
        return "Conectado a la tabla Cliente";
    }

    @GetMapping()
    public List<Cliente> getAllClientes(){
        return clienteService.findAll();
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable String idCliente){
        Cliente cliente = clienteService.findByIdCliente(idCliente);
        return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cliente> registarCliente(@RequestBody Cliente cliente) throws Exception {
        String encodedPassword = passwordEncoder.encode(cliente.getPassword());
        return new ResponseEntity<>(clienteService.registrarCliente(cliente, encodedPassword), HttpStatus.CREATED);
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable String idCliente, @RequestBody Cliente cliente) throws Exception{
        Cliente actualizado = clienteService.updateCliente(idCliente, cliente);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> deleteCliente (@PathVariable String idCliente) throws Exception{
        return clienteService.deleteCliente(idCliente) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
