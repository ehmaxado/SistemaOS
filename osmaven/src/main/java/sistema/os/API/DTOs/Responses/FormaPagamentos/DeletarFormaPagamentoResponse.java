package sistema.os.API.DTOs.Responses.FormaPagamentos;

public class DeletarFormaPagamentoResponse {
    public String id;
    public String mensagem;
    public boolean sucesso;

    public DeletarFormaPagamentoResponse() {
    }

    public DeletarFormaPagamentoResponse(String id, String mensagem, boolean sucesso) {
        this.id = id;
        this.mensagem = mensagem;
        this.sucesso = sucesso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }
}
