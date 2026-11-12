package empleado.usecase;

import empleado.exception.LegajoDuplicadoException;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrearEmpleadoUseCaseTest {

    private static final String NOMBRE = "Ana Pérez";
    private static final String LEGAJO = "ABC1234";
    private static final LocalDate FECHA_INGRESO = LocalDate.of(2020, 5, 10);
    private static final Puesto PUESTO = Puesto.ANALISTA;
    private static final BigDecimal SALARIO = new BigDecimal("500000");

    @Mock
    private EmpleadoRepositorio empleadoRepositorio;

    @InjectMocks
    private CrearEmpleadoUseCase crearEmpleadoUseCase;

    @Test
    void creaYGuardaEmpleadoCuandoElLegajoNoExiste() {
        when(empleadoRepositorio.existePorLegajo(LEGAJO)).thenReturn(false);
        when(empleadoRepositorio.guardar(any(Empleado.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Empleado resultado = crearEmpleadoUseCase.ejecutar(
                NOMBRE, LEGAJO, FECHA_INGRESO, PUESTO, SALARIO);

        ArgumentCaptor<Empleado> empleadoGuardado = ArgumentCaptor.forClass(Empleado.class);
        verify(empleadoRepositorio).guardar(empleadoGuardado.capture());
        assertSame(empleadoGuardado.getValue(), resultado);
        assertEquals(NOMBRE, resultado.getNombreCompleto());
        assertEquals(LEGAJO, resultado.getLegajo());
        assertEquals(FECHA_INGRESO, resultado.getFechaIngreso());
        assertEquals(PUESTO, resultado.getPuesto());
        assertEquals(SALARIO, resultado.getSalario());
    }

    @Test
    void rechazaLaCreacionCuandoElLegajoYaExiste() {
        when(empleadoRepositorio.existePorLegajo(LEGAJO)).thenReturn(true);

        assertThrows(LegajoDuplicadoException.class,
                () -> crearEmpleadoUseCase.ejecutar(
                        NOMBRE, LEGAJO, FECHA_INGRESO, PUESTO, SALARIO));

        verify(empleadoRepositorio, never()).guardar(any(Empleado.class));
    }
}
