package empleado.usecase;

import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepositorio;

import java.util.List;

public class ListarEmpleadosUseCase {

    private final EmpleadoRepositorio empleadoRepositorio;

    public ListarEmpleadosUseCase(EmpleadoRepositorio empleadoRepositorio) {
        this.empleadoRepositorio = empleadoRepositorio;
    }

    public List<Empleado> ejecutar() {
        return empleadoRepositorio.listarTodos();
    }
}
