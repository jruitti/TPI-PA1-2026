package ar.edu.undec.adapter.handler;

import empleado.exception.AtributoEmpleadoObligatorioException;
import empleado.exception.EmpleadoNoEncontradoException;
import empleado.exception.FechaIngresoInvalidaException;
import empleado.exception.LegajoDuplicadoException;
import empleado.exception.LegajoInvalidoException;
import empleado.exception.NombreEmpleadoInvalidoException;
import empleado.exception.SalarioEmpleadoInvalidoException;
import empleado.exception.SalarioMinimoNoAlcanzadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarEmpleadoNoEncontrado(
            EmpleadoNoEncontradoException excepcion) {
        return responder(HttpStatus.NOT_FOUND, excepcion.getMessage());
    }

    @ExceptionHandler(LegajoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> manejarLegajoDuplicado(
            LegajoDuplicadoException excepcion) {
        return responder(HttpStatus.CONFLICT, excepcion.getMessage());
    }

    @ExceptionHandler({
            AtributoEmpleadoObligatorioException.class,
            FechaIngresoInvalidaException.class,
            LegajoInvalidoException.class,
            NombreEmpleadoInvalidoException.class,
            SalarioEmpleadoInvalidoException.class,
            SalarioMinimoNoAlcanzadoException.class
    })
    public ResponseEntity<ErrorResponse> manejarReglaDeDominio(RuntimeException excepcion) {
        return responder(HttpStatus.BAD_REQUEST, excepcion.getMessage());
    }

    private ResponseEntity<ErrorResponse> responder(HttpStatus estado, String mensaje) {
        return ResponseEntity.status(estado).body(new ErrorResponse(mensaje));
    }

    public record ErrorResponse(String mensaje) {
    }
}
