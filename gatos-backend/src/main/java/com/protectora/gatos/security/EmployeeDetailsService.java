package com.protectora.gatos.security;

import com.protectora.gatos.model.Empleado;
import com.protectora.gatos.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Empleado empleado = empleadoRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("El empleado con email:" + email + "no existe"));

        return EmployeeDetailsSecurity.build(empleado);
    }
}