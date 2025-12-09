package sistema.os.domain.Entidades;

import java.time.LocalDateTime;
import java.util.UUID;

public class Usuario {
    private final UUID id;
    private final String nome;
    private final String email;
    private final String senha;
    private final String perfil;
    private final boolean ativo;
    private final LocalDateTime dataCriacao;

    public Usuario(String nome, String email, String senha, String perfil) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
        if (perfil == null || perfil.trim().isEmpty()) {
            throw new IllegalArgumentException("Perfil é obrigatório");
        }

        this.id = UUID.randomUUID();
        this.nome = nome.trim();
        this.email = email.trim().toLowerCase();
        this.senha = senha; // Armazenando em texto simples (apenas para desenvolvimento)
        this.perfil = perfil.trim();
        this.ativo = true;
        this.dataCriacao = LocalDateTime.now();
    }

    public Usuario(UUID id, String nome, String email, String senha, String perfil, boolean ativo, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }
}
