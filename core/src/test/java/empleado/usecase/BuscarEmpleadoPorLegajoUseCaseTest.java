package empleado.usecase;

import empleado.exception.EmpleadoNoEncontradoException;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarEmpleadoPorLegajoUseCaseTest {

    private static final String LEGAJO = "XYZ9876";

    @Mock
    private EmpleadoRepositorio empleadoRepositorio;

    @InjectMocks
    private BuscarEmpleadoPorLegajoUseCase buscarEmpleadoPorLegajoUseCase;

    private Empleado empleado;

    @BeforeEach
    void crearEmpleadoDePrueba() {
        empleado = Empleado.crear(
                "Ana Pérez",
                LEGAJO,
                LocalDate.of(2020, 5, 10),
                Puesto.ANALISTA,
                new BigDecimal("500000")
        );
    }

    @Test
    void devuelveEmpleadoCuandoElLegajoExiste() {
        when(empleadoRepositorio.buscarPorLegajo(LEGAJO)).thenReturn(Optional.of(empleado));

        Empleado resultado = buscarEmpleadoPorLegajoUseCase.ejecutar(LEGAJO);

        assertSame(empleado, resultado);
        verify(empleadoRepositorio).buscarPorLegajo(LEGAJO);
    }

    @Test
    void lanzaExcepcionCuandoElLegajoNoExiste() {
        when(empleadoRepositorio.buscarPorLegajo(LEGAJO)).thenReturn(Optional.empty());

        assertThrows(EmpleadoNoEncontradoException.class,
                () -> buscarEmpleadoPorLegajoUseCase.ejecutar(LEGAJO));

        verify(empleadoRepositorio).buscarPorLegajo(LEGAJO);
    }
}
