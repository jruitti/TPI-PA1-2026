package empleado.output;

import empleado.modelo.Empleado;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository {

    boolean existByLegajo(String legajo);

    boolean save(Empleado empleado);

    Optional<Empleado> findByLegajo(String legajo);

    List<Empleado> findAll();
}