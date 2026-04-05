package com.ryan.fatura_service.exceptions;

public class GeracaoPdfException extends RuntimeException {
    public GeracaoPdfException(String mensagem) {
        super(mensagem);
    }
}
