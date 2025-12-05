package com.protectora.gatos.controller;

import com.protectora.gatos.model.Gato;
import com.protectora.gatos.service.GatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gatos")
@CrossOrigin(origins = "http://localhost:4200")
public class GatoController {

    @Autowired
    private GatoService gatoService;
    
    // GET -> /api/gatos
    @GetMapping
    public ResponseEntity<List<Gato>> obtenerTodosLosGatos() {
        List<Gato> gatos = gatoService.obtenerTodosLosGatos();
        return ResponseEntity.ok(gatos);
    }
    
    // GET -> /api/gatos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerGatoPorId(@PathVariable Long id) {
        return gatoService.obtenerGatoPorId(id)
            .map(gato -> ResponseEntity.ok(gato))
            .orElse(ResponseEntity.notFound().build());
    }
    
    //GET -> /api/gatos/disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Gato>> obtenerGatosDisponibles() {
        List<Gato> gatos = gatoService.obtenerGatosDisponibles();
        return ResponseEntity.ok(gatos);
    }

    // GET -> /api/gatos/adoptados
    @GetMapping("/adoptados")
    public ResponseEntity<List<Gato>> obtenerGatosAdoptados() {
        List<Gato> gatos = gatoService.obtenerGatosAdoptados();
        return ResponseEntity.ok(gatos);
    }

    // GET -> /api/gatos/buscar/{nombre}
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<Gato>> buscarGatosPorNombre(@PathVariable String nombre) {
        List<Gato> gatos = gatoService.buscarGatosPorNombre(nombre);
        return ResponseEntity.ok(gatos);
    }
    
    // POST -> /api/gatos/register
    @PostMapping("/register")
    public ResponseEntity<?> registrarGato(@RequestBody Gato gato) {
        try {
            Gato nuevoGato = gatoService.registrarGato(gato);
            return ResponseEntity.ok(nuevoGato);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Se ha producido un error al registrar el nuevo gato: " + e.getMessage());
        }
    }
    
    // PUT -> /api/gatos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarGato(@PathVariable Long id, @RequestBody Gato gato) {
        try {
            Gato gatoActualizado = gatoService.actualizarGato(id, gato);
            return ResponseEntity.ok(gatoActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Se ha producido un error al actualizar el gato: " + e.getMessage());
        }
    }

    // PUT -> /api/gatos/{id}/adoptado
    @PutMapping("/{id}/adoptado")
    public ResponseEntity<?> marcarComoAdoptado(@PathVariable Long id) {
        try {
            Gato gato = gatoService.marcarComoAdoptado(id);
            return ResponseEntity.ok(gato);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Error: " + e.getMessage());
        }
    }
    
    // DELETE -> /api/gatos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarGato(@PathVariable Long id) {
        try {
            gatoService.eliminarGato(id);
            return ResponseEntity.ok("El gato se ha eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("Se ha producido un error al intentar eliminar el gato:" + e.getMessage());
        }
    }
}