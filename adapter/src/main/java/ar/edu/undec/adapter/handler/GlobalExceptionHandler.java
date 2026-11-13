package ar.edu.undec.adapter.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(empleado.exception.EmpleadoYaExisteException.class)
    public ResponseEntity<String> handleEmpleadoYaExiste(empleado.exception.EmpleadoYaExisteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(empleado.exception.EmpleadoNoEncontradoException.class)
    public ResponseEntity<String> handleEmpleadoNoEncontrado(empleado.exception.EmpleadoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Captura cualquier violación de regla de negocio del dominio (Salario, Legajo, Fecha, etc.)
    @ExceptionHandler(empleado.exception.EmpleadoInvalidoException.class)
    public ResponseEntity<String> handleEmpleadoInvalido(empleado.exception.EmpleadoInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}