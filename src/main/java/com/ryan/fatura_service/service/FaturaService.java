package com.ryan.fatura_service.service;

import com.ryan.fatura_service.dto.TransacaoDTO;
import com.ryan.fatura_service.model.Fatura;
import com.ryan.fatura_service.repository.FaturaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FaturaService {


    private final FaturaRepository faturaRepository;

    public Fatura processarFatura(TransacaoDTO dto){
        Fatura fatura = new Fatura();

        fatura.setContaId(dto.conta().id());
        fatura.setContaNome(dto.conta().nomeCompleto());
        fatura.setContaCartao(dto.conta().cartao());
        fatura.setContaCpf(dto.conta().cpf());
        fatura.setValor(dto.valor());
        fatura.setDataHota(dto.dataHota());
        fatura.setTipoTransacao(dto.tipoTransacao());
        fatura.setComerciante(dto.comerciante());
        return faturaRepository.save(fatura);
    }
}
