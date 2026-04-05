package com.ryan.fatura_service.controller;

import com.ryan.fatura_service.dto.TransacaoDTO;
import com.ryan.fatura_service.enums.TipoTransacao;
import com.ryan.fatura_service.model.Fatura;
import com.ryan.fatura_service.service.FaturaService;
import com.ryan.fatura_service.service.PdfService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor

@RestController
@RequestMapping("/api/faturas")
public class FaturaController {

    private final FaturaService faturaService;
    private final PdfService pdfService;


    @PostMapping
    public ResponseEntity<Fatura> receberTransacao(@RequestBody TransacaoDTO transacaoDTO) {
        Fatura faturaSalva = faturaService.processarFatura(transacaoDTO);
        log.info("salvando fatura");
        return ResponseEntity.status(HttpStatus.CREATED).body(faturaSalva);
    }


    @GetMapping("/conta/{contaId}/pdf")
    public ResponseEntity<byte[]> baixarPdf(
            @PathVariable String contaId,
            @RequestParam TipoTransacao tipo) {
        byte[] pdfBytes = pdfService.gerarDocumentoPdf(contaId, tipo);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"documento-" + contaId + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
