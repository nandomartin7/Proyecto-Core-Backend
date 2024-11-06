package com.example.Backend.Core.Repository;

import com.example.Backend.Core.Models.Automovil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutomovilRepository extends JpaRepository <Automovil, String> {
    Automovil findByIdAutomovil(String idAutomovil);

}
