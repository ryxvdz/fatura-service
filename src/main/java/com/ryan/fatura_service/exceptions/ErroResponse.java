package com.ryan.fatura_service.exceptions;

import java.time.LocalDateTime;

public record ErroResponse(LocalDateTime timestamp,
                           Integer status,
                           String erro,
                           String mensagem) {
}
