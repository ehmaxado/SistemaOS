package sistema.os.Application.UseCase;

import sistema.os.API.DTOs.Responses.OrdemServicoResponse;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;
import java.util.List;

public class ListarOrdensSerivicoUseCase {
    private final IOrdemServicoRepository repository;

    public ListarOrdensSerivicoUseCase(IOrdemServicoRepository repository) {
        this.repository = repository;
    }

    public List<OrdemServicoResponse> executar() {
        return repository.listarTodas()
            .stream()
            .map(os -> new OrdemServicoResponse(
                os.getId().toString(),
                os.getPessoaClienteId().toString(),
                os.getPessoaPrestadorId().toString(),
                os.getStatus().name(),
                os.getDataCriacao(),
                os.getDataAtualizacao(),
                os.getDescricao(),
                os.calcularValorTotal()
            ))
            .toList();
    }
}
