package com.example.Backend.Core.Repository;

import com.example.Backend.Core.Models.PlanSeguro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<PlanSeguro, Integer> {
    PlanSeguro findByIdPlan(int idPlan);
}
