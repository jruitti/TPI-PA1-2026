package ar.edu.undec.adapter.service.empleado.beanconfig;

import ar.edu.undec.adapter.data.empleado.crud.EmpleadoCrudRepository;
import ar.edu.undec.adapter.data.empleado.mapper.EmpleadoMapper;
import ar.edu.undec.adapter.data.empleado.repoimplementacion.EmpleadoRepositoryImpl;
import empleado.input.BuscarEmpleadoPorLegajoInput;
import empleado.input.CrearEmpleadoInput;
import empleado.input.ListarEmpleadosInput;
import empleado.output.EmpleadoRepository;
import empleado.usecase.BuscarEmpleadoPorLegajoUseCase;
import empleado.usecase.CrearEmpleadoUseCase;
import empleado.usecase.ListarEmpleadosUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public EmpleadoMapper empleadoMapper() {
        return new EmpleadoMapper();
    }

    @Bean
    public EmpleadoRepository empleadoRepository(EmpleadoCrudRepository empleadoCrudRepository,
                                                 EmpleadoMapper empleadoMapper) {
        return new EmpleadoRepositoryImpl(empleadoCrudRepository, empleadoMapper);
    }

    @Bean
    public CrearEmpleadoInput crearEmpleadoInput(EmpleadoRepository empleadoRepository) {
        return new CrearEmpleadoUseCase(empleadoRepository);
    }

    @Bean
    public BuscarEmpleadoPorLegajoInput buscarEmpleadoPorLegajoInput(EmpleadoRepository empleadoRepository) {
        return new BuscarEmpleadoPorLegajoUseCase(empleadoRepository);
    }

    @Bean
    public ListarEmpleadosInput listarEmpleadosInput(EmpleadoRepository empleadoRepository) {
        return new ListarEmpleadosUseCase(empleadoRepository);
    }
}