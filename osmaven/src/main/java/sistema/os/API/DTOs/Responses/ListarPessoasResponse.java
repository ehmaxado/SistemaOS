package sistema.os.API.DTOs.Responses;

import java.util.List;

public class ListarPessoasResponse {
    public List<ListaPessoaResponse> pessoas;
    public int total;
    public boolean sucesso;
    public String mensagem;

    public ListarPessoasResponse() {
    }

    public ListarPessoasResponse(List<ListaPessoaResponse> pessoas, int total) {
        this.pessoas = pessoas;
        this.total = total;
        this.sucesso = true;
        this.mensagem = "Pessoas listadas com sucesso";
    }

    public ListarPessoasResponse(List<ListaPessoaResponse> pessoas, int total, String mensagem) {
        this.pessoas = pessoas;
        this.total = total;
        this.sucesso = true;
        this.mensagem = mensagem;
    }

    public List<ListaPessoaResponse> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<ListaPessoaResponse> pessoas) {
        this.pessoas = pessoas;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
