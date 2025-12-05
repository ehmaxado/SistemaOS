package sistema.os.domain.Entidades;

import java.time.LocalDateTime;
import java.util.UUID;

public class Produto {
    private String nome;
    private final UUID id;
    private final String nome;
    private final String descricao;
    private final double preco;
    private final int estoque;
    private final LocalDateTime dataCadastro;

    // Cria novo produto com validações de negócio
    public Produto(String nome, String descricao, double preco, int estoque) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("Preço do produto não pode ser negativo");
        }
        if (estoque < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo");
        }
        
        this.id = UUID.randomUUID();
        this.nome = nome.trim();
        this.descricao = descricao != null ? descricao.trim() : "";
        this.preco = preco;
        this.estoque = estoque;
        this.dataCadastro = LocalDateTime.now();
    }

    // Reconstrói produto existente do banco de dados
    public Produto(UUID id, String nome, String descricao, double preco, int estoque, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.dataCadastro = dataCadastro;
    }

    // Getters
    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public double getPreco() { return preco; }
    public int getEstoque() { return estoque; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }
}
