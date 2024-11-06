package com.example.Backend.Core.Repository;

import com.example.Backend.Core.Models.UsoSeguro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsoRepository extends JpaRepository<UsoSeguro, Integer> {
    UsoSeguro findByIdUso(int idUso);
}
