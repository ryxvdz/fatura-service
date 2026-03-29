package com.ryan.fatura_service.service;

import com.ryan.fatura_service.enums.TipoTransacao;
import com.ryan.fatura_service.model.Fatura;
import com.ryan.fatura_service.repository.FaturaRepository;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PdfService {

    private final FaturaRepository faturaRepository;
    private final TemplateEngine templateEngine;

    public PdfService(FaturaRepository faturaRepository, TemplateEngine templateEngine) {
        this.faturaRepository = faturaRepository;
        this.templateEngine = templateEngine;
    }

    public byte[] gerarDocumentoPdf(String contaId, TipoTransacao tipoTransacao) throws Exception {


        List<Fatura> transacoes = faturaRepository.findByContaIdAndTipoTransacao(contaId, tipoTransacao);

        if (transacoes.isEmpty()) {
            throw new RuntimeException("Nenhuma transação encontrada para gerar o documento.");
        }


        BigDecimal valorTotal = transacoes.stream()
                .map(Fatura::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        Fatura dadosCliente = transacoes.get(0);


        Context context = new Context();
        context.setVariable("transacoes", transacoes); // Agora estamos passando uma LISTA
        context.setVariable("cliente", dadosCliente);
        context.setVariable("total", valorTotal);


        String nomeDoTemplate = TipoTransacao.DEBITO.equals(tipoTransacao) ? "extrato-template" : "fatura-template";

        String html = templateEngine.process(nomeDoTemplate, context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        return outputStream.toByteArray();
    }
}