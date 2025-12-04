package sistema.os.Application.UseCase;

import java.util.UUID;

import sistema.os.API.DTOs.Requests.EditarFormaPagamentoRequest;
import sistema.os.API.DTOs.Responses.EditarFormaPagamentoResponse;
import sistema.os.domain.Entidades.FormaPagamento;
import sistema.os.domain.Interfaces.IFormaPagamentoRepository;

public class EditarFormaPagamentoUseCase {
    private final IFormaPagamentoRepository repository;

    public EditarFormaPagamentoUseCase(IFormaPagamentoRepository repository) {
        this.repository = repository;
    }

    public EditarFormaPagamentoResponse executar(String id, EditarFormaPagamentoRequest request) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID é obrigatório");
        }

        UUID formaPagamentoId;
        try {
            formaPagamentoId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID inválido");
        }

        FormaPagamento formaPagamento = repository.buscarPorId(formaPagamentoId);

        if (formaPagamento == null) {
            throw new IllegalArgumentException("Forma de pagamento não encontrada");
        }

        FormaPagamento formaPagamentoAtualizada = new FormaPagamento(
            formaPagamentoId,
            request.getNome(),
            request.getDescricao(),
            request.isAtivo(),
            formaPagamento.getDataCriacao()
        );

        try {
            repository.atualizar(formaPagamentoAtualizada);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao atualizar forma de pagamento no banco de dados", e);
        }

        return new EditarFormaPagamentoResponse(
            formaPagamentoAtualizada.getId().toString(),
            formaPagamentoAtualizada.getNome(),
            formaPagamentoAtualizada.getDescricao(),
            formaPagamentoAtualizada.isAtivo(),
            formaPagamentoAtualizada.getDataCriacao()
        );
    }
}
