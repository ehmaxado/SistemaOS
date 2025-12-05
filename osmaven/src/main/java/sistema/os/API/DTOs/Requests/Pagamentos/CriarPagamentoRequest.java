package sistema.os.API.DTOs.Requests;

import java.util.UUID;

public class CriarPagamentoRequest {
    public UUID ordemServicoId;
    public double valor;
    public String descricao;

    public CriarPagamentoRequest() {
    }

    public CriarPagamentoRequest(UUID ordemServicoId, double valor, String descricao) {
        this.ordemServicoId = ordemServicoId;
        this.valor = valor;
        this.descricao = descricao;
    }

    public UUID getOrdemServicoId() {
        return ordemServicoId;
    }

    public void setOrdemServicoId(UUID ordemServicoId) {
        this.ordemServicoId = ordemServicoId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
