package sistema.os.Application.UseCase;

import java.util.UUID;

import sistema.os.API.DTOs.Responses.DeletarFormaPagamentoResponse;
import sistema.os.domain.Interfaces.IFormaPagamentoRepository;

public class DeletarFormaPagamentoUseCase {
    private final IFormaPagamentoRepository repository;

    public DeletarFormaPagamentoUseCase(IFormaPagamentoRepository repository) {
        this.repository = repository;
    }

    public DeletarFormaPagamentoResponse executar(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID é obrigatório");
        }

        UUID formaPagamentoId;
        try {
            formaPagamentoId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID inválido");
        }

        boolean deletada = repository.deletar(formaPagamentoId);

        if (!deletada) {
            throw new IllegalArgumentException("Forma de pagamento não encontrada");
        }

        return new DeletarFormaPagamentoResponse(id, "Forma de pagamento deletada com sucesso", true);
    }
}
