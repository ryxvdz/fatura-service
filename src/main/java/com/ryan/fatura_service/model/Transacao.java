package com.ryan.fatura_service.model;

import com.ryan.fatura_service.enums.StatusDePagamento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Transacao {

    private String id;

    private Conta conta;

    private StatusDePagamento statusDePagamento;

    private TipoTransacao tipoTransacao;

    private BigDecimal valor;

    private String comerciante;

    private String localizacao;

    private LocalDateTime dataHota;

    public Transacao(){
        this.dataHota = LocalDateTime.now();
    }
}
