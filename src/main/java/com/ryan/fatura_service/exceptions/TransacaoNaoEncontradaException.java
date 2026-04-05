package com.ryan.fatura_service.exceptions;

public class TransacaoNaoEncontradaException extends RuntimeException {
    public TransacaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}