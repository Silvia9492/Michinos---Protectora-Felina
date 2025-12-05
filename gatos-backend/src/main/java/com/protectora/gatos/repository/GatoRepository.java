package com.protectora.gatos.repository;

import com.protectora.gatos.model.Gato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GatoRepository extends JpaRepository<Gato, Long>{
    List<Gato> findByAdoptado(Boolean adoptado);
    List<Gato> findByNombreGato(String nombreGato);
}
