package com.ryan.fatura_service.dto;

import java.math.BigDecimal;

public record ContaDTO(String id,
                       String nomeCompleto,
                       String cpf,
                       String cartao,
                       BigDecimal saldoAtual,
                       BigDecimal limiteAtual) {
}
