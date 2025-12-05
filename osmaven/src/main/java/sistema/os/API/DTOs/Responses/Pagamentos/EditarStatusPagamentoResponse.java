package sistema.os.API.DTOs.Responses;

import java.time.LocalDateTime;

public class EditarStatusPagamentoResponse {
    public String id;
    public String status;
    public LocalDateTime dataPagamento;

    public EditarStatusPagamentoResponse() {
    }

    public EditarStatusPagamentoResponse(String id, String status, LocalDateTime dataPagamento) {
        this.id = id;
        this.status = status;
        this.dataPagamento = dataPagamento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
