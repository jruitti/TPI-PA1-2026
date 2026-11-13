package empleado.modelo;

import empleado.exception.FechaIngresoFuturaException;
import empleado.exception.FechaIngresoNulaException;
import empleado.exception.IdNuloException;
import empleado.exception.LegajoInvalidoException;
import empleado.exception.LegajoNuloException;
import empleado.exception.NombreNuloException;
import empleado.exception.PuestoNuloException;
import empleado.exception.SalarioInvalidoException;
import empleado.exception.SalarioNuloException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmpleadoTest {

    private static final UUID ID = UUID.fromString("cabbd417-1841-4b25-8798-e8d54df1416e");
    private static final LocalDate FECHA_VALIDA = LocalDate.now().minusDays(1);
    private static final BigDecimal SALARIO_ANALISTA = new BigDecimal("500000");

    private Empleado empleadoValido() {
        return Empleado.crearEmpleado(ID, "Juan Perez", "ABC1234", FECHA_VALIDA, Puesto.ANALISTA, SALARIO_ANALISTA);
    }

    @Test
    void crearEmpleadoConDatosValidosRetornaInstanciaConLosAtributosCorrectos() {
        Empleado empleado = empleadoValido();

        assertNotNull(empleado);
        assertEquals(ID, empleado.getId());
        assertEquals("Juan Perez", empleado.getNombreCompleto());
        assertEquals("ABC1234", empleado.getLegajo());
        assertEquals(FECHA_VALIDA, empleado.getFechaIngreso());
        assertEquals(Puesto.ANALISTA, empleado.getPuesto());
        assertEquals(SALARIO_ANALISTA, empleado.getSalario());
    }

    @Test
    void crearEmpleadoConIdNuloLanzaExcepcion() {
        assertThrows(IdNuloException.class,
                () -> Empleado.crearEmpleado(null, "Juan Perez", "ABC1234", FECHA_VALIDA, Puesto.ANALISTA, SALARIO_ANALISTA));
    }

    @Test
    void crearEmpleadoConNombreNuloLanzaExcepcion() {
        assertThrows(NombreNuloException.class,
                () -> Empleado.crearEmpleado(ID, null, "ABC1234", FECHA_VALIDA, Puesto.ANALISTA, SALARIO_ANALISTA));
    }

    @Test
    void crearEmpleadoConNombreVacioLanzaExcepcion() {
        assertThrows(NombreNuloException.class,
                () -> Empleado.crearEmpleado(ID, "   ", "ABC1234", FECHA_VALIDA, Puesto.ANALISTA, SALARIO_ANALISTA));
    }

    @Test
    void crearEmpleadoConLegajoNuloLanzaExcepcion() {
        assertThrows(LegajoNuloException.class,
                () -> Empleado.crearEmpleado(ID, "Juan Perez", null, FECHA_VALIDA, Puesto.ANALISTA, SALARIO_ANALISTA));
    }

    @Test
    void crearEmpleadoConLegajoConFormatoInvalidoLanzaExcepcion() {
        assertThrows(LegajoInvalidoException.class,
                () -> Empleado.crearEmpleado(ID, "Juan Perez", "abc1234", FECHA_VALIDA, Puesto.ANALISTA, SALARIO_ANALISTA));
        assertThrows(LegajoInvalidoException.class,
                () -> Empleado.crearEmpleado(ID, "Juan Perez", "AB1234", FECHA_VALIDA, Puesto.ANALISTA, SALARIO_ANALISTA));
        assertThrows(LegajoInvalidoException.class,
                () -> Empleado.crearEmpleado(ID, "Juan Perez", "ABC123", FECHA_VALIDA, Puesto.ANALISTA, SALARIO_ANALISTA));
    }

    @Test
    void crearEmpleadoConFechaIngresoNulaLanzaExcepcion() {
        assertThrows(FechaIngresoNulaException.class,
                () -> Empleado.crearEmpleado(ID, "Juan Perez", "ABC1234", null, Puesto.ANALISTA, SALARIO_ANALISTA));
    }

    @Test
    void crearEmpleadoConFechaIngresoFuturaLanzaExcepcion() {
        assertThrows(FechaIngresoFuturaException.class,
                () -> Empleado.crearEmpleado(ID, "Juan Perez", "ABC1234", LocalDate.now().plusDays(1), Puesto.ANALISTA, SALARIO_ANALISTA));
    }

    @Test
    void crearEmpleadoConPuestoNuloLanzaExcepcion() {
        assertThrows(PuestoNuloException.class,
                () -> Empleado.crearEmpleado(ID, "Juan Perez", "ABC1234", FECHA_VALIDA, null, SALARIO_ANALISTA));
    }

    @Test
    void crearEmpleadoConSalarioNuloLanzaExcepcion() {
        assertThrows(SalarioNuloException.class,
                () -> Empleado.crearEmpleado(ID, "Juan Perez", "ABC1234", FECHA_VALIDA, Puesto.ANALISTA, null));
    }

    @Test
    void crearEmpleadoConSalarioNoPositivoLanzaExcepcion() {
        assertThrows(SalarioInvalidoException.class,
                () -> Empleado.crearEmpleado(ID, "Juan Perez", "ABC1234", FECHA_VALIDA, Puesto.ANALISTA, BigDecimal.ZERO));
        assertThrows(SalarioInvalidoException.class,
                () -> Empleado.crearEmpleado(ID, "Juan Perez", "ABC1234", FECHA_VALIDA, Puesto.ANALISTA, new BigDecimal("-1")));
    }

    @Test
    void crearEmpleadoConSalarioBajoElPisoDelPuestoLanzaExcepcion() {
        assertThrows(SalarioInvalidoException.class,
                () -> Empleado.crearEmpleado(ID, "Juan Perez", "ABC1234", FECHA_VALIDA, Puesto.ANALISTA, new BigDecimal("499999")));
        assertThrows(SalarioInvalidoException.class,
                () -> Empleado.crearEmpleado(ID, "Juan Perez", "ABC1234", FECHA_VALIDA, Puesto.SUPERVISOR, new BigDecimal("799999")));
        assertThrows(SalarioInvalidoException.class,
                () -> Empleado.crearEmpleado(ID, "Juan Perez", "ABC1234", FECHA_VALIDA, Puesto.GERENTE, new BigDecimal("1199999")));
    }

    @Test
    void crearEmpleadoConElSalarioMinimoDeCadaPuestoEsAceptado() {
        Empleado analista = Empleado.crearEmpleado(ID, "Ana Analista", "ABC1234", FECHA_VALIDA, Puesto.ANALISTA, new BigDecimal("500000"));
        Empleado supervisor = Empleado.crearEmpleado(ID, "Sara Supervisora", "DEF5678", FECHA_VALIDA, Puesto.SUPERVISOR, new BigDecimal("800000"));
        Empleado gerente = Empleado.crearEmpleado(ID, "Gustavo Gerente", "GHI9012", FECHA_VALIDA, Puesto.GERENTE, new BigDecimal("1200000"));

        assertEquals(new BigDecimal("500000"), analista.getSalario());
        assertEquals(new BigDecimal("800000"), supervisor.getSalario());
        assertEquals(new BigDecimal("1200000"), gerente.getSalario());
    }
}
