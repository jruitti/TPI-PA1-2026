package ar.edu.undec.adapter.data.empleado.repoimplementacion;

import ar.edu.undec.adapter.data.empleado.crud.EmpleadoCrudRepository;
import ar.edu.undec.adapter.data.empleado.mapper.EmpleadoMapper;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepository;

import java.util.List;
import java.util.Optional;

public class EmpleadoRepositoryImpl implements EmpleadoRepository {

    private final EmpleadoCrudRepository empleadoCrudRepository;
    private final EmpleadoMapper empleadoMapper;

    public EmpleadoRepositoryImpl(EmpleadoCrudRepository empleadoCrudRepository, EmpleadoMapper empleadoMapper) {
        this.empleadoCrudRepository = empleadoCrudRepository;
        this.empleadoMapper = empleadoMapper;
    }

    @Override
    public boolean existByLegajo(String legajo) {
        return empleadoCrudRepository.existsByLegajo(legajo);
    }

    @Override
    public boolean save(Empleado empleado) {
        empleadoCrudRepository.save(empleadoMapper.toEntity(empleado));
        return true;
    }

    @Override
    public Optional<Empleado> findByLegajo(String legajo) {
        return empleadoCrudRepository.findByLegajo(legajo)
                .map(empleadoMapper::toDomain);
    }

    @Override
    public List<Empleado> findAll() {
        return empleadoCrudRepository.findAll()
                .stream()
                .map(empleadoMapper::toDomain)
                .toList();
    }
}