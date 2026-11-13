package empleado.usecase;

import empleado.exception.EmpleadoNoEncontradoException;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarEmpleadoPorLegajoUseCaseTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private BuscarEmpleadoPorLegajoUseCase buscarEmpleadoPorLegajoUseCase;

    private Empleado empleadoEsperado;

    @BeforeEach
    void setUp() {
        empleadoEsperado = new Empleado(
                UUID.randomUUID(),
                "ABC1234",
                "María Gómez",
                Puesto.ANALISTA,
                600000.0,
                LocalDate.of(2025, 3, 15)
        );
    }

    @Test
    void deberiaRetornarEmpleadoCuandoLegajoExiste() {
        // Arrange
        when(empleadoRepository.buscarPorLegajo("ABC1234")).thenReturn(Optional.of(empleadoEsperado));

        // Act
        Empleado resultado = buscarEmpleadoPorLegajoUseCase.ejecutar("ABC1234");

        // Assert
        assertNotNull(resultado);
        assertEquals("ABC1234", resultado.getLegajo());
        assertEquals("María Gómez", resultado.getNombreCompleto());
        verify(empleadoRepository, times(1)).buscarPorLegajo("ABC1234");
    }

    @Test
    void deberiaLanzarEmpleadoNoEncontradoExceptionCuandoLegajoNoExiste() {
        // Arrange
        when(empleadoRepository.buscarPorLegajo("XYZ9999")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmpleadoNoEncontradoException.class, () -> {
            buscarEmpleadoPorLegajoUseCase.ejecutar("XYZ9999");
        });

        verify(empleadoRepository, times(1)).buscarPorLegajo("XYZ9999");
    }
}