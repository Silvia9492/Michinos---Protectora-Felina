package com.protectora.gatos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Debe introducir un nombre para continuar")
    @Column(nullable = false)
    private String nombreEmpleado;
    
    @NotBlank(message = "Debe introducir un email para continuar")
    @Email(message = "Email incorrecto. Por favor, introduzca una dirección de correo electrónico válida")
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotBlank(message = "Debe introducir una contraseña para continuar")
    @Column(nullable = false)
    private String password;
    
    @NotBlank(message = "Por favor, indique si usted es administrador para poder continuar")
    @Column(nullable = false)
    private String rol;
    
    @Column(name = "activo")
    private Boolean activo = true;
}
