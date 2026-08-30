package com.example.demo.ai;

import com.example.demo.service.TransacaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.math.BigDecimal;
import java.util.function.Function;

@Configuration
public class TransacaoTools {

    private final TransacaoService service;

    public TransacaoTools(TransacaoService service) {
        this.service = service;
    }

    public record TransacaoRequest(String descricao, double valor, String tipo, String categoria) {}

    @Bean
    @Description("Registra uma nova transação financeira, que pode ser uma RECEITA ou uma DESPESA.")
    public Function<TransacaoRequest, String> registrarTransacao() {
        return request -> {
            service.salvar(request.descricao(), BigDecimal.valueOf(request.valor()), request.tipo(), request.categoria());
            return String.format("A transação '%s' no valor de R$ %.2f foi registrada com sucesso.",
                    request.descricao(), request.valor());
        };
    }

    public record SaldoRequest() {}

    @Bean
    @Description("Consulta o saldo atual da conta do usuário. Retorna a diferença entre receitas e despesas.")
    public Function<SaldoRequest, String> consultarSaldo() {
        return request -> {
            BigDecimal saldo = service.calcularSaldoTotal();
            return String.format("O saldo atual da sua conta é de R$ %.2f.", saldo);
        };
    }
}