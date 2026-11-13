package empleado.usecase;

import empleado.exception.LegajoDuplicadoException;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepository;

public class CrearEmpleadoUseCase {

    private final EmpleadoRepository empleadoRepository;

    public CrearEmpleadoUseCase(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public Empleado ejecutar(Empleado empleado) {
        if (empleadoRepository.existePorLegajo(empleado.getLegajo())) {
            throw new LegajoDuplicadoException("Ya existe un empleado registrado con el legajo: " + empleado.getLegajo());
        }
        return empleadoRepository.guardar(empleado);
    }
}