package sistema.os.Application.UseCase;

import java.util.UUID;

import sistema.os.API.DTOs.Responses.BuscarPagamentoResponse;
import sistema.os.domain.Entidades.Pagamento;
import sistema.os.domain.Interfaces.IPagamentoRepository;

public class BuscarPagamentoPorIdUseCase {
    private final IPagamentoRepository repository;

    public BuscarPagamentoPorIdUseCase(IPagamentoRepository repository) {
        this.repository = repository;
    }

    public BuscarPagamentoResponse executar(String id) {
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

        return new BuscarPagamentoResponse(
            pagamento.getId().toString(),
            pagamento.getOrdemServicoId().toString(),
            pagamento.getValor(),
            pagamento.getStatus().name(),
            pagamento.getDataPagamento(),
            pagamento.getDataCriacao(),
            pagamento.getDescricao()
        );
    }
}
