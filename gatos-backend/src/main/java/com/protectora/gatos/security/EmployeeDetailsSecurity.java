package com.protectora.gatos.security;
import com.protectora.gatos.model.Empleado;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeDetailsSecurity implements UserDetails{
    private Long id;
    private String nombreE;
    private String email;
    private String password;
    private String rol;
    private Collection<? extends GrantedAuthority> authorities;
    
    public static EmployeeDetailsSecurity build(Empleado empleado) {
        List<GrantedAuthority> authorities = List.of(
            new SimpleGrantedAuthority("ROLE_" + empleado.getRol())
        );
        
        return new EmployeeDetailsSecurity(
            empleado.getId(),
            empleado.getNombreEmpleado(),
            empleado.getEmail(),
            empleado.getPassword(),
            empleado.getRol(),
            authorities
        );
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return email;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}