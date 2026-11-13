package empleado.usecase;

import empleado.exception.LegajoDuplicadoException;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrearEmpleadoUseCaseTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private CrearEmpleadoUseCase crearEmpleadoUseCase;

    private Empleado empleadoValido;

    @BeforeEach
    void setUp() {
        empleadoValido = new Empleado(
                UUID.randomUUID(),
                "ABC1234",
                "Juan Pérez",
                Puesto.ANALISTA,
                600000.0,
                LocalDate.of(2025, 1, 10)
        );
    }

    @Test
    void deberiaCrearEmpleadoExitosamenteCuandoLegajoNoExiste() {
        // Arrange
        when(empleadoRepository.existePorLegajo("ABC1234")).thenReturn(false);
        when(empleadoRepository.guardar(any(Empleado.class))).thenReturn(empleadoValido);

        // Act
        Empleado resultado = crearEmpleadoUseCase.ejecutar(empleadoValido);

        // Assert
        assertNotNull(resultado);
        assertEquals("ABC1234", resultado.getLegajo());
        verify(empleadoRepository, times(1)).existePorLegajo("ABC1234");
        verify(empleadoRepository, times(1)).guardar(empleadoValido);
    }

    @Test
    void deberiaLanzarLegajoDuplicadoExceptionCuandoLegajoYaExiste() {
        // Arrange
        when(empleadoRepository.existePorLegajo("ABC1234")).thenReturn(true);

        // Act & Assert
        assertThrows(LegajoDuplicadoException.class, () -> {
            crearEmpleadoUseCase.ejecutar(empleadoValido);
        });

        // Verificamos que NUNCA intente guardar si el legajo ya existe
        verify(empleadoRepository, times(1)).existePorLegajo("ABC1234");
        verify(empleadoRepository, never()).guardar(any(Empleado.class));
    }
}
