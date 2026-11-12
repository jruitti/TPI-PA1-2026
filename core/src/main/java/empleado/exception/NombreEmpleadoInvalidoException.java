package empleado.exception;

public class NombreEmpleadoInvalidoException extends RuntimeException {
    public NombreEmpleadoInvalidoException(String message) {
        super(message);
    }
}
