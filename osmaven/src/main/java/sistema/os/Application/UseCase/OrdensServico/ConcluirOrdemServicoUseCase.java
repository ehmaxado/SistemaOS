package sistema.os.Application.UseCase.OrdensServico;

import sistema.os.domain.Entidades.OrdemServico.OrdemServico;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;
import java.util.UUID;

public class ConcluirOrdemServicoUseCase {
    private final IOrdemServicoRepository repository;

    public ConcluirOrdemServicoUseCase(IOrdemServicoRepository repository) {
        this.repository = repository;
    }

    public void executar(String id) {
        OrdemServico os = repository.buscarPorId(UUID.fromString(id));
        
        if (os == null) {
            throw new IllegalArgumentException("Ordem de serviço não encontrada com ID: " + id);
        }
        
        if (os.getDataFechamento() != null) {
            throw new IllegalArgumentException("Ordem de serviço já foi concluída");
        }
        
        // Atualizar data de fechamento no banco
        repository.concluirOrdemServico(UUID.fromString(id));
    }
}
