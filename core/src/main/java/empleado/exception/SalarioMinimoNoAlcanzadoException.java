package empleado.exception;

public class SalarioMinimoNoAlcanzadoException extends RuntimeException {
    public SalarioMinimoNoAlcanzadoException(String message) {
        super(message);
    }
}
