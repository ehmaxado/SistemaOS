package sistema.os.domain.Entidades.OrdemServico;

import java.util.UUID;

public class OrdemServicoProduto {
    private final UUID id;
    private final UUID ordemServicoId;
    private final UUID produtoId;
    private final String nome; // Adicionado
    private final int quantidade;
    private final double valorUnitario; // Adicionado
    private final double valorTotal;

    // Cria novo detalhe de produto em uma ordem de serviço
    public OrdemServicoProduto(UUID ordemServicoId, UUID produtoId, int quantidade, double valorTotal) {
        if (ordemServicoId == null) {
            throw new IllegalArgumentException("ID da ordem de serviço é obrigatório");
        }
        if (produtoId == null) {
            throw new IllegalArgumentException("ID do produto é obrigatório");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        if (valorTotal < 0) {
            throw new IllegalArgumentException("Valor total não pode ser negativo");
        }

        this.id = UUID.randomUUID();
        this.ordemServicoId = ordemServicoId;
        this.produtoId = produtoId;
        this.nome = ""; // Será preenchido pelo repository
        this.quantidade = quantidade;
        this.valorUnitario = quantidade > 0 ? valorTotal / quantidade : 0;
        this.valorTotal = valorTotal;
    }

    // Reconstrói detalhe de produto existente (com nome)
    public OrdemServicoProduto(UUID id, UUID ordemServicoId, UUID produtoId, String nome, int quantidade, double valorUnitario, double valorTotal) {
        this.id = id;
        this.ordemServicoId = ordemServicoId;
        this.produtoId = produtoId;
        this.nome = nome != null ? nome : "";
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
    }
    
    // Reconstrói detalhe de produto existente (sem nome - compatibilidade)
    public OrdemServicoProduto(UUID id, UUID ordemServicoId, UUID produtoId, int quantidade, double valorTotal) {
        this.id = id;
        this.ordemServicoId = ordemServicoId;
        this.produtoId = produtoId;
        this.nome = "";
        this.quantidade = quantidade;
        this.valorUnitario = quantidade > 0 ? valorTotal / quantidade : 0;
        this.valorTotal = valorTotal;
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getOrdemServicoId() { return ordemServicoId; }
    public UUID getProdutoId() { return produtoId; }
    public String getNome() { return nome; }
    public int getQuantidade() { return quantidade; }
    public double getValorUnitario() { return valorUnitario; }
    public double getValorTotal() { return valorTotal; }
}
