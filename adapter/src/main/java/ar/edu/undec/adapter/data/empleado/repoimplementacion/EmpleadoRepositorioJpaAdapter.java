package ar.edu.undec.adapter.data.empleado.repoimplementacion;

import ar.edu.undec.adapter.data.empleado.crud.EmpleadoJpaRepository;
import ar.edu.undec.adapter.data.empleado.mapper.EmpleadoJpaMapper;
import ar.edu.undec.adapter.data.empleado.model.EmpleadoJpaEntity;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepositorio;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmpleadoRepositorioJpaAdapter implements EmpleadoRepositorio {

    private final EmpleadoJpaRepository empleadoJpaRepository;

    public EmpleadoRepositorioJpaAdapter(EmpleadoJpaRepository empleadoJpaRepository) {
        this.empleadoJpaRepository = empleadoJpaRepository;
    }

    @Override
    public boolean existePorLegajo(String legajo) {
        return empleadoJpaRepository.existsByLegajo(legajo);
    }

    @Override
    public Empleado guardar(Empleado empleado) {
        EmpleadoJpaEntity entidad = EmpleadoJpaMapper.toEntity(empleado);
        EmpleadoJpaEntity guardada = empleadoJpaRepository.save(entidad);
        return EmpleadoJpaMapper.toDomain(guardada);
    }

    @Override
    public Optional<Empleado> buscarPorLegajo(String legajo) {
        return empleadoJpaRepository.findByLegajo(legajo)
                .map(EmpleadoJpaMapper::toDomain);
    }

    @Override
    public List<Empleado> listarTodos() {
        return empleadoJpaRepository.findAll().stream()
                .map(EmpleadoJpaMapper::toDomain)
                .toList();
    }
}
