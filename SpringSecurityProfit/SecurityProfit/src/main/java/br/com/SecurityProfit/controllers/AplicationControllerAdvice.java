package br.com.SecurityProfit.controllers;

import br.com.SecurityProfit.exceptions.ApiErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;

@RestControllerAdvice
public class AplicationControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorMessage handleException(Exception ex) {
        return new ApiErrorMessage(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorMessage handleMethodNotValidException(MethodArgumentNotValidException ex) {
        ArrayList<String> listaErros = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            listaErros.add(error.getField() + ": " + error.getDefaultMessage());
        }
        return new ApiErrorMessage(listaErros);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorMessage> handleAccessDeniedException(AccessDeniedException ex) {
        ApiErrorMessage errorResponse = new ApiErrorMessage(Arrays.asList("Acesso negado"));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorMessage> handleResponseStatusException(ResponseStatusException ex) {
        ApiErrorMessage errorResponse = new ApiErrorMessage(Arrays.asList(ex.getReason()));
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}
