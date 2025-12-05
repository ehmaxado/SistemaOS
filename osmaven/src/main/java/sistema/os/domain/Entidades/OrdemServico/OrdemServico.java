package sistema.os.domain.Entidades.OrdemServico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sistema.os.domain.Enums.StatusOrdemServico;

public class OrdemServico {
    private final UUID id;
    private final UUID pessoaClienteId;
    private final UUID pessoaPrestadorId;
    private StatusOrdemServico status;
    private final LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private String descricao;
    private final List<OrdemServicoServico> servicos;
    private final List<OrdemServicoProduto> produtos;

    // Cria nova ordem de serviço com validações de negócio
    public OrdemServico(UUID pessoaClienteId, UUID pessoaPrestadorId, String descricao) {
        if (pessoaClienteId == null) {
            throw new IllegalArgumentException("ID do cliente é obrigatório");
        }
        if (pessoaPrestadorId == null) {
            throw new IllegalArgumentException("ID do prestador é obrigatório");
        }
        if (pessoaClienteId.equals(pessoaPrestadorId)) {
            throw new IllegalArgumentException("Cliente e prestador devem ser pessoas diferentes");
        }
        
        this.id = UUID.randomUUID();
        this.pessoaClienteId = pessoaClienteId;
        this.pessoaPrestadorId = pessoaPrestadorId;
        this.status = StatusOrdemServico.ABERTA;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.descricao = descricao != null ? descricao.trim() : "";
        this.servicos = new ArrayList<>();
        this.produtos = new ArrayList<>();
    }

    // Reconstrói ordem de serviço existente do banco de dados
    public OrdemServico(UUID id, UUID pessoaClienteId, UUID pessoaPrestadorId, StatusOrdemServico status,
                       LocalDateTime dataCriacao, LocalDateTime dataAtualizacao, String descricao) {
        this.id = id;
        this.pessoaClienteId = pessoaClienteId;
        this.pessoaPrestadorId = pessoaPrestadorId;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.descricao = descricao;
        this.servicos = new ArrayList<>();
        this.produtos = new ArrayList<>();
    }

    // Métodos de negócio
    public void adicionarServico(OrdemServicoServico servico) {
        if (servico == null) {
            throw new IllegalArgumentException("Serviço não pode ser nulo");
        }
        if (status == StatusOrdemServico.CONCLUIDA || status == StatusOrdemServico.CANCELADA) {
            throw new IllegalArgumentException("Não é possível adicionar serviço em ordem de serviço finalizada");
        }
        this.servicos.add(servico);
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void removerServico(UUID servicoId) {
        if (status == StatusOrdemServico.CONCLUIDA || status == StatusOrdemServico.CANCELADA) {
            throw new IllegalArgumentException("Não é possível remover serviço de ordem de serviço finalizada");
        }
        this.servicos.removeIf(s -> s.getServicoId().equals(servicoId));
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void adicionarProduto(OrdemServicoProduto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        if (status == StatusOrdemServico.CONCLUIDA || status == StatusOrdemServico.CANCELADA) {
            throw new IllegalArgumentException("Não é possível adicionar produto em ordem de serviço finalizada");
        }
        this.produtos.add(produto);
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void removerProduto(UUID produtoId) {
        if (status == StatusOrdemServico.CONCLUIDA || status == StatusOrdemServico.CANCELADA) {
            throw new IllegalArgumentException("Não é possível remover produto de ordem de serviço finalizada");
        }
        this.produtos.removeIf(p -> p.getProdutoId().equals(produtoId));
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void mudarStatus(StatusOrdemServico novoStatus) {
        if (novoStatus == null) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }
        this.status = novoStatus;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public double calcularValorTotal() {
        double total = 0;
        total += servicos.stream().mapToDouble(OrdemServicoServico::getValorUnitario).sum();
        total += produtos.stream().mapToDouble(p -> p.getValorUnitario() * p.getQuantidade()).sum();
        return total;
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getPessoaClienteId() { return pessoaClienteId; }
    public UUID getPessoaPrestadorId() { return pessoaPrestadorId; }
    public StatusOrdemServico getStatus() { return status; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public String getDescricao() { return descricao; }
    public List<OrdemServicoServico> getServicos() { return new ArrayList<>(servicos); }
    public List<OrdemServicoProduto> getProdutos() { return new ArrayList<>(produtos); }
}
