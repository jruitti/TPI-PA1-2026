package empleado.modelo;

import empleado.exception.FechaIngresoInvalidaException;
import empleado.exception.LegajoInvalidoException;
import empleado.exception.AtributoObligatorioException;
import empleado.exception.SalarioInsuficienteException;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static empleado.modelo.Puesto.*;
import static org.junit.jupiter.api.Assertions.*;

class EmpleadoTest {

    @Test
    void crearEmpleadoExitosamente() {
        // Arrange
        UUID id = UUID.randomUUID();
        String legajo = "ABC1234";
        String nombreCompleto = "María Gómez";
        Puesto puesto = ANALISTA;
        Double salario = 600000.0;
        LocalDate fechaIngreso = LocalDate.of(2025, 3, 15);

        // Act
        Empleado empleado = new Empleado(id, legajo, nombreCompleto, puesto, salario, fechaIngreso);

        // Assert
        assertNotNull(empleado);
        assertEquals(id, empleado.getId());
        assertEquals(legajo, empleado.getLegajo());
        assertEquals(nombreCompleto, empleado.getNombreCompleto());
        assertEquals(puesto, empleado.getPuesto());
        assertEquals(salario, empleado.getSalario());
        assertEquals(fechaIngreso, empleado.getFechaIngreso());
    }

    @Test
    void deberiaLanzarLegajoInvalidoException() {
        // Arrange & Act & Assert
        assertThrows(LegajoInvalidoException.class, () -> {
            new Empleado(
                    UUID.randomUUID(),
                    "123ABCD", // Legajo inválido (debe ser 3 letras y 4 números: ABC1234)
                    "Juan Pérez",
                    ANALISTA,
                    600000.0,
                    LocalDate.of(2023, 5, 10)
            );
        });
    }

    @Test
    void deberiaLanzarAtributoObligatorioException() {
        // Arrange & Act & Assert
        assertThrows(AtributoObligatorioException.class, () -> {
            new Empleado(
                    UUID.randomUUID(),
                    "ABC1234", // Legajo válido
                    null,       // Nombre nulo (inválido)
                    ANALISTA,
                    600000.0,
                    LocalDate.of(2023, 5, 10)
            );
        });
    }

    @Test
    void deberiaLanzarSalarioInsuficienteException() {
        // Arrange & Act & Assert
        assertThrows(SalarioInsuficienteException.class, () -> {
            new Empleado(
                    UUID.randomUUID(),
                    "ABC1234",
                    "Juan Pérez",
                    ANALISTA,
                    0.0, // Salario inválido (cero o negativo)
                    LocalDate.of(2023, 5, 10)
            );
        });
    }

    @Test
    void deberiaLanzarSalarioInsuficienteExceptionCuandoAnalistaTengaSalarioMenorA500000() {
        // Arrange & Act & Assert
        assertThrows(SalarioInsuficienteException.class, () -> {
            new Empleado(
                    UUID.randomUUID(),
                    "ABC1234",
                    "Juan Pérez",
                    ANALISTA,
                    499999.99, // Salario menor a 500.000
                    LocalDate.of(2023, 5, 10)
            );
        });
    }

    @Test
    void deberiaLanzarSalarioInsuficienteExceptionCuandoSupervisorTengaSalarioMenorA800000() {
        // Arrange & Act & Assert
        assertThrows(SalarioInsuficienteException.class, () -> {
            new Empleado(
                    UUID.randomUUID(),
                    "ABC1234",
                    "Juan Pérez",
                    SUPERVISOR,
                    750000.0, // Salario menor a 800.000
                    LocalDate.of(2023, 5, 10)
            );
        });
    }

    @Test
    void deberiaLanzarSalarioInsuficienteExceptionCuandoGerenteTengaSalarioMenorA1200000() {
        // Arrange & Act & Assert
        assertThrows(SalarioInsuficienteException.class, () -> {
            new Empleado(
                    UUID.randomUUID(),
                    "ABC1234",
                    "Juan Pérez",
                    GERENTE,
                    1199999.0, // Salario menor a 1.200.000
                    LocalDate.of(2023, 5, 10)
            );
        });
    }

    @Test
    void deberiaLanzarFechaIngresoInvalidaException(){
        assertThrows(FechaIngresoInvalidaException.class, () -> {
            new Empleado(
                    UUID.randomUUID(),
                    "ABC1234",
                    "Juan Pérez",
                    ANALISTA,
                    550000.0,
                    LocalDate.of(2027, 2, 10) //Fecha posterior a fecha actual
            );
        });
    }











}