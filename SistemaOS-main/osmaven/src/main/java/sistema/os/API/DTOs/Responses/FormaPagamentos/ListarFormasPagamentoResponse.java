package sistema.os.API.DTOs.Responses.FormaPagamentos;

import java.util.List;

public class ListarFormasPagamentoResponse {
    public List<BuscarFormaPagamentoResponse> formas;
    public int total;
    public boolean sucesso;
    public String mensagem;

    public ListarFormasPagamentoResponse() {
    }

    public ListarFormasPagamentoResponse(List<BuscarFormaPagamentoResponse> formas, int total) {
        this.formas = formas;
        this.total = total;
        this.sucesso = true;
        this.mensagem = "Formas de pagamento listadas com sucesso";
    }

    public ListarFormasPagamentoResponse(List<BuscarFormaPagamentoResponse> formas, int total, String mensagem) {
        this.formas = formas;
        this.total = total;
        this.sucesso = true;
        this.mensagem = mensagem;
    }

    public List<BuscarFormaPagamentoResponse> getFormas() {
        return formas;
    }

    public void setFormas(List<BuscarFormaPagamentoResponse> formas) {
        this.formas = formas;
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
