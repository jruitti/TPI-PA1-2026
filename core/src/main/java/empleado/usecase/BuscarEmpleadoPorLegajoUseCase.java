package empleado.usecase;

import empleado.exception.EmpleadoNoEncontradoException;
import empleado.input.BuscarEmpleadoPorLegajoInput;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepository;

public class BuscarEmpleadoPorLegajoUseCase implements BuscarEmpleadoPorLegajoInput {

    private final EmpleadoRepository empleadoRepository;

    public BuscarEmpleadoPorLegajoUseCase(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public Empleado ejecutar(String legajo) {
        return empleadoRepository.findByLegajo(legajo)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("No existe un empleado con el legajo " + legajo));
    }
}