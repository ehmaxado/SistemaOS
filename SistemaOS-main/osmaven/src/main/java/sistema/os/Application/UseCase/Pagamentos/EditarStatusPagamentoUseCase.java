package sistema.os.Application.UseCase.Pagamentos;

import java.time.LocalDateTime;
import java.util.UUID;

import sistema.os.API.DTOs.Requests.Pagamentos.EditarStatusPagamentoRequest;
import sistema.os.API.DTOs.Responses.Pagamentos.EditarStatusPagamentoResponse;
import sistema.os.domain.Entidades.Pagamento;
import sistema.os.domain.Enums.StatusPagamento;
import sistema.os.domain.Interfaces.IPagamentoRepository;

public class EditarStatusPagamentoUseCase {
    private final IPagamentoRepository repository;

    public EditarStatusPagamentoUseCase(IPagamentoRepository repository) {
        this.repository = repository;
    }

    public EditarStatusPagamentoResponse executar(String id, EditarStatusPagamentoRequest request) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID é obrigatório");
        }

        UUID pagamentoId;
        try {
            pagamentoId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID inválido");
        }

        Pagamento pagamento = repository.buscarPorId(pagamentoId);

        if (pagamento == null) {
            throw new IllegalArgumentException("Pagamento não encontrado");
        }

        StatusPagamento novoStatus;
        try {
            novoStatus = StatusPagamento.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido");
        }

        LocalDateTime dataPagamento = novoStatus == StatusPagamento.APROVADO ? LocalDateTime.now() : pagamento.getDataPagamento();

        Pagamento pagamentoAtualizado = new Pagamento(
            pagamentoId,
            pagamento.getOrdemServicoId(),
            pagamento.getValor(),
            novoStatus,
            dataPagamento,
            pagamento.getDataCriacao(),
            pagamento.getDescricao()
        );

        try {
            repository.atualizar(pagamentoAtualizado);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao atualizar pagamento no banco de dados", e);
        }

        return new EditarStatusPagamentoResponse(
            pagamentoAtualizado.getId().toString(),
            pagamentoAtualizado.getStatus().name(),
            pagamentoAtualizado.getDataPagamento()
        );
    }
}
