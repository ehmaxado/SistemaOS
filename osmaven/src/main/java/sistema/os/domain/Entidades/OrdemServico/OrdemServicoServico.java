package sistema.os.domain.Entidades.OrdemServico;

import java.util.UUID;

public class OrdemServicoServico {
    private final UUID id;
    private final UUID ordemServicoId;
    private final UUID servicoId;
    private final String descricao; // Adicionado
    private final int quantidade;
    private final double valorUnitario; // Adicionado
    private final double valorTotal;

    // Cria novo detalhe de serviço em uma ordem de serviço
    public OrdemServicoServico(UUID ordemServicoId, UUID servicoId, int quantidade, double valorTotal) {
        if (ordemServicoId == null) {
            throw new IllegalArgumentException("ID da ordem de serviço é obrigatório");
        }
        if (servicoId == null) {
            throw new IllegalArgumentException("ID do serviço é obrigatório");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        if (valorTotal < 0) {
            throw new IllegalArgumentException("Valor total não pode ser negativo");
        }

        this.id = UUID.randomUUID();
        this.ordemServicoId = ordemServicoId;
        this.servicoId = servicoId;
        this.descricao = ""; // Será preenchido pelo repository
        this.quantidade = quantidade;
        this.valorUnitario = quantidade > 0 ? valorTotal / quantidade : 0;
        this.valorTotal = valorTotal;
    }

    // Reconstrói detalhe de serviço existente (com descrição)
    public OrdemServicoServico(UUID id, UUID ordemServicoId, UUID servicoId, String descricao, int quantidade, double valorUnitario, double valorTotal) {
        this.id = id;
        this.ordemServicoId = ordemServicoId;
        this.servicoId = servicoId;
        this.descricao = descricao != null ? descricao : "";
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
    }
    
    // Reconstrói detalhe de serviço existente (sem descrição - compatibilidade)
    public OrdemServicoServico(UUID id, UUID ordemServicoId, UUID servicoId, int quantidade, double valorTotal) {
        this.id = id;
        this.ordemServicoId = ordemServicoId;
        this.servicoId = servicoId;
        this.descricao = "";
        this.quantidade = quantidade;
        this.valorUnitario = quantidade > 0 ? valorTotal / quantidade : 0;
        this.valorTotal = valorTotal;
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getOrdemServicoId() { return ordemServicoId; }
    public UUID getServicoId() { return servicoId; }
    public String getDescricao() { return descricao; }
    public int getQuantidade() { return quantidade; }
    public double getValorUnitario() { return valorUnitario; }
    public double getValorTotal() { return valorTotal; }
}
