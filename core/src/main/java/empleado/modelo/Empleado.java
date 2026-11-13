package empleado.modelo;
import empleado.exception.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Empleado {
    private final UUID id;
    private final String nombreCompleto;
    private final String legajo;
    private final LocalDate fechaIngreso;
    private final Puesto puesto;
    private final BigDecimal salario;

    private Empleado(UUID id,
                     String nombreCompleto,
                     String legajo,
                     LocalDate fechaIngreso,
                     Puesto puesto,
                     BigDecimal salario) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.legajo = legajo;
        this.fechaIngreso = fechaIngreso;
        this.puesto = puesto;
        this.salario = salario;
    }

    public static Empleado crearEmpleado(UUID id,
                                         String nombreCompleto,
                                         String legajo,
                                         LocalDate fechaIngreso,
                                         Puesto puesto,
                                         BigDecimal salario) {

        if (id == null) {
            throw new IdNuloException("El identificador no puede ser nulo");
        }
        if (nombreCompleto == null || nombreCompleto.isBlank()) {
            throw new NombreNuloException("El nombre no puede ser nulo o vacio");
        }
        if (legajo == null) {
            throw new LegajoNuloException("El legajo no puede ser nulo");
        }
        if (!legajo.matches("^[A-Z]{3}\\d{4}$")) {
            throw new LegajoInvalidoException("El legajo debe tener 3 letras mayusculas seguidas de 4 numeros (ej. ABC1234)");
        }
        if (fechaIngreso == null) {
            throw new FechaIngresoNulaException("La fecha de ingreso no puede ser nula");
        }
        if (fechaIngreso.isAfter(LocalDate.now())) {
            throw new FechaIngresoFuturaException("La fecha de ingreso no puede ser posterior a la fecha actual");
        }
        if (puesto == null) {
            throw new PuestoNuloException("El puesto no puede ser nulo");
        }
        if (salario == null) {
            throw new SalarioNuloException("El salario no puede ser nulo");
        }
        if (salario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new SalarioInvalidoException("El salario debe ser mayor a cero");
        }
        if (salario.compareTo(puesto.getSalarioMinimo()) < 0) {
            throw new SalarioInvalidoException("El salario no puede ser inferior al piso salarial de " + puesto);
        }

        return new Empleado(id, nombreCompleto, legajo, fechaIngreso, puesto, salario);
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
