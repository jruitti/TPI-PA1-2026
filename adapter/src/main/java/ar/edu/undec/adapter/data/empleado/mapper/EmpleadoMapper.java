package ar.edu.undec.adapter.data.empleado.mapper;

import ar.edu.undec.adapter.data.empleado.model.EmpleadoEntity;
import empleado.modelo.Empleado;

public class EmpleadoMapper {

    public static EmpleadoEntity aEntity(Empleado empleadoDominio) {
        if (empleadoDominio == null) {
            return null;
        }
        return new EmpleadoEntity(
                empleadoDominio.getId(),
                empleadoDominio.getLegajo(),
                empleadoDominio.getNombreCompleto(),
                empleadoDominio.getPuesto(),
                empleadoDominio.getSalario(),
                empleadoDominio.getFechaIngreso()
        );
    }

    public static Empleado aDominio(EmpleadoEntity empleadoEntity) {
        if (empleadoEntity == null) {
            return null;
        }
        return new Empleado(
                empleadoEntity.getId(),
                empleadoEntity.getLegajo(),
                empleadoEntity.getNombreCompleto(),
                empleadoEntity.getPuesto(),
                empleadoEntity.getSalario(),
                empleadoEntity.getFechaIngreso()
        );
    }
}