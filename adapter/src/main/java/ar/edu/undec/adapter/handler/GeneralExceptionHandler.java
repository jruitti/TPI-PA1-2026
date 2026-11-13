package ar.edu.undec.adapter.handler;

import empleado.exception.EmpleadoNoEncontradoException;
import empleado.exception.FechaIngresoFuturaException;
import empleado.exception.FechaIngresoNulaException;
import empleado.exception.IdNuloException;
import empleado.exception.LegajoExistenteException;
import empleado.exception.LegajoInvalidoException;
import empleado.exception.LegajoNuloException;
import empleado.exception.NombreNuloException;
import empleado.exception.PuestoNuloException;
import empleado.exception.SalarioInvalidoException;
import empleado.exception.SalarioNuloException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    ResponseEntity<?> handleNoEncontrado(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(LegajoExistenteException.class)
    ResponseEntity<?> handleDuplicado(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler({
            IdNuloException.class,
            NombreNuloException.class,
            LegajoNuloException.class,
            LegajoInvalidoException.class,
            FechaIngresoNulaException.class,
            FechaIngresoFuturaException.class,
            PuestoNuloException.class,
            SalarioNuloException.class,
            SalarioInvalidoException.class
    })
    ResponseEntity<?> handleReglaDeNegocio(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}