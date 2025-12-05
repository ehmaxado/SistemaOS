package sistema.os.Application.UseCase;

import sistema.os.API.DTOs.Requests.CriarPagamentoRequest;
import sistema.os.API.DTOs.Responses.CriarPagamentoResponse;
import sistema.os.domain.Entidades.Pagamento;
import sistema.os.domain.Interfaces.IPagamentoRepository;

public class CriarPagamentoUseCase {
    private final IPagamentoRepository repository;

    public CriarPagamentoUseCase(IPagamentoRepository repository) {
        this.repository = repository;
    }

    public CriarPagamentoResponse executar(CriarPagamentoRequest request) {
        Pagamento pagamento = new Pagamento(
            request.getOrdemServicoId(),
            request.getValor(),
            request.getDescricao()
        );

        try {
            repository.salvar(pagamento);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar pagamento no banco de dados", e);
        }

        return new CriarPagamentoResponse(
            pagamento.getId().toString(),
            pagamento.getOrdemServicoId().toString(),
            pagamento.getValor(),
            pagamento.getStatus().name(),
            pagamento.getDataCriacao(),
            pagamento.getDescricao()
        );
    }
}
