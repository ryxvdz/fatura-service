package com.ryan.fatura_service.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class Conta {

    private String id;
    private String nomeCompleto;
    private String cpf;
    private String cartao;
    private BigDecimal saldoAtual;
    private BigDecimal limiteAtual;
}
