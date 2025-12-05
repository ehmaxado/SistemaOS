package sistema.os.Application.UseCase;

import java.util.UUID;

import sistema.os.API.DTOs.Responses.BuscarFormaPagamentoResponse;
import sistema.os.domain.Entidades.FormaPagamento;
import sistema.os.domain.Interfaces.IFormaPagamentoRepository;

public class BuscarFormaPagamentoPorIdUseCase {
    private final IFormaPagamentoRepository repository;

    public BuscarFormaPagamentoPorIdUseCase(IFormaPagamentoRepository repository) {
        this.repository = repository;
    }

    public BuscarFormaPagamentoResponse executar(String id) {
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

        return new BuscarFormaPagamentoResponse(
            formaPagamento.getId().toString(),
            formaPagamento.getNome(),
            formaPagamento.getDescricao(),
            formaPagamento.isAtivo(),
            formaPagamento.getDataCriacao()
        );
    }
}
