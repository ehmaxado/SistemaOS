package sistema.os.API.DTOs.Requests.FormaPagamentos;

public class EditarFormaPagamentoRequest {
    public String nome;
    public String descricao;
    public boolean ativo;

    public EditarFormaPagamentoRequest() {
    }

    public EditarFormaPagamentoRequest(String nome, String descricao, boolean ativo) {
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
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
}
