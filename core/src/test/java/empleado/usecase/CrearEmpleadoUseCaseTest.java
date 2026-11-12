package empleado.usecase;
import empleado.exception.FechaIngresoFuturaException;
import empleado.exception.LegajoExistenteException;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)

public class CrearEmpleadoUseCaseTest {
    private static final LocalDate FECHA_VALIDA = LocalDate.now().minusDays(1);
    private static final BigDecimal SALARIO_ANALISTA = new BigDecimal("500000");

    private EmpleadoRepository mockEmpleadoRepository;
    private CrearEmpleadoUseCase crearEmpleadoUseCase;

    @BeforeEach
    void setUp() {
        mockEmpleadoRepository = mock(EmpleadoRepository.class);
        crearEmpleadoUseCase = new CrearEmpleadoUseCase(mockEmpleadoRepository);
    }

    @Test
    void ejecutarConDatosValidosGuardaYRetornaElEmpleado() {
        when(mockEmpleadoRepository.existByLegajo("ABC1234")).thenReturn(false);

        Empleado empleado = crearEmpleadoUseCase.ejecutar("Juan Perez", "ABC1234", FECHA_VALIDA, Puesto.ANALISTA, SALARIO_ANALISTA);

        assertNotNull(empleado.getId());
        assertEquals("Juan Perez", empleado.getNombreCompleto());
        assertEquals("ABC1234", empleado.getLegajo());
        verify(mockEmpleadoRepository).save(empleado);
    }

    @Test
    void ejecutarConLegajoExistenteLanzaExcepcionYNoGuarda() {
        when(mockEmpleadoRepository.existByLegajo("ABC1234")).thenReturn(true);

        assertThrows(LegajoExistenteException.class, () ->
                crearEmpleadoUseCase.ejecutar("Juan Perez", "ABC1234", FECHA_VALIDA, Puesto.ANALISTA, SALARIO_ANALISTA));

        verify(mockEmpleadoRepository, never()).save(any(Empleado.class));
    }

    @Test
    void ejecutarConFechaIngresoFuturaLanzaExcepcionYNoGuarda() {
        when(mockEmpleadoRepository.existByLegajo("ABC1234")).thenReturn(false);

        assertThrows(FechaIngresoFuturaException.class, () ->
                crearEmpleadoUseCase.ejecutar("Juan Perez", "ABC1234", LocalDate.now().plusDays(1), Puesto.ANALISTA, SALARIO_ANALISTA));

        verify(mockEmpleadoRepository, never()).save(any(Empleado.class));
    }
}
