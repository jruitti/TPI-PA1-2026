package ar.edu.undec.adapter.service.empleado.controller;

import ar.edu.undec.adapter.service.empleado.mapper.EmpleadoRestMapper;
import ar.edu.undec.adapter.service.empleado.model.EmpleadoRequest;
import ar.edu.undec.adapter.service.empleado.model.EmpleadoResponse;
import empleado.input.BuscarEmpleadoPorLegajoInput;
import empleado.input.CrearEmpleadoInput;
import empleado.input.ListarEmpleadosInput;
import empleado.modelo.Empleado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final CrearEmpleadoInput crearEmpleadoInput;
    private final BuscarEmpleadoPorLegajoInput buscarEmpleadoPorLegajoInput;
    private final ListarEmpleadosInput listarEmpleadosInput;
    private final EmpleadoRestMapper empleadoRestMapper;

    public EmpleadoController(CrearEmpleadoInput crearEmpleadoInput,
                              BuscarEmpleadoPorLegajoInput buscarEmpleadoPorLegajoInput,
                              ListarEmpleadosInput listarEmpleadosInput,
                              EmpleadoRestMapper empleadoRestMapper) {
        this.crearEmpleadoInput = crearEmpleadoInput;
        this.buscarEmpleadoPorLegajoInput = buscarEmpleadoPorLegajoInput;
        this.listarEmpleadosInput = listarEmpleadosInput;
        this.empleadoRestMapper = empleadoRestMapper;
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> crear(@RequestBody EmpleadoRequest request) {
        Empleado empleado = crearEmpleadoInput.ejecutar(
                request.getNombreCompleto(),
                request.getLegajo(),
                request.getFechaIngreso(),
                request.getPuesto(),
                request.getSalario());

        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoRestMapper.toResponse(empleado));
    }

    @GetMapping("/{legajo}")
    public ResponseEntity<EmpleadoResponse> buscarPorLegajo(@PathVariable("legajo") String legajo) {
        Empleado empleado = buscarEmpleadoPorLegajoInput.ejecutar(legajo);
        return ResponseEntity.ok(empleadoRestMapper.toResponse(empleado));
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoResponse>> listar() {
        List<EmpleadoResponse> empleados = listarEmpleadosInput.ejecutar()
                .stream()
                .map(empleadoRestMapper::toResponse)
                .toList();

        return ResponseEntity.ok(empleados);
    }
}