package com.example.Backend.Core.Repository;

import com.example.Backend.Core.Models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, String> {
    Empleado findByIdEmpleado(String idEmpleado);
    Empleado findByCorreo(String correo);
}
