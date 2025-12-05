package com.protectora.gatos.config;

import com.protectora.gatos.model.Empleado;
import com.protectora.gatos.model.Gato;
import com.protectora.gatos.repository.EmpleadoRepository;
import com.protectora.gatos.repository.GatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
@Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private GatoRepository gatoRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Solo cargar datos si la base de datos está vacía
        if (empleadoRepository.count() == 0) {
            cargarEmpleados();
        }
        
        if (gatoRepository.count() == 0) {
            cargarGatos();
        }
        
        System.out.println("========================================");
        System.out.println("✅ DATOS DE PRUEBA CARGADOS");
        System.out.println("========================================");
        System.out.println("📋 EMPLEADOS DE PRUEBA:");
        System.out.println("----------------------------------------");
        System.out.println("👨‍💼 ADMINISTRADOR:");
        System.out.println("   Email: admin@protectora.com");
        System.out.println("   Password: admin123");
        System.out.println("----------------------------------------");
        System.out.println("👤 EMPLEADO:");
        System.out.println("   Email: empleado@protectora.com");
        System.out.println("   Password: empleado123");
        System.out.println("----------------------------------------");
        System.out.println("👤 EMPLEADO CON PASSWORD POR DEFECTO:");
        System.out.println("   Email: nuevo@protectora.com");
        System.out.println("   Password: Protectora2024");
        System.out.println("========================================");
        System.out.println("🐱 Total de gatos cargados: " + gatoRepository.count());
        System.out.println("========================================");
    }

    private void cargarEmpleados() {
        Empleado administrador = new Empleado();
        administrador.setNombreEmpleado("Silvia González Fernández");
        administrador.setEmail("administracion@gatinos.com");
        administrador.setPassword(passwordEncoder.encode("administracion2025"));
        administrador.setRol("ADMIN");
        administrador.setActivo(true);
        empleadoRepository.save(administrador);
        
        Empleado empleado1 = new Empleado();
        empleado1.setNombreEmpleado("Beatriz Jabonero Puerta");
        empleado1.setEmail("bjp@gatinos.com");
        empleado1.setPassword(passwordEncoder.encode("beaProte2025**"));
        empleado1.setRol("EMPLEADO");
        empleado1.setActivo(true);
        empleadoRepository.save(empleado1);

        Empleado empleado2 = new Empleado();
        empleado2.setNombreEmpleado("Margarita López Pérez-Pellón");
        empleado2.setEmail("mlpp@gatinos.com");
        empleado2.setPassword(passwordEncoder.encode("margaProte2025**"));
        empleado2.setRol("EMPLEADO");
        empleado2.setActivo(true);
        empleadoRepository.save(empleado2);
        
        // Crear un EMPLEADO con contraseña por defecto (simula uno recién creado por admin)
        Empleado empleado3 = new Empleado();
        empleado3.setNombreEmpleado("Zeltia Rodriguez Rodriguez");
        empleado3.setEmail("zrr@gatinos.com");
        empleado3.setPassword(passwordEncoder.encode("Protectora2025")); // Password por defecto
        empleado3.setRol("EMPLEADO");
        empleado3.setActivo(true);
        empleadoRepository.save(empleado3);
    }
    
    private void cargarGatos() {
        Gato gato1 = new Gato();
        gato1.setNombreGato("Smokey");
        gato1.setEdad(4);
        gato1.setRaza("Mestizo de Maine Coon");
        gato1.setDescripcion("Smokey es un gato muy cariñoso que disfruta mucho en compañía de personas. Le encanta dormir largas siestas al sol y jugar con sus congéneres.");
        gato1.setColor("Atigrado gris");
        gato1.setSexo("MACHO");
        gato1.setFechaAlta(LocalDate.now().minusMonths(2));
        gato1.setAdoptado(true);
        gatoRepository.save(gato1);
        
        Gato gato2 = new Gato();
        gato2.setNombreGato("Níobe");
        gato2.setEdad(4);
        gato2.setRaza("Mestiza de Blue British");
        gato2.setDescripcion("Níobe es una gata muy elegante y amigable. Le encanta llamar la atención y dormirse bajo las mantas.");
        gato2.setColor("Negra");
        gato2.setSexo("HEMBRA");
        gato2.setFechaAlta(LocalDate.now().minusMonths(1));
        gato2.setAdoptado(true);
        gatoRepository.save(gato2);
        
        Gato gato3 = new Gato();
        gato3.setNombreGato("Iris");
        gato3.setEdad(10);
        gato3.setRaza("Europea común de pelo corto");
        gato3.setDescripcion("Iris es una gata muy tranquila que, con su edad, prefiere la calma del hogar a las aventuras del exterior. Le encanta estar con personas y que le den mucha atención");
        gato3.setColor("Atigrada gris");
        gato3.setSexo("Hembra");
        gato3.setFechaAlta(LocalDate.now().minusMonths(4));
        gato3.setAdoptado(true);
        gatoRepository.save(gato3);
        
        Gato gato4 = new Gato();
        gato4.setNombreGato("Casper");
        gato4.setEdad(8);
        gato4.setRaza("Mestizo de siamés");
        gato4.setDescripcion("Casper es un gato muy activo y curioso. Le encanta salir al exterior a explorar todo su entorno.");
        gato4.setColor("Capa propia de siamés + atigrado");
        gato4.setSexo("MACHO");
        gato4.setFechaAlta(LocalDate.now().minusMonths(3));
        gato4.setAdoptado(true);
        gatoRepository.save(gato4);
        
        Gato gato5 = new Gato();
        gato5.setNombreGato("Simba");
        gato5.setEdad(5);
        gato5.setRaza("Maine Coon");
        gato5.setDescripcion("Simba es un gato muy amigable y paciente. Disfruta mucho jugando con otros gatos, y también con niños.");
        gato5.setColor("Gris ceniza + blanco");
        gato5.setSexo("MACHO");
        gato5.setFechaAlta(LocalDate.now().minusMonths(5));
        gato5.setAdoptado(false);
        gatoRepository.save(gato5);
        
        Gato gato6 = new Gato();
        gato6.setNombreGato("Félix");
        gato6.setEdad(2);
        gato6.setRaza("Bengalí");
        gato6.setDescripcion("Félix es un gato muy aventurero que disfruta mucho explorando el entorno. Eso sí, siempre le gusta dormir calentito en su hogar.");
        gato6.setColor("Capa propia");
        gato6.setSexo("MACHO");
        gato6.setFechaAlta(LocalDate.now().minusMonths(6));
        gato6.setAdoptado(false);
        gatoRepository.save(gato6);
        
        Gato gato7 = new Gato();
        gato7.setNombreGato("Naia");
        gato7.setEdad(3);
        gato7.setRaza("Angora");
        gato7.setDescripcion("Naia es una gata muy cariñosa, pero le cuesta confiar. Tener paciencia con ella es un éxito futuro asegurado.");
        gato7.setColor("Atigrada naranja");
        gato7.setSexo("HEMBRA");
        gato7.setFechaAlta(LocalDate.now().minusMonths(3));
        gato7.setAdoptado(false);
        gatoRepository.save(gato7);

        Gato gato8 = new Gato();
        gato8.setNombreGato("Mora");
        gato8.setEdad(6);
        gato8.setRaza("Azul Ruso");
        gato8.setDescripcion("Mora es una gata muy dulce y afectiva. Le encanta estar en brazos a todas horas, y disfruta mucho de la compañía de personas.");
        gato8.setColor("Capa propia");
        gato8.setSexo("HEMBRA");
        gato8.setFechaAlta(LocalDate.now().minusMonths(3));
        gato8.setAdoptado(false);
        gatoRepository.save(gato8);
    }
}