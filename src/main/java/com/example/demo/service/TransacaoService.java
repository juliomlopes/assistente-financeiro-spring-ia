package com.example.demo.service;

import com.example.demo.model.TipoTransacao;
import com.example.demo.model.Transacao;
import com.example.demo.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransacaoService {

    private final TransacaoRepository repository;

    public TransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Transacao salvar(String descricao, BigDecimal valor, String tipoStr, String categoria) {
        TipoTransacao tipo;
        try {
            tipo = TipoTransacao.valueOf(tipoStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            tipo = TipoTransacao.DESPESA;
        }

        Transacao transacao = new Transacao(
                descricao,
                valor,
                tipo,
                categoria != null ? categoria : "Geral",
                LocalDate.now()
        );

        return repository.save(transacao);
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularSaldoTotal() {
        BigDecimal totalReceitas = repository.sumValorByTipo(TipoTransacao.RECEITA);
        BigDecimal totalDespesas = repository.sumValorByTipo(TipoTransacao.DESPESA);

        return totalReceitas.subtract(totalDespesas);
    }
}