package ar.edu.undec.adapter.data.empleado.model;

import empleado.modelo.Puesto;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "empleados")
public class EmpleadoEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true, length = 7)
    private String legajo;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Puesto puesto;

    @Column(nullable = false)
    private Double salario;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    public EmpleadoEntity() {
    }

    public EmpleadoEntity(UUID id, String legajo, String nombreCompleto, Puesto puesto, Double salario, LocalDate fechaIngreso) {
        this.id = id;
        this.legajo = legajo;
        this.nombreCompleto = nombreCompleto;
        this.puesto = puesto;
        this.salario = salario;
        this.fechaIngreso = fechaIngreso;
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getLegajo() { return legajo; }
    public void setLegajo(String legajo) { this.legajo = legajo; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public Puesto getPuesto() { return puesto; }
    public void setPuesto(Puesto puesto) { this.puesto = puesto; }

    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
}