package empleado.usecase;

import empleado.exception.EmpleadoNoEncontradoException;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepositorio;

public class BuscarEmpleadoPorLegajoUseCase {

    private final EmpleadoRepositorio empleadoRepositorio;

    public BuscarEmpleadoPorLegajoUseCase(EmpleadoRepositorio empleadoRepositorio) {
        this.empleadoRepositorio = empleadoRepositorio;
    }

    public Empleado ejecutar(String legajo) {
        return empleadoRepositorio.buscarPorLegajo(legajo)
                .orElseThrow(() -> new EmpleadoNoEncontradoException(
                        "No se encontró un empleado con el legajo " + legajo));
    }
}
