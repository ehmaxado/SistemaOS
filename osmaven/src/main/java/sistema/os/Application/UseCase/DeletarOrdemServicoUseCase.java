package sistema.os.Application.UseCase;

import sistema.os.domain.Interfaces.IOrdemServicoRepository;
import java.util.UUID;

public class DeletarOrdemServicoUseCase {
    private final IOrdemServicoRepository repository;

    public DeletarOrdemServicoUseCase(IOrdemServicoRepository repository) {
        this.repository = repository;
    }

    public void executar(String id) {
        UUID osId = UUID.fromString(id);
        
        if (repository.buscarPorId(osId) == null) {
            throw new IllegalArgumentException("Ordem de serviço não encontrada com ID: " + id);
        }

        repository.deletar(osId);
    }
}
