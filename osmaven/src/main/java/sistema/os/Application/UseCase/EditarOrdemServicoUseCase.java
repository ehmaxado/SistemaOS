package sistema.os.Application.UseCase;

import sistema.os.API.DTOs.Requests.CriarOrdemServicoRequest;
import sistema.os.API.DTOs.Responses.OrdemServicoResponse;
import sistema.os.domain.Entidades.OrdemServico.OrdemServico;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;
import java.util.UUID;

public class EditarOrdemServicoUseCase {
    private final IOrdemServicoRepository repository;

    public EditarOrdemServicoUseCase(IOrdemServicoRepository repository) {
        this.repository = repository;
    }

    public OrdemServicoResponse executar(String id, CriarOrdemServicoRequest request) {
        UUID osId = UUID.fromString(id);
        OrdemServico os = repository.buscarPorId(osId);
        
        if (os == null) {
            throw new IllegalArgumentException("Ordem de serviço não encontrada com ID: " + id);
        }

        // Edita apenas a descrição (cliente e prestador não podem ser alterados após criação)
        OrdemServico osAtualizada = new OrdemServico(
            osId,
            os.getPessoaClienteId(),
            os.getPessoaPrestadorId(),
            os.getStatus(),
            os.getDataCriacao(),
            java.time.LocalDateTime.now(),
            request.descricao()
        );

        repository.editar(osAtualizada);

        return new OrdemServicoResponse(
            osAtualizada.getId().toString(),
            osAtualizada.getPessoaClienteId().toString(),
            osAtualizada.getPessoaPrestadorId().toString(),
            osAtualizada.getStatus().name(),
            osAtualizada.getDataCriacao(),
            osAtualizada.getDataAtualizacao(),
            osAtualizada.getDescricao(),
            osAtualizada.calcularValorTotal()
        );
    }
}
