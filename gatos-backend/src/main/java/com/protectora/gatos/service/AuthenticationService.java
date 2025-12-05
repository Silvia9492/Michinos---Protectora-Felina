package com.protectora.gatos.service;

import com.protectora.gatos.dto.JwtResponse;
import com.protectora.gatos.dto.Login;
import com.protectora.gatos.dto.Register;
import com.protectora.gatos.model.Empleado;
import com.protectora.gatos.repository.EmpleadoRepository;
import com.protectora.gatos.security.EmployeeDetailsSecurity;
import com.protectora.gatos.security.JwtUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtilities jwtUtilities;

    public JwtResponse login(Login login) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                login.getEmail(),
                login.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwt = jwtUtilities.generateJwtToken(authentication);
        
        EmployeeDetailsSecurity userDetails = (EmployeeDetailsSecurity) authentication.getPrincipal();
        
        return new JwtResponse(
            jwt,
            userDetails.getId(),
            userDetails.getNombreE(),
            userDetails.getEmail(),
            userDetails.getRol()
        );
    }
    
    public Empleado registrarEmpleado(Register registro) {
        if (empleadoRepository.existsByEmail(registro.getEmail())) {
            throw new RuntimeException("Error: El email ya está en uso");
        }
        
        if (!registro.getRol().equals("ADMIN") && !registro.getRol().equals("EMPLEADO")) {
            throw new RuntimeException("Error: El rol debe ser ADMIN o EMPLEADO");
        }
        
        Empleado empleado = new Empleado();
        empleado.setNombreEmpleado(registro.getNombre());
        empleado.setEmail(registro.getEmail());
        empleado.setPassword(passwordEncoder.encode(registro.getPassword()));
        empleado.setRol(registro.getRol());
        empleado.setActivo(true);
        
        return empleadoRepository.save(empleado);
    }
}