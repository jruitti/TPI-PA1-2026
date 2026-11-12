package empleado.modelo;

import empleado.exception.AtributoEmpleadoObligatorioException;
import empleado.exception.FechaIngresoInvalidaException;
import empleado.exception.LegajoInvalidoException;
import empleado.exception.NombreEmpleadoInvalidoException;
import empleado.exception.SalarioEmpleadoInvalidoException;
import empleado.exception.SalarioMinimoNoAlcanzadoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmpleadoTest {

    private static final String NOMBRE_VALIDO = "Ana Pérez";
    private static final String LEGAJO_VALIDO = "ABC1234";
    private static final LocalDate FECHA_INGRESO_VALIDA = LocalDate.of(2020, 5, 10);

    @Test
    void creaEmpleadoValidoYGeneraSuIdentificador() {
        Empleado empleado = Empleado.crear(
                NOMBRE_VALIDO,
                LEGAJO_VALIDO,
                FECHA_INGRESO_VALIDA,
                Puesto.ANALISTA,
                new BigDecimal("500000")
        );

        assertNotNull(empleado.getId());
        assertEquals(NOMBRE_VALIDO, empleado.getNombreCompleto());
        assertEquals(LEGAJO_VALIDO, empleado.getLegajo());
        assertEquals(FECHA_INGRESO_VALIDA, empleado.getFechaIngreso());
        assertEquals(Puesto.ANALISTA, empleado.getPuesto());
        assertEquals(new BigDecimal("500000"), empleado.getSalario());
    }

    @Test
    void reconstruyeEmpleadoConElIdentificadorExistente() {
        UUID idExistente = UUID.fromString("11111111-2222-3333-4444-555555555555");

        Empleado empleado = Empleado.reconstruir(
                idExistente,
                NOMBRE_VALIDO,
                LEGAJO_VALIDO,
                FECHA_INGRESO_VALIDA,
                Puesto.ANALISTA,
                new BigDecimal("500000")
        );

        assertEquals(idExistente, empleado.getId());
    }

    @Test
    void rechazaAtributosObligatoriosNulos() {
        assertThrows(AtributoEmpleadoObligatorioException.class,
                () -> Empleado.crear(null, LEGAJO_VALIDO, FECHA_INGRESO_VALIDA,
                        Puesto.ANALISTA, new BigDecimal("500000")));
        assertThrows(AtributoEmpleadoObligatorioException.class,
                () -> Empleado.crear(NOMBRE_VALIDO, null, FECHA_INGRESO_VALIDA,
                        Puesto.ANALISTA, new BigDecimal("500000")));
        assertThrows(AtributoEmpleadoObligatorioException.class,
                () -> Empleado.crear(NOMBRE_VALIDO, LEGAJO_VALIDO, null,
                        Puesto.ANALISTA, new BigDecimal("500000")));
        assertThrows(AtributoEmpleadoObligatorioException.class,
                () -> Empleado.crear(NOMBRE_VALIDO, LEGAJO_VALIDO, FECHA_INGRESO_VALIDA,
                        null, new BigDecimal("500000")));
        assertThrows(AtributoEmpleadoObligatorioException.class,
                () -> Empleado.crear(NOMBRE_VALIDO, LEGAJO_VALIDO, FECHA_INGRESO_VALIDA,
                        Puesto.ANALISTA, null));
    }

    @ParameterizedTest
    @CsvSource({"''", "'   '"})
    void rechazaNombreVacio(String nombre) {
        assertThrows(NombreEmpleadoInvalidoException.class,
                () -> Empleado.crear(nombre, LEGAJO_VALIDO, FECHA_INGRESO_VALIDA,
                        Puesto.ANALISTA, new BigDecimal("500000")));
    }

    @ParameterizedTest
    @CsvSource({"abc1234", "AB1234", "ABCD1234", "ABC123", "ABC12345", "ABC12X4", "' ABC1234'"})
    void rechazaLegajoQueNoCumpleElFormatoInstitucional(String legajo) {
        assertThrows(LegajoInvalidoException.class,
                () -> Empleado.crear(NOMBRE_VALIDO, legajo, FECHA_INGRESO_VALIDA,
                        Puesto.ANALISTA, new BigDecimal("500000")));
    }


    @Test
    void rechazaFechaDeIngresoPosteriorALaFechaActual() {
        assertThrows(FechaIngresoInvalidaException.class,
                () -> Empleado.crear(NOMBRE_VALIDO, LEGAJO_VALIDO, LocalDate.now().plusDays(1),
                        Puesto.ANALISTA, new BigDecimal("500000")));
    }


    @ParameterizedTest
    @CsvSource({"0", "-1", "-500000"})
    void rechazaSalarioMenorOIgualACero(String salario) {
        assertThrows(SalarioEmpleadoInvalidoException.class,
                () -> Empleado.crear(NOMBRE_VALIDO, LEGAJO_VALIDO, FECHA_INGRESO_VALIDA,
                        Puesto.ANALISTA, new BigDecimal(salario)));
    }

    @ParameterizedTest
    @CsvSource({"ANALISTA,499999.99", "SUPERVISOR,799999.99", "GERENTE,1199999.99"})
    void rechazaSalarioInferiorAlMinimoDelPuesto(Puesto puesto, String salario) {
        assertThrows(SalarioMinimoNoAlcanzadoException.class,
                () -> Empleado.crear(NOMBRE_VALIDO, LEGAJO_VALIDO, FECHA_INGRESO_VALIDA,
                        puesto, new BigDecimal(salario)));
    }

    @ParameterizedTest
    @CsvSource({"ANALISTA,500000", "SUPERVISOR,800000", "GERENTE,1200000"})
    void aceptaElSalarioMinimoExactoDelPuesto(Puesto puesto, String salario) {
        Empleado empleado = Empleado.crear(NOMBRE_VALIDO, LEGAJO_VALIDO, FECHA_INGRESO_VALIDA,
                puesto, new BigDecimal(salario));

        assertNotNull(empleado.getId());
        assertEquals(puesto, empleado.getPuesto());
        assertEquals(new BigDecimal(salario), empleado.getSalario());
    }

    @Test
    void aceptaFechaDeIngresoIgualAFechaActual() {
        Empleado empleado = Empleado.crear(NOMBRE_VALIDO, LEGAJO_VALIDO, LocalDate.now(),
                Puesto.ANALISTA, new BigDecimal("500000"));

        assertEquals(LocalDate.now(), empleado.getFechaIngreso());
    }
}

