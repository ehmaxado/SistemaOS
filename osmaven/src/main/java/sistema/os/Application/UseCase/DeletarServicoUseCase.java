package sistema.os.Application.UseCase;

import sistema.os.domain.Interfaces.IServicoRepository;
import java.util.UUID;

public class DeletarServicoUseCase {
    private final IServicoRepository repository;

    public DeletarServicoUseCase(IServicoRepository repository) {
        this.repository = repository;
    }

    public void executar(String id) {
        UUID servicoId = UUID.fromString(id);
        
        // Verifica se serviço existe
        if (repository.buscarPorId(servicoId) == null) {
            throw new IllegalArgumentException("Serviço não encontrado com ID: " + id);
        }

        repository.deletar(servicoId);
    }
}
