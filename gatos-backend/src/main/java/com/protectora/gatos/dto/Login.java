package com.protectora.gatos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    
    @NotBlank(message = "Introduzca su email para iniciar sesión")
    @Email(message = "El email que ha introducido no es válido")
    private String email;
    
    @NotBlank(message = "La contraseña es obligatoria para iniciar sesión con su cuenta")
    private String password;
}
