package sistema.os.API.DTOs.Requests.Pagamentos;

public class EditarPagamentoRequest {
    public double valor;
    public String descricao;

    public EditarPagamentoRequest() {
    }

    public EditarPagamentoRequest(double valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
