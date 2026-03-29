package com.ryan.fatura_service.repository;

import com.ryan.fatura_service.enums.TipoTransacao;
import com.ryan.fatura_service.model.Fatura;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FaturaRepository extends MongoRepository<Fatura ,String > {
    List<Fatura> findByContaIdAndTipoTransacao(String contaId, TipoTransacao tipoTransacao);
}
