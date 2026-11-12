package empleado.modelo;

import empleado.exception.AtributoEmpleadoObligatorioException;
import empleado.exception.FechaIngresoInvalidaException;
import empleado.exception.LegajoInvalidoException;
import empleado.exception.NombreEmpleadoInvalidoException;
import empleado.exception.SalarioEmpleadoInvalidoException;
import empleado.exception.SalarioMinimoNoAlcanzadoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public final class Empleado {

    private static final BigDecimal MINIMO_ANALISTA = new BigDecimal("500000");
    private static final BigDecimal MINIMO_SUPERVISOR = new BigDecimal("800000");
    private static final BigDecimal MINIMO_GERENTE = new BigDecimal("1200000");

    private final UUID id;
    private final String nombreCompleto;
    private final String legajo;
    private final LocalDate fechaIngreso;
    private final Puesto puesto;
    private final BigDecimal salario;

    private Empleado(UUID id, String nombreCompleto, String legajo, LocalDate fechaIngreso,
                     Puesto puesto, BigDecimal salario) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.legajo = legajo;
        this.fechaIngreso = fechaIngreso;
        this.puesto = puesto;
        this.salario = salario;
    }

    public static Empleado crear(String nombreCompleto, String legajo, LocalDate fechaIngreso,
                                 Puesto puesto, BigDecimal salario) {
        validarAtributosObligatorios(nombreCompleto, legajo, fechaIngreso, puesto, salario);
        validarNombre(nombreCompleto);
        validarLegajo(legajo);
        validarFechaIngreso(fechaIngreso);
        validarSalario(puesto, salario);

        return new Empleado(UUID.randomUUID(), nombreCompleto.trim(), legajo,
                fechaIngreso, puesto, salario);
    }

    public static Empleado reconstruir(UUID id, String nombreCompleto, String legajo,
                                       LocalDate fechaIngreso, Puesto puesto, BigDecimal salario) {
        if (id == null) {
            throw new AtributoEmpleadoObligatorioException("El identificador del empleado es obligatorio");
        }
        validarAtributosObligatorios(nombreCompleto, legajo, fechaIngreso, puesto, salario);
        validarNombre(nombreCompleto);
        validarLegajo(legajo);
        validarFechaIngreso(fechaIngreso);
        validarSalario(puesto, salario);

        return new Empleado(id, nombreCompleto.trim(), legajo, fechaIngreso, puesto, salario);
    }

    private static void validarAtributosObligatorios(String nombreCompleto, String legajo,
                                                      LocalDate fechaIngreso, Puesto puesto,
                                                      BigDecimal salario) {
        if (nombreCompleto == null || legajo == null || fechaIngreso == null
                || puesto == null || salario == null) {
            throw new AtributoEmpleadoObligatorioException(
                    "Todos los atributos del empleado son obligatorios");
        }
    }

    private static void validarNombre(String nombreCompleto) {
        if (nombreCompleto.isBlank()) {
            throw new NombreEmpleadoInvalidoException("El nombre completo no puede estar vacío");
        }
    }

    private static void validarLegajo(String legajo) {
        if (!legajo.matches("[A-Z]{3}[0-9]{4}")) {
            throw new LegajoInvalidoException(
                    "El legajo debe tener tres letras mayúsculas seguidas de cuatro números");
        }
    }

    private static void validarFechaIngreso(LocalDate fechaIngreso) {
        if (fechaIngreso.isAfter(LocalDate.now())) {
            throw new FechaIngresoInvalidaException("La fecha de ingreso no puede ser futura");
        }
    }

    private static void validarSalario(Puesto puesto, BigDecimal salario) {
        if (salario.signum() <= 0) {
            throw new SalarioEmpleadoInvalidoException("El salario debe ser mayor a cero");
        }

        BigDecimal minimo = switch (puesto) {
            case ANALISTA -> MINIMO_ANALISTA;
            case SUPERVISOR -> MINIMO_SUPERVISOR;
            case GERENTE -> MINIMO_GERENTE;
        };
        if (salario.compareTo(minimo) < 0) {
            throw new SalarioMinimoNoAlcanzadoException(
                    "El salario para el puesto " + puesto + " debe ser al menos " + minimo);
        }
    }

    public UUID getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getLegajo() {
        return legajo;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public BigDecimal getSalario() {
        return salario;
    }
}
