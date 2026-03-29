package com.ryan.fatura_service.model;

import com.ryan.fatura_service.enums.StatusDePagamento;
import com.ryan.fatura_service.enums.TipoTransacao;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString

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
