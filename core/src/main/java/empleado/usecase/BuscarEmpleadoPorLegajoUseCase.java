package empleado.usecase;

import empleado.exception.EmpleadoNoEncontradoException;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepository;

public class BuscarEmpleadoPorLegajoUseCase {

    private final EmpleadoRepository empleadoRepository;

    public BuscarEmpleadoPorLegajoUseCase(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public Empleado ejecutar(String legajo) {
        return empleadoRepository.buscarPorLegajo(legajo)
                .orElseThrow(() -> new EmpleadoNoEncontradoException("No se encontró ningún empleado con el legajo: " + legajo));
    }
}
