package ar.edu.undec.adapter.service.empleado.controller;

import ar.edu.undec.adapter.service.empleado.model.EmpleadoRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import empleado.input.CrearEmpleadoInput;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.usecase.BuscarEmpleadoPorLegajoUseCase;
import empleado.usecase.ListarEmpleadosUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmpleadoController.class)
class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CrearEmpleadoInput crearEmpleadoInput;

    @MockBean
    private BuscarEmpleadoPorLegajoUseCase buscarEmpleadoPorLegajoUseCase;

    @MockBean
    private ListarEmpleadosUseCase listarEmpleadosUseCase;

    @Test
    void crearEmpleado_conDatosValidos_deberiaRetornarCreatedYEmpleadoDTO() throws Exception {
        UUID id = UUID.randomUUID();
        Empleado empleadoCreado = new Empleado(id, "ABC1234", "Juan Perez", Puesto.ANALISTA, 600000.0, LocalDate.now());

        when(crearEmpleadoInput.crear(any())).thenReturn(empleadoCreado);

        EmpleadoRequestDTO requestDTO = new EmpleadoRequestDTO();
        requestDTO.setLegajo("ABC1234");
        requestDTO.setNombreCompleto("Juan Perez");
        requestDTO.setPuesto("ANALISTA");
        requestDTO.setSalario(600000.0);
        requestDTO.setFechaIngreso(LocalDate.now());

        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.legajo").value("ABC1234"))
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Perez"));
    }

    @Test
    void crearEmpleado_cuandoEmpleadoYaExiste_deberiaRetornarConflict() throws Exception {
        when(crearEmpleadoInput.crear(any())).thenThrow(new empleado.exception.EmpleadoYaExisteException("El empleado ya existe"));

        EmpleadoRequestDTO requestDTO = new EmpleadoRequestDTO();
        requestDTO.setLegajo("ABC1234");
        requestDTO.setNombreCompleto("Juan Perez");
        requestDTO.setPuesto("ANALISTA");
        requestDTO.setSalario(600000.0);
        requestDTO.setFechaIngreso(LocalDate.now());

        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void buscarEmpleadoPorLegajo_cuandoExiste_deberiaRetornarOkYEmpleado() throws Exception {
        UUID id = UUID.randomUUID();
        Empleado empleado = new Empleado(id, "ABC1234", "Juan Perez", Puesto.ANALISTA, 600000.0, LocalDate.now());

        when(buscarEmpleadoPorLegajoUseCase.ejecutar("ABC1234")).thenReturn(empleado);

        mockMvc.perform(get("/empleados/ABC1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.legajo").value("ABC1234"))
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Perez"));
    }

    @Test
    void buscarEmpleadoPorLegajo_cuandoNoExiste_deberiaRetornarNotFound() throws Exception {
        when(buscarEmpleadoPorLegajoUseCase.ejecutar("XYZ9999"))
                .thenThrow(new empleado.exception.EmpleadoNoEncontradoException("No encontrado"));

        mockMvc.perform(get("/empleados/XYZ9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void listarEmpleados_deberiaRetornarListaYOk() throws Exception {
        Empleado empleado1 = new Empleado(UUID.randomUUID(), "ABC1234", "Juan Perez", Puesto.ANALISTA, 600000.0, LocalDate.now());
        Empleado empleado2 = new Empleado(UUID.randomUUID(), "XYZ5678", "Ana Gomez", Puesto.SUPERVISOR, 900000.0, LocalDate.now());

        when(listarEmpleadosUseCase.ejecutar()).thenReturn(List.of(empleado1, empleado2));

        mockMvc.perform(get("/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].legajo").value("ABC1234"))
                .andExpect(jsonPath("$[1].legajo").value("XYZ5678"));
    }

    @Test
    void crearEmpleado_cuandoViolacionReglasNegocio_deberiaRetornarBadRequest() throws Exception {
        when(crearEmpleadoInput.crear(any())).thenThrow(new empleado.exception.SalarioInsuficienteException("Salario insuficiente"));

        EmpleadoRequestDTO requestDTO = new EmpleadoRequestDTO();
        requestDTO.setLegajo("ABC1234");
        requestDTO.setNombreCompleto("Juan Perez");
        requestDTO.setPuesto("ANALISTA");
        requestDTO.setSalario(100000.0); // Salario inválido menor al piso
        requestDTO.setFechaIngreso(LocalDate.now());

        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest()); // Esperamos un 400
    }
}