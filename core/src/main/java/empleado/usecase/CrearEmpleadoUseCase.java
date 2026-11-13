package empleado.usecase;

import empleado.exception.LegajoExistenteException;
import empleado.input.CrearEmpleadoInput;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.output.EmpleadoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CrearEmpleadoUseCase implements CrearEmpleadoInput {

    private final EmpleadoRepository empleadoRepository;

    public CrearEmpleadoUseCase(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public Empleado ejecutar(String nombreCompleto,
                             String legajo,
                             LocalDate fechaIngreso,
                             Puesto puesto,
                             BigDecimal salario) {

        if (empleadoRepository.existByLegajo(legajo)) {
            throw new LegajoExistenteException("Ya existe un empleado con el legajo " + legajo);
        }

        Empleado empleado = Empleado.crearEmpleado(UUID.randomUUID(), nombreCompleto, legajo, fechaIngreso, puesto, salario);

        empleadoRepository.save(empleado);
        return empleado;
    }
}