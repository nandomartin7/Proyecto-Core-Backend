package com.example.Backend.Core.Repository;

import com.example.Backend.Core.Models.Automovil;
import com.example.Backend.Core.Models.Contrato;
import com.example.Backend.Core.Models.PlanSeguro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    Contrato findByIdContrato (Long idContato);
}

