package ar.edu.undec.adapter.service.empleado.controller;

import empleado.exception.EmpleadoNoEncontradoException;
import empleado.exception.LegajoDuplicadoException;
import empleado.exception.LegajoInvalidoException;
import empleado.modelo.Empleado;
import empleado.modelo.Puesto;
import empleado.usecase.BuscarEmpleadoPorLegajoUseCase;
import empleado.usecase.CrearEmpleadoUseCase;
import empleado.usecase.ListarEmpleadosUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmpleadoController.class)
class EmpleadoControllerTest {

    private static final String NOMBRE = "Ana Pérez";
    private static final String LEGAJO = "ABC1234";
    private static final LocalDate FECHA_INGRESO = LocalDate.of(2020, 5, 10);
    private static final Puesto PUESTO = Puesto.ANALISTA;
    private static final BigDecimal SALARIO = new BigDecimal("500000");
    private static final String JSON_EMPLEADO = """
            {
              "nombreCompleto": "Ana Pérez",
              "legajo": "ABC1234",
              "fechaIngreso": "2020-05-10",
              "puesto": "ANALISTA",
              "salario": 500000
            }
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrearEmpleadoUseCase crearEmpleadoUseCase;

    @MockBean
    private BuscarEmpleadoPorLegajoUseCase buscarEmpleadoPorLegajoUseCase;

    @MockBean
    private ListarEmpleadosUseCase listarEmpleadosUseCase;

    @Test
    void postEmpleadosCreaEmpleadoYResponde201() throws Exception {
        Empleado empleado = empleadoValido();
        when(crearEmpleadoUseCase.ejecutar(
                eq(NOMBRE), eq(LEGAJO), eq(FECHA_INGRESO), eq(PUESTO), eq(SALARIO)))
                .thenReturn(empleado);

        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_EMPLEADO))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.nombreCompleto").value(NOMBRE))
                .andExpect(jsonPath("$.legajo").value(LEGAJO))
                .andExpect(jsonPath("$.puesto").value(PUESTO.name()))
                .andExpect(jsonPath("$.salario").value(500000));
    }

    @Test
    void getEmpleadoPorLegajoResponde200CuandoExiste() throws Exception {
        when(buscarEmpleadoPorLegajoUseCase.ejecutar(LEGAJO)).thenReturn(empleadoValido());

        mockMvc.perform(get("/empleados/{legajo}", LEGAJO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.legajo").value(LEGAJO))
                .andExpect(jsonPath("$.nombreCompleto").value(NOMBRE));
    }

    @Test
    void getEmpleadosRespondeLaListaRegistrada() throws Exception {
        when(listarEmpleadosUseCase.ejecutar()).thenReturn(List.of(empleadoValido()));

        mockMvc.perform(get("/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].legajo").value(LEGAJO))
                .andExpect(jsonPath("$[0].nombreCompleto").value(NOMBRE));
    }

    @Test
    void getEmpleadosRespondeListaVaciaCuandoNoHayEmpleados() throws Exception {
        when(listarEmpleadosUseCase.ejecutar()).thenReturn(List.of());

        mockMvc.perform(get("/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void postEmpleadosResponde409CuandoElLegajoEstaDuplicado() throws Exception {
        when(crearEmpleadoUseCase.ejecutar(
                eq(NOMBRE), eq(LEGAJO), eq(FECHA_INGRESO), eq(PUESTO), eq(SALARIO)))
                .thenThrow(new LegajoDuplicadoException("Legajo duplicado"));

        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_EMPLEADO))
                .andExpect(status().isConflict());
    }

    @Test
    void getEmpleadoPorLegajoResponde404CuandoNoExiste() throws Exception {
        when(buscarEmpleadoPorLegajoUseCase.ejecutar(LEGAJO))
                .thenThrow(new EmpleadoNoEncontradoException("Empleado no encontrado"));

        mockMvc.perform(get("/empleados/{legajo}", LEGAJO))
                .andExpect(status().isNotFound());
    }

    @Test
    void responde400CuandoSeViolaUnaReglaDeDominio() throws Exception {
        when(crearEmpleadoUseCase.ejecutar(
                eq(NOMBRE), eq(LEGAJO), eq(FECHA_INGRESO), eq(PUESTO), eq(SALARIO)))
                .thenThrow(new LegajoInvalidoException("Formato de legajo inválido"));

        mockMvc.perform(post("/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_EMPLEADO))
                .andExpect(status().isBadRequest());
    }

    private Empleado empleadoValido() {
        return Empleado.crear(NOMBRE, LEGAJO, FECHA_INGRESO, PUESTO, SALARIO);
    }
}
