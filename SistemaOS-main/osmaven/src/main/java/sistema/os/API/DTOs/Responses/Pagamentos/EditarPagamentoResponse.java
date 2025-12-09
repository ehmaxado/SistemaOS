package sistema.os.API.DTOs.Responses.Pagamentos;

public class EditarPagamentoResponse {
    public boolean sucesso;
    public String mensagem;
    public String id;

    public EditarPagamentoResponse(boolean sucesso, String mensagem, String id) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.id = id;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getId() {
        return id;
    }
}
