package sistema.os.domain.Entidades;

import java.time.LocalDateTime;
import java.util.UUID;

public class FormaPagamento {
    private final UUID id;
    private final String nome;
    private final String descricao;
    private final boolean ativo;
    private final LocalDateTime dataCriacao;

    public FormaPagamento(String nome, String descricao) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        this.id = UUID.randomUUID();
        this.nome = nome.trim();
        this.descricao = descricao != null ? descricao.trim() : "";
        this.ativo = true;
        this.dataCriacao = LocalDateTime.now();
    }

    public FormaPagamento(UUID id, String nome, String descricao, boolean ativo, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
}
