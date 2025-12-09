package sistema.os.domain.Entidades.OrdemServico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrdemServico {
    private final UUID id;
    private final String numeroOS;
    private final LocalDateTime dataAbertura;
    private final LocalDateTime dataFechamento;
    private final String id_usuario;
    private final String clienteId;
    private final double valorTotalProdutos;
    private final double valorTotalServicos;
    private final double valorTotal;
    private final double valorTotalFinal;
    private final String formaPagamento;
    private final String observacaoGeral;
    private final List<OrdemServicoServico> servicos;
    private final List<OrdemServicoProduto> produtos;

    // Cria nova ordem de serviço com validações de negócio
    public OrdemServico(String numeroOS, String id_usuario, String clienteId, String formaPagamento, String observacaoGeral) {
        if (numeroOS == null || numeroOS.trim().isEmpty()) {
            throw new IllegalArgumentException("Número da OS é obrigatório");
        }
        if (clienteId == null) {
            throw new IllegalArgumentException("ID do cliente é obrigatório");
        }
        
        this.id = UUID.randomUUID();
        this.numeroOS = numeroOS.trim();
        this.dataAbertura = LocalDateTime.now();
        this.dataFechamento = null;
        this.id_usuario = id_usuario;
        this.clienteId = clienteId;
        this.valorTotalProdutos = 0.0;
        this.valorTotalServicos = 0.0;
        this.valorTotal = 0.0;
        this.valorTotalFinal = 0.0;
        this.formaPagamento = formaPagamento != null ? formaPagamento.trim() : "";
        this.observacaoGeral = observacaoGeral != null ? observacaoGeral.trim() : "";
        this.servicos = new ArrayList<>();
        this.produtos = new ArrayList<>();
    }

    // Reconstrói ordem de serviço existente do banco de dados
    public OrdemServico(UUID id, String numeroOS, LocalDateTime dataAbertura, LocalDateTime dataFechamento,
                       String id_usuario, String clienteId, double valorTotalProdutos, double valorTotalServicos,
                       double valorTotal, double valorTotalFinal, String formaPagamento, String observacaoGeral) {
        this.id = id;
        this.numeroOS = numeroOS;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
        this.id_usuario = id_usuario;
        this.clienteId = clienteId;
        this.valorTotalProdutos = valorTotalProdutos;
        this.valorTotalServicos = valorTotalServicos;
        this.valorTotal = valorTotal;
        this.valorTotalFinal = valorTotalFinal;
        this.formaPagamento = formaPagamento != null ? formaPagamento.trim() : "";
        this.observacaoGeral = observacaoGeral != null ? observacaoGeral.trim() : "";
        this.servicos = new ArrayList<>();
        this.produtos = new ArrayList<>();
    }

    // Métodos de negócio
    public void adicionarServico(OrdemServicoServico servico) {
        if (servico == null) {
            throw new IllegalArgumentException("Serviço não pode ser nulo");
        }
        this.servicos.add(servico);
    }

    public void removerServico(UUID servicoId) {
        this.servicos.removeIf(s -> s.getServicoId().equals(servicoId));
    }

    public void adicionarProduto(OrdemServicoProduto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        this.produtos.add(produto);
    }

    public void removerProduto(UUID produtoId) {
        this.produtos.removeIf(p -> p.getProdutoId().equals(produtoId));
    }

    public double calcularValorTotalServicos() {
        return servicos.stream().mapToDouble(OrdemServicoServico::getValorTotal).sum();
    }

    public double calcularValorTotalProdutos() {
        return produtos.stream().mapToDouble(OrdemServicoProduto::getValorTotal).sum();
    }

    public double calcularValorTotal() {
        return calcularValorTotalServicos() + calcularValorTotalProdutos();
    }

    // Getters
    public UUID getId() { return id; }
    public String getNumeroOS() { return numeroOS; }
    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public LocalDateTime getDataFechamento() { return dataFechamento; }
    public String getId_usuario() { return id_usuario; }
    public String getClienteId() { return clienteId; }
    public double getValorTotalProdutos() { return valorTotalProdutos; }
    public double getValorTotalServicos() { return valorTotalServicos; }
    public double getValorTotal() { return valorTotal; }
    public double getValorTotalFinal() { return valorTotalFinal; }
    public String getFormaPagamento() { return formaPagamento; }
    public String getObservacaoGeral() { return observacaoGeral; }
    public List<OrdemServicoServico> getServicos() { return new ArrayList<>(servicos); }
    public List<OrdemServicoProduto> getProdutos() { return new ArrayList<>(produtos); }
}
