package empleado.usecase;

import empleado.exception.EmpleadoNoEncontradoException;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarEmpleadoPorLegajoUseCaseTest {

    private static final UUID ID = UUID.fromString("cabbd417-1841-4b25-8798-e8d54df1416e");

    private EmpleadoRepository mockEmpleadoRepository;
    private BuscarEmpleadoPorLegajoUseCase buscarEmpleadoPorLegajoUseCase;

    @BeforeEach
    void setUp() {
        mockEmpleadoRepository = mock(EmpleadoRepository.class);
        buscarEmpleadoPorLegajoUseCase = new BuscarEmpleadoPorLegajoUseCase(mockEmpleadoRepository);
    }

    private Empleado empleadoDePrueba() {
        return Empleado.crearEmpleado(ID, "Juan Perez", "ABC1234", LocalDate.now().minusDays(1), Puesto.ANALISTA, new BigDecimal("500000"));
    }

    @Test
    void ejecutarConLegajoExistenteRetornaElEmpleado() {
        Empleado empleado = empleadoDePrueba();
        when(mockEmpleadoRepository.findByLegajo("ABC1234")).thenReturn(Optional.of(empleado));

        Empleado resultado = buscarEmpleadoPorLegajoUseCase.ejecutar("ABC1234");

        assertSame(empleado, resultado);
    }

    @Test
    void ejecutarConLegajoInexistenteLanzaExcepcion() {
        when(mockEmpleadoRepository.findByLegajo("ABC1234")).thenReturn(Optional.empty());

        assertThrows(EmpleadoNoEncontradoException.class, () ->
                buscarEmpleadoPorLegajoUseCase.ejecutar("ABC1234"));
    }
}