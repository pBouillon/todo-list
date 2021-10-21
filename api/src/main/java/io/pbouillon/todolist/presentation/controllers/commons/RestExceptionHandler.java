package io.pbouillon.todolist.presentation.controllers.commons;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

/**
 * Rest exception handler to digest inner exceptions and filter them before sending them to the client
 */
@Log4j2
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle application custom exceptions on missing entities
     *
     * @return A formatted 404 error
     */
    @ResponseBody
    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<?> handleUnknownMemberException() {
        log.error("Unable to find the requested element");

        return ResponseEntity.notFound().build();
    }

}
