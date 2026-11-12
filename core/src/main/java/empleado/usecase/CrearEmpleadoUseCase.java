package empleado.usecase;

import empleado.exception.LegajoDuplicadoException;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepositorio;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CrearEmpleadoUseCase {

    private final EmpleadoRepositorio empleadoRepositorio;

    public CrearEmpleadoUseCase(EmpleadoRepositorio empleadoRepositorio) {
        this.empleadoRepositorio = empleadoRepositorio;
    }

    public Empleado ejecutar(String nombreCompleto, String legajo, LocalDate fechaIngreso,
                             Puesto puesto, BigDecimal salario) {
        Empleado empleado = Empleado.crear(nombreCompleto, legajo, fechaIngreso, puesto, salario);

        if (empleadoRepositorio.existePorLegajo(legajo)) {
            throw new LegajoDuplicadoException("Ya existe un empleado con el legajo " + legajo);
        }

        return empleadoRepositorio.guardar(empleado);
    }
}
