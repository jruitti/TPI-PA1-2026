package empleado.exception;

public class EmpleadoNoEncontradoException extends EmpleadoInvalidoException {
    public EmpleadoNoEncontradoException(String message) {
        super(message);
    }
}