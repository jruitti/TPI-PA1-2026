package ar.edu.undec.adapter.data.empleado.repoimplementacion;

import ar.edu.undec.adapter.data.empleado.crud.SpringDataEmpleadoRepository;
import ar.edu.undec.adapter.data.empleado.mapper.EmpleadoMapper;
import ar.edu.undec.adapter.data.empleado.model.EmpleadoEntity;
import empleado.modelo.Empleado;
import empleado.output.EmpleadoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmpleadoRepositoryImpl implements EmpleadoRepository {

    private final SpringDataEmpleadoRepository springDataEmpleadoRepository;

    public EmpleadoRepositoryImpl(SpringDataEmpleadoRepository springDataEmpleadoRepository) {
        this.springDataEmpleadoRepository = springDataEmpleadoRepository;
    }

    @Override
    public boolean existePorLegajo(String legajo) {
        return springDataEmpleadoRepository.existsByLegajo(legajo);
    }

    @Override
    public Empleado guardar(Empleado empleado) {
        EmpleadoEntity entity = EmpleadoMapper.aEntity(empleado);
        EmpleadoEntity savedEntity = springDataEmpleadoRepository.saveAndFlush(entity);
        return EmpleadoMapper.aDominio(savedEntity);
    }

    @Override
    public Optional<Empleado> buscarPorLegajo(String legajo) {
        Optional<EmpleadoEntity> entityOpt = springDataEmpleadoRepository.findByLegajo(legajo);
        return entityOpt.map(EmpleadoMapper::aDominio);
    }

    @Override
    public List<Empleado> listarTodos() {
        List<EmpleadoEntity> entities = springDataEmpleadoRepository.findAll();
        return entities.stream()
                .map(EmpleadoMapper::aDominio)
                .collect(Collectors.toList());
    }
}