package com.protectora.gatos.controller;

import com.protectora.gatos.model.Empleado;
import com.protectora.gatos.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "http://localhost:4200")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    // GET -> /api/empleados
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Empleado>> obtenerTodosLosEmpleados() {
        List<Empleado> empleados = empleadoService.obtenerTodosLosEmpleados();
        return ResponseEntity.ok(empleados);
    }
    
    // GET -> /api/empleados/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEmpleadoPorId(@PathVariable Long id) {
        if (!esAdminOPropioEmpleado(id)) {
            return ResponseEntity.status(403).body("No tienes permiso para ver este empleado");
        }
        return empleadoService.obtenerEmpleadoPorId(id)
            .map(empleado -> ResponseEntity.ok(empleado))
            .orElse(ResponseEntity.notFound().build());
    }
    
    // GET -> /api/empleados/buscar/{nombre}
    @GetMapping("/buscar/{nombre}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Empleado>> buscarEmpleadosPorNombre(@PathVariable String nombre) {
        List<Empleado> empleados = empleadoService.buscarEmpleadosPorNombre(nombre);
        return ResponseEntity.ok(empleados);
    }
    
    /* LOS NUEVOS EMPLEADOS SE CREAN CON CONTRASEÑA POR DEFECTO, QUE LUEGO CADA EMPLEADO TIENE QUE CAMBIAR
        POST -> /api/empleados
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> crearEmpleado(@RequestBody Empleado empleado) {
        try {
            Empleado nuevoEmpleado = empleadoService.crearEmpleado(empleado);
            String passwordDefecto = empleadoService.getDefaultPassword();
            
            return ResponseEntity.ok(Map.of(
                "empleado", nuevoEmpleado,
                "mensaje", "Empleado creado exitosamente",
                "passwordTemporal", passwordDefecto,
                "aviso", "El empleado debe cambiar su contraseña en el primer inicio de sesión"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al crear empleado: " + e.getMessage());
        }
    }
    
    // PUT -> /api/empleados/{id}

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
        try {
            boolean esAdmin = esAdmin();
            boolean esPropioEmpleado = esPropioEmpleado(id);
            
            if (!esAdmin && !esPropioEmpleado) {
                return ResponseEntity.status(403).body("No tienes permiso para actualizar este empleado");
            }

            if (!esAdmin && empleado.getRol() != null) {
                return ResponseEntity.status(403).body("Solo los administradores pueden cambiar roles");
            }
            
            Empleado empleadoActualizado = empleadoService.actualizarEmpleado(id, empleado);
            return ResponseEntity.ok(empleadoActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al actualizar empleado: " + e.getMessage());
        }
    }

    // PUT -> /api/empleados/{id}/cambiar-password

    @PutMapping("/{id}/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            if (!esAdminOPropioEmpleado(id)) {
                return ResponseEntity.status(403).body("No tienes permiso para cambiar esta contraseña");
            }
            
            String newPassword = body.get("newPassword");
            if (newPassword == null || newPassword.isEmpty()) {
                return ResponseEntity.badRequest().body("La contraseña nueva es obligatoria");
            }
            
            empleadoService.cambiarPassword(id, newPassword);
            return ResponseEntity.ok("Contraseña actualizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al cambiar contraseña: " + e.getMessage());
        }
    }
    
    //DELETE -> /api/empleados/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Long id) {
        try {
            empleadoService.eliminarEmpleado(id);
            return ResponseEntity.ok("Empleado marcado como inactivo exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error al marcar el empleado como inactivo: " + e.getMessage());
        }
    }

    // GET -> /api/empleados/password-defecto

    @GetMapping("/password-defecto")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> obtenerDefaultPassword() {
        String password = empleadoService.getDefaultPassword();
        return ResponseEntity.ok(Map.of(
            "passwordDefecto", password,
            "mensaje", "Esta es la contraseña que se asigna a los nuevos empleados"
        ));
    }

    private boolean esAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
    
    private boolean esPropioEmpleado(Long empleadoId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailActual = auth.getName(); // El email del usuario autenticado
        
        return empleadoService.obtenerEmpleadoPorId(empleadoId)
            .map(emp -> emp.getEmail().equals(emailActual))
            .orElse(false);
    }

    private boolean esAdminOPropioEmpleado(Long empleadoId) {
        return esAdmin() || esPropioEmpleado(empleadoId);
    }
}