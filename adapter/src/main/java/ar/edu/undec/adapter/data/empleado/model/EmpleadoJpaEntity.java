package ar.edu.undec.adapter.data.empleado.model;

import empleado.modelo.Puesto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "empleados")
public class EmpleadoJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(nullable = false, unique = true, length = 7)
    private String legajo;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Puesto puesto;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal salario;

    protected EmpleadoJpaEntity() {
    }

    public EmpleadoJpaEntity(UUID id, String nombreCompleto, String legajo,
                             LocalDate fechaIngreso, Puesto puesto, BigDecimal salario) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.legajo = legajo;
        this.fechaIngreso = fechaIngreso;
        this.puesto = puesto;
        this.salario = salario;
    }

    public UUID getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getLegajo() {
        return legajo;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
}
