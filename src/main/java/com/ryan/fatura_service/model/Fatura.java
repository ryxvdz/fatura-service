package com.ryan.fatura_service.model;

import com.ryan.fatura_service.enums.TipoTransacao;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Document("Fatura")
public class Fatura {

    @Id
    private String id;

    private String contaId;

    private String contaNome;

    private String contaCpf;

    private String contaCartao;

    private BigDecimal valor;

    private LocalDateTime dataHota;

    private TipoTransacao tipoTransacao;

    private String comerciante;
}
