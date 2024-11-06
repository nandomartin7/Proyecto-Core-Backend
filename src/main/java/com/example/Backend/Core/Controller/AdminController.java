package com.example.Backend.Core.Controller;

import com.example.Backend.Core.Models.Admin;
import com.example.Backend.Core.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final AdminService adminService;

    public AdminController(PasswordEncoder passwordEncoder, AdminService adminService) {
        this.passwordEncoder = passwordEncoder;
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Admin admin){
        Admin existe = adminService.findByUsuario(admin.getAdministrador());
        if (existe != null && passwordEncoder.matches(admin.getPassword(), existe.getPassword())){
            return ResponseEntity.ok("Inicio de sesion exitoso");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales Invalidas");
    }

    /*@PostMapping("/registro")
    public ResponseEntity<String> registrar(@RequestBody Admin admin){
        String encodedPassword = passwordEncoder.encode(admin.getPassword());
        adminService.registrarAdmin(admin, encodedPassword);
        return ResponseEntity.ok("Registro Exitoso");
    }*/
}
