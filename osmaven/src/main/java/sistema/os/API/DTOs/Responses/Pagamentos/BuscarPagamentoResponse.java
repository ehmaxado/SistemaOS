package sistema.os.API.DTOs.Responses;

import java.time.LocalDateTime;

public class BuscarPagamentoResponse {
    public String id;
    public String ordemServicoId;
    public double valor;
    public String status;
    public LocalDateTime dataPagamento;
    public LocalDateTime dataCriacao;
    public String descricao;

    public BuscarPagamentoResponse() {
    }

    public BuscarPagamentoResponse(String id, String ordemServicoId, double valor, String status, LocalDateTime dataPagamento, LocalDateTime dataCriacao, String descricao) {
        this.id = id;
        this.ordemServicoId = ordemServicoId;
        this.valor = valor;
        this.status = status;
        this.dataPagamento = dataPagamento;
        this.dataCriacao = dataCriacao;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdemServicoId() {
        return ordemServicoId;
    }

    public void setOrdemServicoId(String ordemServicoId) {
        this.ordemServicoId = ordemServicoId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
