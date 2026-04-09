package com.ryan.fatura_service.service;

import com.ryan.fatura_service.dto.TransacaoDTO;
import com.ryan.fatura_service.enums.TipoTransacao;
import com.ryan.fatura_service.model.Fatura;
import com.ryan.fatura_service.repository.FaturaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FaturaServiceTest {

    @Mock
    private FaturaRepository faturaRepository;

    @InjectMocks
    private FaturaService faturaService;


    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private TransacaoDTO transacaoDTO;

    @Test
    @DisplayName("Deve mapear os dados da transação corretamente e salvar a fatura no banco")
    void deveProcessarFaturaComSucesso() {

        String idConta = "conta-123";
        String nomeCompleto = "Ryan Dias";
        String cartao = "1234-5678-9012-3456";
        String cpf = "123.456.789-00";
        BigDecimal valor = new BigDecimal("250.75");
        LocalDateTime dataHora = LocalDateTime.now();
        TipoTransacao tipoTransacao = TipoTransacao.CREDITO;
        String comerciante = "Amazon";


        when(transacaoDTO.conta().id()).thenReturn(idConta);
        when(transacaoDTO.conta().nomeCompleto()).thenReturn(nomeCompleto);
        when(transacaoDTO.conta().cartao()).thenReturn(cartao);
        when(transacaoDTO.conta().cpf()).thenReturn(cpf);


        when(transacaoDTO.valor()).thenReturn(valor);
        when(transacaoDTO.dataHota()).thenReturn(dataHora);
        when(transacaoDTO.tipoTransacao()).thenReturn(TipoTransacao.valueOf(String.valueOf(tipoTransacao)));
        when(transacaoDTO.comerciante()).thenReturn(comerciante);


        Fatura faturaSimuladaBanco = new Fatura();
        faturaSimuladaBanco.setContaId(idConta);
        faturaSimuladaBanco.setValor(valor);
        when(faturaRepository.save(any(Fatura.class))).thenAnswer(invocation -> {
            return invocation.getArgument(0);
        });


        Fatura resultado = faturaService.processarFatura(transacaoDTO);


        assertNotNull(resultado, "A fatura retornada não deveria ser nula");


        assertEquals(idConta, resultado.getContaId());
        assertEquals(nomeCompleto, resultado.getContaNome());
        assertEquals(cartao, resultado.getContaCartao());
        assertEquals(cpf, resultado.getContaCpf());
        assertEquals(valor, resultado.getValor());
        assertEquals(dataHora, resultado.getDataHota());
        assertEquals(tipoTransacao, resultado.getTipoTransacao());
        assertEquals(comerciante, resultado.getComerciante());


        verify(faturaRepository, times(1)).save(any(Fatura.class));
    }
}