package ar.edu.undec.adapter.service.empleado.mapper;

import ar.edu.undec.adapter.service.empleado.model.EmpleadoResponseDTO;
import empleado.modelo.Empleado;

public class EmpleadoWebMapper {

    public static EmpleadoResponseDTO aResponseDTO(Empleado empleado) {
        if (empleado == null) return null;
        EmpleadoResponseDTO dto = new EmpleadoResponseDTO();
        dto.setId(empleado.getId());
        dto.setLegajo(empleado.getLegajo());
        dto.setNombreCompleto(empleado.getNombreCompleto());
        dto.setPuesto(empleado.getPuesto().name());
        dto.setSalario(empleado.getSalario());
        dto.setFechaIngreso(empleado.getFechaIngreso());
        return dto;
    }
}