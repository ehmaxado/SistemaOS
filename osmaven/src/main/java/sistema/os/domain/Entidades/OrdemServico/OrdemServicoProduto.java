package sistema.os.domain.Entidades.OrdemServico;

import java.util.UUID;

public class OrdemServicoProduto {
    private final UUID id;
    private final UUID ordemServicoId;
    private final UUID produtoId;
    private final double valorUnitario;
    private final int quantidade;

    // Cria novo detalhe de produto em uma ordem de serviço
    public OrdemServicoProduto(UUID ordemServicoId, UUID produtoId, double valorUnitario, int quantidade) {
        if (ordemServicoId == null) {
            throw new IllegalArgumentException("ID da ordem de serviço é obrigatório");
        }
        if (produtoId == null) {
            throw new IllegalArgumentException("ID do produto é obrigatório");
        }
        if (valorUnitario < 0) {
            throw new IllegalArgumentException("Valor unitário não pode ser negativo");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        this.id = UUID.randomUUID();
        this.ordemServicoId = ordemServicoId;
        this.produtoId = produtoId;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
    }

    // Reconstrói detalhe de produto existente
    public OrdemServicoProduto(UUID id, UUID ordemServicoId, UUID produtoId, double valorUnitario, int quantidade) {
        this.id = id;
        this.ordemServicoId = ordemServicoId;
        this.produtoId = produtoId;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getOrdemServicoId() { return ordemServicoId; }
    public UUID getProdutoId() { return produtoId; }
    public double getValorUnitario() { return valorUnitario; }
    public int getQuantidade() { return quantidade; }
}
