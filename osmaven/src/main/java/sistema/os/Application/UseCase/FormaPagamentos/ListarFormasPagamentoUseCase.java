package sistema.os.Application.UseCase.FormaPagamentos;

import java.util.List;
import java.util.stream.Collectors;

import sistema.os.API.DTOs.Responses.FormaPagamentos.BuscarFormaPagamentoResponse;
import sistema.os.API.DTOs.Responses.FormaPagamentos.ListarFormasPagamentoResponse;
import sistema.os.domain.Entidades.FormaPagamento;
import sistema.os.domain.Interfaces.IFormaPagamentoRepository;

public class ListarFormasPagamentoUseCase {
    private final IFormaPagamentoRepository repository;

    public ListarFormasPagamentoUseCase(IFormaPagamentoRepository repository) {
        this.repository = repository;
    }

    public ListarFormasPagamentoResponse executar() {
        List<FormaPagamento> formas = repository.buscarTodas();

        List<BuscarFormaPagamentoResponse> formasResponse = formas.stream()
            .map(forma -> new BuscarFormaPagamentoResponse(
                forma.getId().toString(),
                forma.getNome(),
                forma.getDescricao(),
                forma.isAtivo(),
                forma.getDataCriacao()
            ))
            .collect(Collectors.toList());

        return new ListarFormasPagamentoResponse(formasResponse, formasResponse.size());
    }
}
