package sistema.os.Application.UseCase;

import sistema.os.API.DTOs.Requests.CriarOrdemServicoRequest;
import sistema.os.API.DTOs.Responses.OrdemServicoResponse;
import sistema.os.domain.Entidades.OrdemServico.OrdemServico;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;
import java.util.UUID;

public class CriarOrdemServicoUseCase {
    private final IOrdemServicoRepository repository;

    public CriarOrdemServicoUseCase(IOrdemServicoRepository repository) {
        this.repository = repository;
    }

    public OrdemServicoResponse executar(CriarOrdemServicoRequest request) {
        UUID clienteId = UUID.fromString(request.pessoaClienteId());
        UUID prestadorId = UUID.fromString(request.pessoaPrestadorId());
        
        OrdemServico os = new OrdemServico(clienteId, prestadorId, request.descricao());

        try {
            repository.salvar(os);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar ordem de serviço", e);
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
