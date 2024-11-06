package com.example.Backend.Core.Repository;

import com.example.Backend.Core.Models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,String> {
    Admin findByAdministrador(String administrador);
}
