package ar.edu.undec.adapter.service.empleado;

import ar.edu.undec.adapter.service.empleado.controller.EmpleadoController;
import ar.edu.undec.adapter.service.empleado.mapper.EmpleadoRestMapper;
import empleado.exception.EmpleadoNoEncontradoException;
import empleado.exception.LegajoExistenteException;
import empleado.exception.SalarioInvalidoException;
import empleado.input.BuscarEmpleadoPorLegajoInput;
import empleado.input.CrearEmpleadoInput;
import empleado.input.ListarEmpleadosInput;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmpleadoController.class)
@Import(EmpleadoRestMapper.class)
public class EmpleadoControllerTest {

    private static final UUID ID = UUID.fromString("cabbd417-1841-4b25-8798-e8d54df1416e");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrearEmpleadoInput crearEmpleadoInput;

    @MockBean
    private BuscarEmpleadoPorLegajoInput buscarEmpleadoPorLegajoInput;

    @MockBean
    private ListarEmpleadosInput listarEmpleadosInput;

    private Empleado empleadoDePrueba(String legajo) {
        return Empleado.crearEmpleado(ID, "Juan Perez", legajo, LocalDate.of(2024, 1, 15), Puesto.ANALISTA, new BigDecimal("500000"));
    }

    private String empleadoJson() {
        return """
                {
                  "nombreCompleto": "Juan Perez",
                  "legajo": "ABC1234",
                  "fechaIngreso": "2024-01-15",
                  "puesto": "ANALISTA",
                  "salario": 500000
                }
                """;
    }

    @Test
    void postEmpleadoConDatosValidosRetorna201YElEmpleadoCreado() throws Exception {
        when(crearEmpleadoInput.ejecutar(any(), any(), any(), any(), any())).thenReturn(empleadoDePrueba("ABC1234"));

        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empleadoJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ID.toString()))
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Perez"))
                .andExpect(jsonPath("$.legajo").value("ABC1234"))
                .andExpect(jsonPath("$.puesto").value("ANALISTA"))
                .andExpect(jsonPath("$.salario").value(500000));
    }

    @Test
    void postEmpleadoConLegajoDuplicadoRetorna409() throws Exception {
        when(crearEmpleadoInput.ejecutar(any(), any(), any(), any(), any()))
                .thenThrow(new LegajoExistenteException("Ya existe un empleado con el legajo ABC1234"));

        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empleadoJson()))
                .andExpect(status().isConflict())
                .andExpect(content().string("Ya existe un empleado con el legajo ABC1234"));
    }

    @Test
    void postEmpleadoConSalarioInvalidoRetorna400() throws Exception {
        when(crearEmpleadoInput.ejecutar(any(), any(), any(), any(), any()))
                .thenThrow(new SalarioInvalidoException("El salario no puede ser inferior al piso salarial de ANALISTA"));

        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(empleadoJson()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El salario no puede ser inferior al piso salarial de ANALISTA"));
    }

    @Test
    void getEmpleadoPorLegajoExistenteRetorna200ConLosDatos() throws Exception {
        when(buscarEmpleadoPorLegajoInput.ejecutar("ABC1234")).thenReturn(empleadoDePrueba("ABC1234"));

        mockMvc.perform(get("/empleados/ABC1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.legajo").value("ABC1234"))
                .andExpect(jsonPath("$.nombreCompleto").value("Juan Perez"));
    }

    @Test
    void getEmpleadoPorLegajoInexistenteRetorna404() throws Exception {
        when(buscarEmpleadoPorLegajoInput.ejecutar("ZZZ9999"))
                .thenThrow(new EmpleadoNoEncontradoException("No existe un empleado con el legajo ZZZ9999"));

        mockMvc.perform(get("/empleados/ZZZ9999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No existe un empleado con el legajo ZZZ9999"));
    }

    @Test
    void getEmpleadosRetornaElListadoCompleto() throws Exception {
        when(listarEmpleadosInput.ejecutar())
                .thenReturn(List.of(empleadoDePrueba("ABC1234"), empleadoDePrueba("DEF5678")));

        mockMvc.perform(get("/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].legajo").value("ABC1234"))
                .andExpect(jsonPath("$[1].legajo").value("DEF5678"));
    }
}
