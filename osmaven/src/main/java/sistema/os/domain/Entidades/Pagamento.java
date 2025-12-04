package sistema.os.domain.Entidades;

import java.time.LocalDateTime;
import java.util.UUID;

import sistema.os.domain.Enums.StatusPagamento;

public class Pagamento {
    private final UUID id;
    private final UUID ordemServicoId;
    private final double valor;
    private final StatusPagamento status;
    private final LocalDateTime dataPagamento;
    private final LocalDateTime dataCriacao;
    private final String descricao;

    public Pagamento(UUID ordemServicoId, double valor, String descricao) {
        if (ordemServicoId == null) {
            throw new IllegalArgumentException("ID da ordem de serviço é obrigatório");
        }

        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }

        this.id = UUID.randomUUID();
        this.ordemServicoId = ordemServicoId;
        this.valor = valor;
        this.status = StatusPagamento.PENDENTE;
        this.dataPagamento = null;
        this.dataCriacao = LocalDateTime.now();
        this.descricao = descricao != null ? descricao.trim() : "";
    }

    public Pagamento(UUID id, UUID ordemServicoId, double valor, StatusPagamento status, 
                     LocalDateTime dataPagamento, LocalDateTime dataCriacao, String descricao) {
        this.id = id;
        this.ordemServicoId = ordemServicoId;
        this.valor = valor;
        this.status = status;
        this.dataPagamento = dataPagamento;
        this.dataCriacao = dataCriacao;
        this.descricao = descricao;
    }

    public UUID getId() {
        return id;
    }

    public UUID getOrdemServicoId() {
        return ordemServicoId;
    }

    public double getValor() {
        return valor;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getDescricao() {
        return descricao;
    }
}

