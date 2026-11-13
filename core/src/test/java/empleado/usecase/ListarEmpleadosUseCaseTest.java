package empleado.usecase;

import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarEmpleadosUseCaseTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private ListarEmpleadosUseCase listarEmpleadosUseCase;

    private Empleado empleado1;
    private Empleado empleado2;

    @BeforeEach
    void setUp() {
        empleado1 = new Empleado(
                UUID.randomUUID(),
                "ABC1234",
                "Juan Pérez",
                Puesto.ANALISTA,
                600000.0,
                LocalDate.of(2025, 1, 10)
        );

        empleado2 = new Empleado(
                UUID.randomUUID(),
                "XYZ5678",
                "Ana López",
                Puesto.SUPERVISOR,
                900000.0,
                LocalDate.of(2025, 2, 20)
        );
    }

    @Test
    void deberiaRetornarListaDeEmpleadosCuandoExistenRegistros() {
        // Arrange
        List<Empleado> listaEsperada = List.of(empleado1, empleado2);
        when(empleadoRepository.listarTodos()).thenReturn(listaEsperada);

        // Act
        List<Empleado> resultado = listarEmpleadosUseCase.ejecutar();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("ABC1234", resultado.get(0).getLegajo());
        assertEquals("XYZ5678", resultado.get(1).getLegajo());
        verify(empleadoRepository, times(1)).listarTodos();
    }

    @Test
    void deberiaRetornarListaVaciaCuandoNoExistenEmpleados() {
        // Arrange
        when(empleadoRepository.listarTodos()).thenReturn(List.of());

        // Act
        List<Empleado> resultado = listarEmpleadosUseCase.ejecutar();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(empleadoRepository, times(1)).listarTodos();
    }
}