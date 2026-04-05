package com.ryan.fatura_service.exceptions;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(TransacaoNaoEncontradaException.class)
    public ResponseEntity<ErroResponse> handleNaoEncontrado(TransacaoNaoEncontradaException ex) {
        ErroResponse erro = new ErroResponse(LocalDateTime.now(), 404, "Not Found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(GeracaoPdfException.class)
    public ResponseEntity<ErroResponse> handleErroPdf(GeracaoPdfException ex) {
        ErroResponse erro = new ErroResponse(LocalDateTime.now(), 500, "Internal Server Error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
