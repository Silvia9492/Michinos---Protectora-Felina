package com.protectora.gatos.service;

import com.protectora.gatos.model.Empleado;
import com.protectora.gatos.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    //Contraseña por defecto para nuevos empleados
    private static final String DEFAULT_PASSWORD = "Protectora2025";
    
    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }
    
    public Optional<Empleado> obtenerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id);
    }

    public List<Empleado> buscarEmpleadosPorNombre(String nombre) {
        return empleadoRepository.findAll().stream()
            .filter(e -> e.getNombreEmpleado().toLowerCase().contains(nombre.toLowerCase()))
            .toList();
    }
    
    /*
    public Optional<Empleado> obtenerEmpleadoPorEmail(String email) {
        return empleadoRepository.findByEmail(email);
    }
    */
    
    public Empleado crearEmpleado(Empleado empleado) {
        if (empleadoRepository.existsByEmail(empleado.getEmail())) {
            throw new RuntimeException("Ya existe una cuenta asociada a esta dirección de email.");
        }
        empleado.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        empleado.setActivo(true);
        
        return empleadoRepository.save(empleado);
    }
    
    public Empleado actualizarEmpleado(Long id, Empleado empleadoActualizado) {
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El empleado con id " + id + "no ha podido encontrarse."));
        
        empleado.setNombreEmpleado(empleadoActualizado.getNombreEmpleado());

        if (!empleado.getEmail().equals(empleadoActualizado.getEmail())) {
            if (empleadoRepository.existsByEmail(empleadoActualizado.getEmail())) {
                throw new RuntimeException("Ya existe una cuenta asociada a esta dirección de email.");
            }
            empleado.setEmail(empleadoActualizado.getEmail());
        }

        if (empleadoActualizado.getPassword() != null && !empleadoActualizado.getPassword().isEmpty()) {
            empleado.setPassword(passwordEncoder.encode(empleadoActualizado.getPassword()));
        }
        
        if (empleadoActualizado.getRol() != null) {
            empleado.setRol(empleadoActualizado.getRol());
        }
        
        return empleadoRepository.save(empleado);
    }
    
    public Empleado cambiarPassword(Long id, String newPassword) {
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se ha encontrado el empleado con id:" + id));
        
        empleado.setPassword(passwordEncoder.encode(newPassword));
        return empleadoRepository.save(empleado);
    }

    public void eliminarEmpleado(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El empleado con id " + id + "no ha podido encontrarse."));
        
        empleadoRepository.delete(empleado);
    }

    //YA VERÉ SI NECESITO ESTO, SI NO LA QUITO
    public String getDefaultPassword() {
        return DEFAULT_PASSWORD;
    }
}