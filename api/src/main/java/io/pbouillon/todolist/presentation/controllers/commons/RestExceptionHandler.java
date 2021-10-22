package io.pbouillon.todolist.presentation.controllers.commons;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Rest exception handler to digest inner exceptions and filter them before sending them to the client
 */
@Log4j2
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Handle application custom exceptions on missing entities
     * @return A formatted HTTP 404 error
     */
    @ResponseBody
    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElement() {
        log.error("Unable to find the requested element");

        return ResponseEntity.notFound().build();
    }

    /**
     * Handle Hibernate validation exceptions
     * @param exception Hibernate validation exception
     * @return A formatted HTTP 400 error
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();

                    errors.put(fieldName, errorMessage);
                });

        // noinspection ConstantConditions
        log.error(
                "Intercepted validation errors for {} with messages : {}",
                exception.getBindingResult().getTarget().getClass(),
                errors);

        return ResponseEntity.badRequest()
                .body(errors);
    }

}
