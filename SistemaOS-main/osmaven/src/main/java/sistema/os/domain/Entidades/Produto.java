package sistema.os.domain.Entidades;

import java.util.UUID;

public class Produto {
    private final UUID id;
    private final String nome;
    private final String descricao;
    private final String marca;
    private final String unidade;
    private final int estoqueAtual;
    private final double valorCusto;
    private final double valorVenda;
    private final boolean ativo;

    // Cria novo produto com validações de negócio
    public Produto(String nome, String descricao, String marca, String unidade, 
                   int estoqueAtual, double valorCusto, double valorVenda) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        if (valorCusto < 0) {
            throw new IllegalArgumentException("Valor de custo não pode ser negativo");
        }
        if (valorVenda < 0) {
            throw new IllegalArgumentException("Valor de venda não pode ser negativo");
        }
        if (estoqueAtual < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo");
        }
        
        this.id = UUID.randomUUID();
        this.nome = nome.trim();
        this.descricao = descricao != null ? descricao.trim() : "";
        this.marca = marca != null ? marca.trim() : "";
        this.unidade = unidade != null ? unidade.trim() : "";
        this.estoqueAtual = estoqueAtual;
        this.valorCusto = valorCusto;
        this.valorVenda = valorVenda;
        this.ativo = true;
    }

    // Reconstrói produto existente do banco de dados
    public Produto(UUID id, String nome, String descricao, String marca, String unidade,
                   int estoqueAtual, double valorCusto, double valorVenda, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.marca = marca;
        this.unidade = unidade;
        this.estoqueAtual = estoqueAtual;
        this.valorCusto = valorCusto;
        this.valorVenda = valorVenda;
        this.ativo = ativo;
    }

    // Getters
    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getMarca() { return marca; }
    public String getUnidade() { return unidade; }
    public int getEstoqueAtual() { return estoqueAtual; }
    public double getValorCusto() { return valorCusto; }
    public double getValorVenda() { return valorVenda; }
    public boolean isAtivo() { return ativo; }
}
