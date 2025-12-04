package sistema.os.API.DTOs.Requests;

public class CriarFormaPagamentoRequest {
    public String nome;
    public String descricao;

    public CriarFormaPagamentoRequest() {
    }

    public CriarFormaPagamentoRequest(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
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
}
