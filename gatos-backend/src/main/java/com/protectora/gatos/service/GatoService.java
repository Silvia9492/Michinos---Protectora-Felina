package com.protectora.gatos.service;

import com.protectora.gatos.model.Gato;
import com.protectora.gatos.repository.GatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GatoService {

     @Autowired
    private GatoRepository gatoRepository;
    
    public List<Gato> obtenerTodosLosGatos() {
        return gatoRepository.findAll();
    }
    
    public Optional<Gato> obtenerGatoPorId(Long id) {
        return gatoRepository.findById(id);
    }
    
    public List<Gato> obtenerGatosDisponibles() {
        return gatoRepository.findByAdoptado(false);
    }
    
    public List<Gato> obtenerGatosAdoptados() {
        return gatoRepository.findByAdoptado(true);
    }
    
    public List<Gato> buscarGatosPorNombre(String nombre) {
        return gatoRepository.findByNombreGato(nombre);
    }
    
    public Gato registrarGato(Gato gato) {
        if (gato.getFechaAlta() == null) {
            gato.setFechaAlta(LocalDate.now());
        }
        
        if (gato.getAdoptado() == null) {
            gato.setAdoptado(false);
        }
        
        return gatoRepository.save(gato);
    }
    
    public Gato actualizarGato(Long id, Gato gatoActualizado) {
        Gato gato = gatoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El gato con id " + id + "no ha podido encontrarse."));
        
        gato.setNombreGato(gatoActualizado.getNombreGato());
        gato.setEdad(gatoActualizado.getEdad());
        gato.setRaza(gatoActualizado.getRaza());
        gato.setDescripcion(gatoActualizado.getDescripcion());
        gato.setColor(gatoActualizado.getColor());
        gato.setSexo(gatoActualizado.getSexo());
        gato.setAdoptado(gatoActualizado.getAdoptado());

        return gatoRepository.save(gato);
    }
    
    public Gato marcarComoAdoptado(Long id) {
        Gato gato = gatoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El gato con id " + id + "no ha podido encontrarse."));
        //Ponerlo con el nombre del gato, ver cómo se recupera
        if (gato.getAdoptado()) {
            throw new RuntimeException(gato.getNombreGato() + "ya ha sido adoptado");
        }
        
        gato.setAdoptado(true);
        return gatoRepository.save(gato);
    }
    
    public void eliminarGato(Long id) {
        Gato gato = gatoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El gato con id " + id + "no ha podido encontrarse."));
        
        gatoRepository.delete(gato);
    }
}
