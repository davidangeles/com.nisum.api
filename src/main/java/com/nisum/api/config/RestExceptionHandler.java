package com.nisum.api.config;

import com.nisum.api.exception.NotFoundException;
import com.nisum.api.exception.UnprocessableEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnprocessableEntity.class)
    public ResponseEntity<Object> handleUnprocessableEntity(UnprocessableEntity ex) {
        return new ResponseEntity<>(this.getDefaultError(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(this.getDefaultError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add("Campo " + error.getField() + " : " + error.getDefaultMessage() + " - Valor: " + error.getRejectedValue());
        }

        for (ObjectError error : e.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        if (!errors.isEmpty()) {
            String message = String.join(" | ", errors);
            return new ResponseEntity<>(this.getDefaultError(message), HttpStatus.BAD_REQUEST);
        }

        return this.handleExceptionInternal(e, null, headers, status, request);
    }

    private Map<String, Object> getDefaultError(String message) {
        Map<String, Object> values = new LinkedHashMap<>();
        values.put("mensaje", message);

        return values;
    }
}
