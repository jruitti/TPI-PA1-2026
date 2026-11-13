package empleado.exception;

public class SalarioInsuficienteException extends EmpleadoInvalidoException {
    public SalarioInsuficienteException(String mensaje) {
        super(mensaje);
    }
}