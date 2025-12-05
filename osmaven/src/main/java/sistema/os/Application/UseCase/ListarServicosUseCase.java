package sistema.os.Application.UseCase;

import sistema.os.API.DTOs.Responses.ServicoResponse;
import sistema.os.domain.Entidades.Servico;
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
                s.getNome(),
                s.getDescricao(),
                s.getPreco(),
                s.getDataCadastro()
            ))
            .toList();
    }
}
