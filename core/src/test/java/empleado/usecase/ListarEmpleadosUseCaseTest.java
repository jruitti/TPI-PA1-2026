package empleado.usecase;

import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListarEmpleadosUseCaseTest {

    private EmpleadoRepository mockEmpleadoRepository;
    private ListarEmpleadosUseCase listarEmpleadosUseCase;

    @BeforeEach
    void setUp() {
        mockEmpleadoRepository = mock(EmpleadoRepository.class);
        listarEmpleadosUseCase = new ListarEmpleadosUseCase(mockEmpleadoRepository);
    }

    private Empleado empleadoDePrueba(String legajo) {
        return Empleado.crearEmpleado(UUID.randomUUID(), "Juan Perez", legajo, LocalDate.now().minusDays(1), Puesto.ANALISTA, new BigDecimal("500000"));
    }

    @Test
    void ejecutarConEmpleadosRegistradosRetornaElListado() {
        when(mockEmpleadoRepository.findAll()).thenReturn(List.of(empleadoDePrueba("ABC1234"), empleadoDePrueba("DEF5678")));

        List<Empleado> empleados = listarEmpleadosUseCase.ejecutar();

        assertEquals(2, empleados.size());
    }

    @Test
    void ejecutarSinEmpleadosRegistradosRetornaListaVacia() {
        when(mockEmpleadoRepository.findAll()).thenReturn(List.of());

        List<Empleado> empleados = listarEmpleadosUseCase.ejecutar();

        assertTrue(empleados.isEmpty());
    }
}