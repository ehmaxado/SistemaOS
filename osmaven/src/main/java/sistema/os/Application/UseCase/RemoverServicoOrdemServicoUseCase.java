package sistema.os.Application.UseCase;

import sistema.os.API.DTOs.Responses.OrdemServicoResponse;
import sistema.os.domain.Entidades.OrdemServico.OrdemServico;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;
import java.util.UUID;

public class RemoverServicoOrdemServicoUseCase {
    private final IOrdemServicoRepository repository;

    public RemoverServicoOrdemServicoUseCase(IOrdemServicoRepository repository) {
        this.repository = repository;
    }

    public OrdemServicoResponse executar(String ordemServicoId, String servicoId) {
        UUID osId = UUID.fromString(ordemServicoId);
        UUID sId = UUID.fromString(servicoId);
        
        OrdemServico os = repository.buscarPorId(osId);
        
        if (os == null) {
            throw new IllegalArgumentException("Ordem de serviço não encontrada com ID: " + ordemServicoId);
        }

        repository.removerServico(osId, sId);
        os.removerServico(sId);

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
