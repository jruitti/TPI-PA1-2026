package ar.edu.undec.adapter.data.empleado;

import ar.edu.undec.adapter.data.empleado.repoimplementacion.EmpleadoRepositorioJpaAdapter;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepositorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Import(EmpleadoRepositorioJpaAdapter.class)
public class EmpleadoRepositorioJpaAdapterTest {

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Test
    void guardaEmpleadoYRetornaSusDatos() {
        Empleado empleado = crearEmpleado("Ana Pérez", "ABC1234");

        Empleado guardado = empleadoRepositorio.guardar(empleado);

        assertNotNull(guardado.getId());
        assertEquals(empleado.getNombreCompleto(), guardado.getNombreCompleto());
        assertEquals(empleado.getLegajo(), guardado.getLegajo());
        assertEquals(empleado.getFechaIngreso(), guardado.getFechaIngreso());
        assertEquals(empleado.getPuesto(), guardado.getPuesto());
        assertEquals(empleado.getSalario(), guardado.getSalario());
    }

    @Test
    void informaSiExisteUnEmpleadoConElLegajo() {
        empleadoRepositorio.guardar(crearEmpleado("Ana Pérez", "ABC1234"));

        assertTrue(empleadoRepositorio.existePorLegajo("ABC1234"));
    }

    @Test
    void buscaEmpleadoPorLegajo() {
        Empleado empleado = crearEmpleado("Ana Pérez", "ABC1234");
        empleadoRepositorio.guardar(empleado);

        Optional<Empleado> encontrado = empleadoRepositorio.buscarPorLegajo("ABC1234");

        assertTrue(encontrado.isPresent());
        assertEquals(empleado.getLegajo(), encontrado.orElseThrow().getLegajo());
        assertEquals(empleado.getNombreCompleto(), encontrado.orElseThrow().getNombreCompleto());
    }

    @Test
    void listaLosEmpleadosGuardados() {
        empleadoRepositorio.guardar(crearEmpleado("Ana Pérez", "ABC1234"));
        empleadoRepositorio.guardar(crearEmpleado("Juan López", "XYZ9876"));

        List<Empleado> empleados = empleadoRepositorio.listarTodos();

        assertEquals(2, empleados.size());
        assertTrue(empleados.stream().anyMatch(empleado -> empleado.getLegajo().equals("ABC1234")));
        assertTrue(empleados.stream().anyMatch(empleado -> empleado.getLegajo().equals("XYZ9876")));
    }

    private Empleado crearEmpleado(String nombre, String legajo) {
        return Empleado.crear(
                nombre,
                legajo,
                LocalDate.of(2020, 5, 10),
                Puesto.ANALISTA,
                new BigDecimal("500000")
        );
    }
}
