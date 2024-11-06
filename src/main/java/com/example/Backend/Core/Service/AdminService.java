package com.example.Backend.Core.Service;

import com.example.Backend.Core.Models.Admin;
import com.example.Backend.Core.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private final AdminRepository repositorio;

    public AdminService(AdminRepository repositorio) {
        this.repositorio = repositorio;
    }

    public Admin registrarAdmin(Admin admin, String encodedPassword){
        admin.setPassword(encodedPassword);
        repositorio.save(admin);
        return admin;
    }

    public Admin findByUsuario(String usuario){
        return repositorio.findByAdministrador(usuario);
    }

}
