package sistema.os.Application.UseCase;

import sistema.os.API.DTOs.Requests.AdicionarServicoOrdemServicoRequest;
import sistema.os.API.DTOs.Responses.OrdemServicoResponse;
import sistema.os.domain.Entidades.OrdemServico.OrdemServico;
import sistema.os.domain.Entidades.OrdemServico.OrdemServicoServico;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;
import java.util.UUID;

public class AdicionarServicoOrdemServicoUseCase {
    private final IOrdemServicoRepository repository;

    public AdicionarServicoOrdemServicoUseCase(IOrdemServicoRepository repository) {
        this.repository = repository;
    }

    public OrdemServicoResponse executar(String ordemServicoId, AdicionarServicoOrdemServicoRequest request) {
        UUID osId = UUID.fromString(ordemServicoId);
        UUID servicoId = UUID.fromString(request.servicoId());
        
        OrdemServico os = repository.buscarPorId(osId);
        
        if (os == null) {
            throw new IllegalArgumentException("Ordem de serviço não encontrada com ID: " + ordemServicoId);
        }

        OrdemServicoServico oss = new OrdemServicoServico(osId, servicoId, request.valorUnitario());
        repository.adicionarServico(oss);
        os.adicionarServico(oss);

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
