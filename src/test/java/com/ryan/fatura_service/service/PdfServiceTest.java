package com.ryan.fatura_service.service;

import com.ryan.fatura_service.enums.TipoTransacao;
import com.ryan.fatura_service.exceptions.GeracaoPdfException;
import com.ryan.fatura_service.exceptions.TransacaoNaoEncontradaException;
import com.ryan.fatura_service.model.Fatura;
import com.ryan.fatura_service.repository.FaturaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PdfServiceTest {

    @Mock
    private FaturaRepository faturaRepository;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private PdfService pdfService;

    @Test
    @DisplayName("Deve gerar Fatura em PDF com sucesso quando transação for CREDITO")
    void deveGerarFaturaComSucesso() {

        String contaId = "conta-123";
        Fatura f1 = new Fatura();
        f1.setValor(new BigDecimal("100.00"));
        Fatura f2 = new Fatura();
        f2.setValor(new BigDecimal("50.50"));

        List<Fatura> transacoes = List.of(f1, f2);

        when(faturaRepository.findByContaIdAndTipoTransacao(contaId, TipoTransacao.CREDITO)).thenReturn(transacoes);


        when(templateEngine.process(eq("fatura-template"), any(Context.class)))
                .thenReturn("<html><body>Teste</body></html>");


        byte[] pdfBytes = pdfService.gerarDocumentoPdf(contaId, TipoTransacao.CREDITO);


        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0, "O array de bytes do PDF não deveria ser vazio");


        ArgumentCaptor<Context> contextCaptor = ArgumentCaptor.forClass(Context.class);
        verify(templateEngine).process(eq("fatura-template"), contextCaptor.capture());

        Context contextCapturado = contextCaptor.getValue();
        assertEquals(transacoes, contextCapturado.getVariable("transacoes"));
        assertEquals(f1, contextCapturado.getVariable("cliente")); // O primeiro item da lista
        assertEquals(new BigDecimal("150.50"), contextCapturado.getVariable("total")); // A soma!
    }

    @Test
    @DisplayName("Deve gerar Extrato em PDF com sucesso quando transação for DEBITO")
    void deveGerarExtratoComSucesso() {

        String contaId = "conta-456";
        Fatura f1 = new Fatura();
        f1.setValor(new BigDecimal("30.00"));

        when(faturaRepository.findByContaIdAndTipoTransacao(contaId, TipoTransacao.DEBITO)).thenReturn(List.of(f1));
        when(templateEngine.process(eq("extrato-template"), any(Context.class)))
                .thenReturn("<html><body>Teste</body></html>");


        byte[] pdfBytes = pdfService.gerarDocumentoPdf(contaId, TipoTransacao.DEBITO);


        assertNotNull(pdfBytes);


        verify(templateEngine).process(eq("extrato-template"), any(Context.class));
    }

    @Test
    @DisplayName("Deve lançar TransacaoNaoEncontradaException quando não houver transações")
    void deveLancarExcecaoQuandoListaVazia() {

        String contaId = "conta-vazia";
        when(faturaRepository.findByContaIdAndTipoTransacao(contaId, TipoTransacao.CREDITO))
                .thenReturn(Collections.emptyList());


        TransacaoNaoEncontradaException exception = assertThrows(TransacaoNaoEncontradaException.class, () ->
                pdfService.gerarDocumentoPdf(contaId, TipoTransacao.CREDITO)
        );

        assertEquals("Nenhuma transação encontrada para gerar o documento.", exception.getMessage());


        verifyNoInteractions(templateEngine);
    }

    @Test
    @DisplayName("Deve lançar GeracaoPdfException quando o ITextRenderer falhar")
    void deveLancarGeracaoPdfExceptionNoCatch() {

        String contaId = "conta-erro";
        Fatura f1 = new Fatura();
        f1.setValor(BigDecimal.TEN);

        when(faturaRepository.findByContaIdAndTipoTransacao(contaId, TipoTransacao.CREDITO)).thenReturn(List.of(f1));


        when(templateEngine.process(eq("fatura-template"), any(Context.class))).thenReturn(null);


        GeracaoPdfException exception = assertThrows(GeracaoPdfException.class, () ->
                pdfService.gerarDocumentoPdf(contaId, TipoTransacao.CREDITO)
        );

        assertTrue(exception.getMessage().contains("Falha interna ao gerar o arquivo PDF:"));
    }
}