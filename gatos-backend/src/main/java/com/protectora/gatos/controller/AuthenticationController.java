package com.protectora.gatos.controller;

import com.protectora.gatos.dto.JwtResponse;
import com.protectora.gatos.dto.Login;
import com.protectora.gatos.dto.Register;
import com.protectora.gatos.model.Empleado;
import com.protectora.gatos.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    
    // POST -> /api/authentication/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody Login login) {
        try {
            JwtResponse jwtResponse = authenticationService.login(login);
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Los datos de inicio de sesión introducidos son incorrectos:" + e.getMessage());
        }
    }

    // POST -> /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<?> registrarEmpleado(@Valid @RequestBody Register registro) {
        try {
            Empleado empleado = authenticationService.registrarEmpleado(registro);
            return ResponseEntity.ok("Empleado " + empleado.getNombreEmpleado() + " registrado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Se ha producido un error durante el registro del empleado:" + e.getMessage());
        }
    }
}