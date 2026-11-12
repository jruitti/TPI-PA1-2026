package empleado.usecase;

import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarEmpleadosUseCaseTest {

    @Mock
    private EmpleadoRepositorio empleadoRepositorio;

    @InjectMocks
    private ListarEmpleadosUseCase listarEmpleadosUseCase;

    @Test
    void devuelveLaListaDeEmpleadosDelRepositorio() {
        List<Empleado> empleados = List.of(
                crearEmpleado("Ana Pérez", "ABC1234"),
                crearEmpleado("Juan López", "XYZ9876")
        );
        when(empleadoRepositorio.listarTodos()).thenReturn(empleados);

        List<Empleado> resultado = listarEmpleadosUseCase.ejecutar();

        assertSame(empleados, resultado);
        verify(empleadoRepositorio).listarTodos();
    }

    @Test
    void devuelveListaVaciaCuandoNoHayEmpleadosRegistrados() {
        List<Empleado> empleados = List.of();
        when(empleadoRepositorio.listarTodos()).thenReturn(empleados);

        List<Empleado> resultado = listarEmpleadosUseCase.ejecutar();

        assertSame(empleados, resultado);
        verify(empleadoRepositorio).listarTodos();
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
