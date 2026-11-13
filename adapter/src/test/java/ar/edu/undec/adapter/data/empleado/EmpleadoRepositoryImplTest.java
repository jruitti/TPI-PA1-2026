package ar.edu.undec.adapter.data.empleado;
import ar.edu.undec.adapter.data.empleado.crud.EmpleadoCrudRepository;
import ar.edu.undec.adapter.data.empleado.mapper.EmpleadoMapper;
import ar.edu.undec.adapter.data.empleado.repoimplementacion.EmpleadoRepositoryImpl;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class EmpleadoRepositoryImplTest {
    @Autowired
    private EmpleadoCrudRepository empleadoCrudRepository;

    private EmpleadoRepositoryImpl empleadoRepositoryImpl;

    @BeforeEach
    void setUp() {
        empleadoRepositoryImpl = new EmpleadoRepositoryImpl(empleadoCrudRepository, new EmpleadoMapper());
    }

    private Empleado empleadoDePrueba(String legajo) {
        return Empleado.crearEmpleado(UUID.randomUUID(), "Juan Perez", legajo, LocalDate.now().minusDays(1), Puesto.ANALISTA, new BigDecimal("500000"));
    }

    @Test
    void saveGuardaElEmpleadoYQuedaDisponiblePorLegajo() {
        Empleado empleado = empleadoDePrueba("ABC1234");

        boolean resultado = empleadoRepositoryImpl.save(empleado);

        assertTrue(resultado);
        assertTrue(empleadoRepositoryImpl.existByLegajo("ABC1234"));
    }

    @Test
    void findByLegajoRetornaElEmpleadoConSusDatos() {
        empleadoRepositoryImpl.save(empleadoDePrueba("ABC1234"));

        Optional<Empleado> encontrado = empleadoRepositoryImpl.findByLegajo("ABC1234");

        assertTrue(encontrado.isPresent());
        assertEquals("Juan Perez", encontrado.get().getNombreCompleto());
        assertEquals("ABC1234", encontrado.get().getLegajo());
        assertEquals(Puesto.ANALISTA, encontrado.get().getPuesto());
        assertEquals(0, encontrado.get().getSalario().compareTo(new BigDecimal("500000")));
    }

    @Test
    void findByLegajoInexistenteRetornaOptionalVacio() {
        Optional<Empleado> encontrado = empleadoRepositoryImpl.findByLegajo("ZZZ9999");

        assertTrue(encontrado.isEmpty());
    }

    @Test
    void existByLegajoRetornaFalseCuandoNoExiste() {
        assertFalse(empleadoRepositoryImpl.existByLegajo("ZZZ9999"));
    }

    @Test
    void findAllRetornaTodosLosEmpleadosGuardados() {
        empleadoRepositoryImpl.save(empleadoDePrueba("ABC1234"));
        empleadoRepositoryImpl.save(empleadoDePrueba("DEF5678"));

        List<Empleado> empleados = empleadoRepositoryImpl.findAll();

        assertEquals(2, empleados.size());
    }
}
