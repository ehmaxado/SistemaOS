package sistema.os.API.DTOs.Responses;

import java.time.LocalDateTime;

public class EditarFormaPagamentoResponse {
    public String id;
    public String nome;
    public String descricao;
    public boolean ativo;
    public LocalDateTime dataCriacao;

    public EditarFormaPagamentoResponse() {
    }

    public EditarFormaPagamentoResponse(String id, String nome, String descricao, boolean ativo, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
