package ar.edu.undec.adapter.service.empleado.controller;

import ar.edu.undec.adapter.service.empleado.model.EmpleadoRequestDTO;
import ar.edu.undec.adapter.service.empleado.model.EmpleadoResponseDTO;
import ar.edu.undec.adapter.service.empleado.mapper.EmpleadoWebMapper;
import empleado.input.CrearEmpleadoInput;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.usecase.BuscarEmpleadoPorLegajoUseCase;
import empleado.usecase.ListarEmpleadosUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private final CrearEmpleadoInput crearEmpleadoInput;
    private final BuscarEmpleadoPorLegajoUseCase buscarEmpleadoPorLegajoUseCase;
    private final ListarEmpleadosUseCase listarEmpleadosUseCase;

    public EmpleadoController(CrearEmpleadoInput crearEmpleadoInput,
                              BuscarEmpleadoPorLegajoUseCase buscarEmpleadoPorLegajoUseCase,
                              ListarEmpleadosUseCase listarEmpleadosUseCase) {
        this.crearEmpleadoInput = crearEmpleadoInput;
        this.buscarEmpleadoPorLegajoUseCase = buscarEmpleadoPorLegajoUseCase;
        this.listarEmpleadosUseCase = listarEmpleadosUseCase;
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponseDTO> crearEmpleado(@RequestBody EmpleadoRequestDTO requestDTO) {
        Empleado empleadoDominio = new Empleado(
                null,
                requestDTO.getLegajo(),
                requestDTO.getNombreCompleto(),
                Puesto.valueOf(requestDTO.getPuesto()),
                requestDTO.getSalario(),
                requestDTO.getFechaIngreso()
        );

        Empleado empleadoCreado = crearEmpleadoInput.crear(empleadoDominio);
        EmpleadoResponseDTO responseDTO = EmpleadoWebMapper.aResponseDTO(empleadoCreado);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{legajo}")
    public ResponseEntity<EmpleadoResponseDTO> buscarPorLegajo(@PathVariable("legajo") String legajo) {
        Empleado empleado = buscarEmpleadoPorLegajoUseCase.ejecutar(legajo);
        return ResponseEntity.ok(EmpleadoWebMapper.aResponseDTO(empleado));
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoResponseDTO>> listarEmpleados() {
        List<Empleado> empleados = listarEmpleadosUseCase.ejecutar();
        List<EmpleadoResponseDTO> responseDTOs = empleados.stream()
                .map(EmpleadoWebMapper::aResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
}