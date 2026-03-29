package com.ryan.fatura_service.model;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Conta {

    private String id;
    private String nomeCompleto;
    private String cpf;
    private String cartao;
    private BigDecimal saldoAtual;
    private BigDecimal limiteAtual;
}
