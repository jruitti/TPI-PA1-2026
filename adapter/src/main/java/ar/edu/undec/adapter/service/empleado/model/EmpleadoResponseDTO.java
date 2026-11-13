package ar.edu.undec.adapter.service.empleado.model;

import java.time.LocalDate;
import java.util.UUID;

public class EmpleadoResponseDTO {
    private UUID id;
    private String legajo;
    private String nombreCompleto;
    private String puesto;
    private Double salario;
    private LocalDate fechaIngreso;

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getLegajo() { return legajo; }
    public void setLegajo(String legajo) { this.legajo = legajo; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }

    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
}