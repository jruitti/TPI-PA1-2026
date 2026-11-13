package empleado.usecase;

import empleado.input.ListarEmpleadosInput;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepository;

import java.util.List;

public class ListarEmpleadosUseCase implements ListarEmpleadosInput {

    private final EmpleadoRepository empleadoRepository;

    public ListarEmpleadosUseCase(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public List<Empleado> ejecutar() {
        return empleadoRepository.findAll();
    }
}
