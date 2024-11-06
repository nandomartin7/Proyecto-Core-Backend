package com.example.Backend.Core.Repository;

import com.example.Backend.Core.Models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
    Cliente findByIdCliente(String idCliente);
    Cliente findByCorreo(String correo);
}
