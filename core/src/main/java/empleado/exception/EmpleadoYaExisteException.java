package empleado.exception;

public class EmpleadoYaExisteException extends EmpleadoInvalidoException {
    public EmpleadoYaExisteException(String message) {
        super(message);
    }
}
