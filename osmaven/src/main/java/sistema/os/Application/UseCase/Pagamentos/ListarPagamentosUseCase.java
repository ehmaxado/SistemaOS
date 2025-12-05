package sistema.os.Application.UseCase;

import java.util.List;
import java.util.stream.Collectors;

import sistema.os.API.DTOs.Responses.BuscarPagamentoResponse;
import sistema.os.API.DTOs.Responses.ListarPagamentosResponse;
import sistema.os.domain.Entidades.Pagamento;
import sistema.os.domain.Enums.StatusPagamento;
import sistema.os.domain.Interfaces.IPagamentoRepository;

public class ListarPagamentosUseCase {
    private final IPagamentoRepository repository;

    public ListarPagamentosUseCase(IPagamentoRepository repository) {
        this.repository = repository;
    }

    public ListarPagamentosResponse executar() {
        List<Pagamento> pagamentos = repository.buscarTodas();
        return montarResposta(pagamentos);
    }

    public ListarPagamentosResponse executarPorStatus(String status) {
        StatusPagamento statusEnum = StatusPagamento.valueOf(status.toUpperCase());
        List<Pagamento> pagamentos = repository.buscarPorStatus(statusEnum);
        return montarResposta(pagamentos);
    }

    private ListarPagamentosResponse montarResposta(List<Pagamento> pagamentos) {
        List<BuscarPagamentoResponse> pagamentosResponse = pagamentos.stream()
            .map(pagamento -> new BuscarPagamentoResponse(
                pagamento.getId().toString(),
                pagamento.getOrdemServicoId().toString(),
                pagamento.getValor(),
                pagamento.getStatus().name(),
                pagamento.getDataPagamento(),
                pagamento.getDataCriacao(),
                pagamento.getDescricao()
            ))
            .collect(Collectors.toList());

        return new ListarPagamentosResponse(pagamentosResponse, pagamentosResponse.size());
    }
}
