package empleado.usecase;

import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepository;

import java.util.List;

public class ListarEmpleadosUseCase {

    private final EmpleadoRepository empleadoRepository;

    public ListarEmpleadosUseCase(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> ejecutar() {
        return empleadoRepository.listarTodos();
    }
}