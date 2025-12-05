package sistema.os.Application.UseCase;

import sistema.os.API.DTOs.Requests.CriarFormaPagamentoRequest;
import sistema.os.API.DTOs.Responses.CriarFormaPagamentoResponse;
import sistema.os.domain.Entidades.FormaPagamento;
import sistema.os.domain.Interfaces.IFormaPagamentoRepository;

public class CriarFormaPagamentoUseCase {
    private final IFormaPagamentoRepository repository;

    public CriarFormaPagamentoUseCase(IFormaPagamentoRepository repository) {
        this.repository = repository;
    }

    public CriarFormaPagamentoResponse executar(CriarFormaPagamentoRequest request) {
        FormaPagamento formaPagamento = new FormaPagamento(
            request.getNome(),
            request.getDescricao()
        );

        try {
            repository.salvar(formaPagamento);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar forma de pagamento no banco de dados", e);
        }

        return new CriarFormaPagamentoResponse(
            formaPagamento.getId().toString(),
            formaPagamento.getNome(),
            formaPagamento.getDescricao(),
            formaPagamento.isAtivo(),
            formaPagamento.getDataCriacao()
        );
    }
}
