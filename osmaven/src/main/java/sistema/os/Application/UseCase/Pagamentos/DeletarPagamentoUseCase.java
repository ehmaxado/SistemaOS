package sistema.os.Application.UseCase.Pagamentos;

import java.util.UUID;

import sistema.os.API.DTOs.Responses.Pagamentos.DeletarPagamentoResponse;
import sistema.os.domain.Interfaces.IPagamentoRepository;

public class DeletarPagamentoUseCase {
    private final IPagamentoRepository repository;

    public DeletarPagamentoUseCase(IPagamentoRepository repository) {
        this.repository = repository;
    }

    public DeletarPagamentoResponse executar(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID é obrigatório");
        }

        UUID pagamentoId;
        try {
            pagamentoId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID inválido");
        }

        boolean deletado = repository.deletar(pagamentoId);

        if (!deletado) {
            throw new IllegalArgumentException("Pagamento não encontrado");
        }

        return new DeletarPagamentoResponse(id, "Pagamento deletado com sucesso", true);
    }
}
