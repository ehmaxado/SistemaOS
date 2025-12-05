package sistema.os.Application.UseCase;

import sistema.os.API.DTOs.Responses.OrdemServicoResponse;
import sistema.os.domain.Entidades.OrdemServico.OrdemServico;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;
import java.util.UUID;

public class RemoverProdutoOrdemServicoUseCase {
    private final IOrdemServicoRepository repository;

    public RemoverProdutoOrdemServicoUseCase(IOrdemServicoRepository repository) {
        this.repository = repository;
    }

    public OrdemServicoResponse executar(String ordemServicoId, String produtoId) {
        UUID osId = UUID.fromString(ordemServicoId);
        UUID pId = UUID.fromString(produtoId);
        
        OrdemServico os = repository.buscarPorId(osId);
        
        if (os == null) {
            throw new IllegalArgumentException("Ordem de serviço não encontrada com ID: " + ordemServicoId);
        }

        repository.removerProduto(osId, pId);
        os.removerProduto(pId);

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
