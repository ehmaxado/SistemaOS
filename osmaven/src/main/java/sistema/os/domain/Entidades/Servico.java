package sistema.os.domain.Entidades;

import java.time.LocalDateTime;
import java.util.UUID;

public class Servico {
    private final UUID id;
    private final String nome;
    private final String descricao;
    private final double preco;
    private final LocalDateTime dataCadastro;

    // Cria novo serviço com validações de negócio
    public Servico(String nome, String descricao, double preco) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do serviço é obrigatório");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("Preço do serviço não pode ser negativo");
        }
        
        this.id = UUID.randomUUID();
        this.nome = nome.trim();
        this.descricao = descricao != null ? descricao.trim() : "";
        this.preco = preco;
        this.dataCadastro = LocalDateTime.now();
    }

    // Reconstrói serviço existente do banco de dados
    public Servico(UUID id, String nome, String descricao, double preco, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.dataCadastro = dataCadastro;
    }

    // Getters
    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public double getPreco() { return preco; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }
}
