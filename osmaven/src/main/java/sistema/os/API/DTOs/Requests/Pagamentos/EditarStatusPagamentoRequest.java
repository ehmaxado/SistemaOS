package sistema.os.API.DTOs.Requests.Pagamentos;

public class EditarStatusPagamentoRequest {
    public String status;

    public EditarStatusPagamentoRequest() {
    }

    public EditarStatusPagamentoRequest(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
