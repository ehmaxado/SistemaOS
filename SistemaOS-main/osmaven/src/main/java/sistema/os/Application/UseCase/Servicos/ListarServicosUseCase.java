package sistema.os.Application.UseCase.Servicos;

import sistema.os.API.DTOs.Responses.Servicos.ServicoResponse;
import sistema.os.domain.Interfaces.IServicoRepository;
import java.util.List;

public class ListarServicosUseCase {
    private final IServicoRepository repository;

    public ListarServicosUseCase(IServicoRepository repository) {
        this.repository = repository;
    }

    public List<ServicoResponse> executar() {
        return repository.listarTodos()
            .stream()
            .map(s -> new ServicoResponse(
                s.getId().toString(),
                s.getDescricao(),
                s.getCodigo(),
                s.getValorPadrao(),
                s.getTempoEstimadoMinutos(),
                s.isAtivo()
            ))
            .toList();
    }
}
