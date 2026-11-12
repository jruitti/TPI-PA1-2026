package ar.edu.undec.adapter.service.empleado.controller;

import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.usecase.BuscarEmpleadoPorLegajoUseCase;
import empleado.usecase.CrearEmpleadoUseCase;
import empleado.usecase.ListarEmpleadosUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final CrearEmpleadoUseCase crearEmpleadoUseCase;
    private final BuscarEmpleadoPorLegajoUseCase buscarEmpleadoPorLegajoUseCase;
    private final ListarEmpleadosUseCase listarEmpleadosUseCase;

    public EmpleadoController(CrearEmpleadoUseCase crearEmpleadoUseCase,
                              BuscarEmpleadoPorLegajoUseCase buscarEmpleadoPorLegajoUseCase,
                              ListarEmpleadosUseCase listarEmpleadosUseCase) {
        this.crearEmpleadoUseCase = crearEmpleadoUseCase;
        this.buscarEmpleadoPorLegajoUseCase = buscarEmpleadoPorLegajoUseCase;
        this.listarEmpleadosUseCase = listarEmpleadosUseCase;
    }

    @PostMapping
    public ResponseEntity<Empleado> crear(@RequestBody CrearEmpleadoRequest request) {
        Empleado empleado = crearEmpleadoUseCase.ejecutar(
                request.nombreCompleto(),
                request.legajo(),
                request.fechaIngreso(),
                request.puesto(),
                request.salario()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(empleado);
    }

    @GetMapping("/{legajo}")
    public ResponseEntity<Empleado> buscarPorLegajo(@PathVariable("legajo") String legajo) {
        return ResponseEntity.ok(buscarEmpleadoPorLegajoUseCase.ejecutar(legajo));
    }

    @GetMapping
    public ResponseEntity<List<Empleado>> listar() {
        return ResponseEntity.ok(listarEmpleadosUseCase.ejecutar());
    }

    public record CrearEmpleadoRequest(String nombreCompleto,
                                       String legajo,
                                       LocalDate fechaIngreso,
                                       Puesto puesto,
                                       BigDecimal salario) {
    }
}
