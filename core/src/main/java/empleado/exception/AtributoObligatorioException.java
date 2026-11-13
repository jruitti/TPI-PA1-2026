package empleado.exception;

public class AtributoObligatorioException extends EmpleadoInvalidoException {
    public AtributoObligatorioException(String mensaje) {
        super(mensaje);
    }
}