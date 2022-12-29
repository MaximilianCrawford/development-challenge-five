package com.medcloud.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<Object> handleModelNotFoundException(ModelNotFoundException ex) {

        List<String> mensagens = new ArrayList<>();
        mensagens.add(ex.getMessage());

        ApiException erro = new ApiException(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST,
            "Recurso não encontrado",
            mensagens
        );

        return ResponseEntityBuilder.build(erro);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {

        List<String> mensagens = new ArrayList<>();
        mensagens.add(ex.getMessage());

        ApiException erro = new ApiException(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST,
            "Erro de tipo.",
            mensagens
        );

        return ResponseEntityBuilder.build(erro);
    }

}
