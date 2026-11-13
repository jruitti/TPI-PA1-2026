package empleado.exception;

// Excepción base para cualquier error de validación de empleados
public class EmpleadoInvalidoException extends RuntimeException {
    public EmpleadoInvalidoException(String mensaje) {
        super(mensaje);
    }
}