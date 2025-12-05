package sistema.os.Application.UseCase;

import sistema.os.API.DTOs.Responses.OrdemServicoResponse;
import sistema.os.domain.Entidades.OrdemServico.OrdemServico;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;
import java.util.UUID;

public class BuscarOrdemServicoPorIdUseCase {
    private final IOrdemServicoRepository repository;

    public BuscarOrdemServicoPorIdUseCase(IOrdemServicoRepository repository) {
        this.repository = repository;
    }

    public OrdemServicoResponse executar(String id) {
        OrdemServico os = repository.buscarPorId(UUID.fromString(id));
        
        if (os == null) {
            throw new IllegalArgumentException("Ordem de serviço não encontrada com ID: " + id);
        }

        return new OrdemServicoResponse(
            os.getId().toString(),
            os.getPessoaClienteId().toString(),
            os.getPessoaPrestadorId().toString(),
            os.getStatus().name(),
            os.getDataCriacao(),
            os.getDataAtualizacao(),
            os.getDescricao(),
            os.calcularValorTotal()
        );
    }
}
