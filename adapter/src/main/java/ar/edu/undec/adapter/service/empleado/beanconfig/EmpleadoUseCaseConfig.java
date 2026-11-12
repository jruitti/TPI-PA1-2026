package ar.edu.undec.adapter.service.empleado.beanconfig;

import empleado.output.EmpleadoRepositorio;
import empleado.usecase.BuscarEmpleadoPorLegajoUseCase;
import empleado.usecase.CrearEmpleadoUseCase;
import empleado.usecase.ListarEmpleadosUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmpleadoUseCaseConfig {

    @Bean
    public CrearEmpleadoUseCase crearEmpleadoUseCase(EmpleadoRepositorio empleadoRepositorio) {
        return new CrearEmpleadoUseCase(empleadoRepositorio);
    }

    @Bean
    public BuscarEmpleadoPorLegajoUseCase buscarEmpleadoPorLegajoUseCase(
            EmpleadoRepositorio empleadoRepositorio) {
        return new BuscarEmpleadoPorLegajoUseCase(empleadoRepositorio);
    }

    @Bean
    public ListarEmpleadosUseCase listarEmpleadosUseCase(EmpleadoRepositorio empleadoRepositorio) {
        return new ListarEmpleadosUseCase(empleadoRepositorio);
    }
}
