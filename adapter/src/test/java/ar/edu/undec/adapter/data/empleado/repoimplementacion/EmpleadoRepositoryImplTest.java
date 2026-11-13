package ar.edu.undec.adapter.data.empleado.repoimplementacion;

import ar.edu.undec.adapter.data.empleado.crud.SpringDataEmpleadoRepository;
import ar.edu.undec.adapter.data.empleado.model.EmpleadoEntity;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(EmpleadoRepositoryImpl.class)
class EmpleadoRepositoryImplTest {

    @Autowired
    private EmpleadoRepositoryImpl empleadoRepository;

    @Autowired
    private SpringDataEmpleadoRepository springDataEmpleadoRepository;

    @Test
    void guardar_empleadoNuevo_deberiaPersistirYRetornarEmpleado() {
        // Arrange
        UUID id = UUID.randomUUID();
        Empleado empleadoDominio = new Empleado(
                id,
                "ABC1234",
                "Juan Perez",
                Puesto.GERENTE,
                1500000.0,
                LocalDate.of(2023, 5, 10)
        );

        // Act
        Empleado resultado = empleadoRepository.guardar(empleadoDominio);

        // Assert
        assertNotNull(resultado);
        assertEquals(empleadoDominio.getLegajo(), resultado.getLegajo());
        assertTrue(springDataEmpleadoRepository.existsByLegajo("ABC1234"));
    }

    @Test
    void existePorLegajo_cuandoExiste_deberiaRetornarTrue() {
        // Arrange
        EmpleadoEntity entity = new EmpleadoEntity(
                UUID.randomUUID(), "XYZ5678", "Ana Gomez", Puesto.ANALISTA, 600000.0, LocalDate.now()
        );
        springDataEmpleadoRepository.save(entity);

        // Act
        boolean existe = empleadoRepository.existePorLegajo("XYZ5678");

        // Assert
        assertTrue(existe);
    }

    @Test
    void buscarPorLegajo_cuandoExiste_deberiaRetornarOptionalConEmpleado() {
        // Arrange
        UUID id = UUID.randomUUID();
        EmpleadoEntity entity = new EmpleadoEntity(
                id, "DEF9012", "Carlos Ruiz", Puesto.SUPERVISOR, 900000.0, LocalDate.now()
        );
        springDataEmpleadoRepository.save(entity);

        // Act
        Optional<Empleado> resultado = empleadoRepository.buscarPorLegajo("DEF9012");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Carlos Ruiz", resultado.get().getNombreCompleto());
        assertEquals(id, resultado.get().getId());
    }

    @Test
    void buscarPorLegajo_cuandoNoExiste_deberiaRetornarOptionalVacio() {
        // Act
        Optional<Empleado> resultado = empleadoRepository.buscarPorLegajo("NOEXISTE");

        // Assert
        assertTrue(resultado.isEmpty());
    }

    @Test
    void listarTodos_deberiaRetornarListaDeEmpleados() {
        // Arrange
        springDataEmpleadoRepository.save(new EmpleadoEntity(UUID.randomUUID(), "AAA1111", "Empleado 1", Puesto.ANALISTA, 600000.0, LocalDate.now()));
        springDataEmpleadoRepository.save(new EmpleadoEntity(UUID.randomUUID(), "BBB2222", "Empleado 2", Puesto.GERENTE, 1300000.0, LocalDate.now()));

        // Act
        List<Empleado> empleados = empleadoRepository.listarTodos();

        // Assert
        assertNotNull(empleados);
        assertEquals(2, empleados.size());
    }
}