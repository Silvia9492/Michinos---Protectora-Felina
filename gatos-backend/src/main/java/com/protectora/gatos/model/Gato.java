package com.protectora.gatos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "gatos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Introduzca el nombre del gato para continuar")
    @Column(nullable = false)
    private String nombreGato;
    
    @Min(value = 0, message = "Introduzca la edad del gato para continuar")
    @Column(nullable = false)
    private Integer edad;
    
    @NotBlank(message = "Introduzca la raza del gato para continuar")
    @Column(nullable = false)
    private String raza;

    @NotBlank(message = "Introduzca el color de la capa para continuar")
    @Column(nullable = false)
    private String color;

    @NotBlank(message = "Indique si el gato es un macho o una hembra para continuar")
    @Column(nullable = false)
    private String sexo;
    
    @Column(length = 1000)
    private String descripcion;
    
    @Column(name = "fecha_de_alta")
    private LocalDate fechaAlta;
    
    @Column(name = "adoptado")
    private Boolean adoptado = false;
}
