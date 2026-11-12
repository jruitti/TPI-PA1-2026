package ar.edu.undec.adapter.data.empleado.crud;

import ar.edu.undec.adapter.data.empleado.model.EmpleadoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmpleadoJpaRepository extends JpaRepository<EmpleadoJpaEntity, UUID> {

    boolean existsByLegajo(String legajo);

    Optional<EmpleadoJpaEntity> findByLegajo(String legajo);
}
