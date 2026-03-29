package com.ryan.fatura_service.dto;

import com.ryan.fatura_service.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoDTO (String id,
                            ContaDTO conta,
                            String statusDoPagamento,
                            TipoTransacao tipoTransacao,
                            BigDecimal valor,
                            String comerciante,
                            String localizacao,
                            LocalDateTime dataHota){
}
