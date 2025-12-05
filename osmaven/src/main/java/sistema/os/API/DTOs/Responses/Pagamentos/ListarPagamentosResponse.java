package sistema.os.API.DTOs.Responses;

import java.util.List;

public class ListarPagamentosResponse {
    public List<BuscarPagamentoResponse> pagamentos;
    public int total;
    public boolean sucesso;
    public String mensagem;

    public ListarPagamentosResponse() {
    }

    public ListarPagamentosResponse(List<BuscarPagamentoResponse> pagamentos, int total) {
        this.pagamentos = pagamentos;
        this.total = total;
        this.sucesso = true;
        this.mensagem = "Pagamentos listados com sucesso";
    }

    public ListarPagamentosResponse(List<BuscarPagamentoResponse> pagamentos, int total, String mensagem) {
        this.pagamentos = pagamentos;
        this.total = total;
        this.sucesso = true;
        this.mensagem = mensagem;
    }

    public List<BuscarPagamentoResponse> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<BuscarPagamentoResponse> pagamentos) {
        this.pagamentos = pagamentos;
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
