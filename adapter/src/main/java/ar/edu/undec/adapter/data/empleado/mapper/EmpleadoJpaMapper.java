package ar.edu.undec.adapter.data.empleado.mapper;

import ar.edu.undec.adapter.data.empleado.model.EmpleadoJpaEntity;
import empleado.modelo.Empleado;

public final class EmpleadoJpaMapper {

    private EmpleadoJpaMapper() {
    }

    public static EmpleadoJpaEntity toEntity(Empleado empleado) {
        return new EmpleadoJpaEntity(
                empleado.getId(),
                empleado.getNombreCompleto(),
                empleado.getLegajo(),
                empleado.getFechaIngreso(),
                empleado.getPuesto(),
                empleado.getSalario()
        );
    }

    public static Empleado toDomain(EmpleadoJpaEntity entity) {
        return Empleado.reconstruir(
                entity.getId(),
                entity.getNombreCompleto(),
                entity.getLegajo(),
                entity.getFechaIngreso(),
                entity.getPuesto(),
                entity.getSalario()
        );
    }
}
