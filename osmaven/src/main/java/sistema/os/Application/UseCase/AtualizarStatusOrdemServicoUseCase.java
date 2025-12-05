package sistema.os.Application.UseCase;

import sistema.os.API.DTOs.Requests.MudarStatusOrdemServicoRequest;
import sistema.os.API.DTOs.Responses.OrdemServicoResponse;
import sistema.os.domain.Entidades.OrdemServico.OrdemServico;
import sistema.os.domain.Enums.StatusOrdemServico;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;
import java.util.UUID;

public class AtualizarStatusOrdemServicoUseCase {
    private final IOrdemServicoRepository repository;

    public AtualizarStatusOrdemServicoUseCase(IOrdemServicoRepository repository) {
        this.repository = repository;
    }

    public OrdemServicoResponse executar(String id, MudarStatusOrdemServicoRequest request) {
        UUID osId = UUID.fromString(id);
        OrdemServico os = repository.buscarPorId(osId);
        
        if (os == null) {
            throw new IllegalArgumentException("Ordem de serviço não encontrada com ID: " + id);
        }

        StatusOrdemServico novoStatus = StatusOrdemServico.valueOf(request.status().toUpperCase());
        os.mudarStatus(novoStatus);

        repository.editar(os);

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
