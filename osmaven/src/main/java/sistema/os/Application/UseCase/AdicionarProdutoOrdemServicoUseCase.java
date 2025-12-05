package sistema.os.Application.UseCase;

import sistema.os.API.DTOs.Requests.AdicionarProdutoOrdemServicoRequest;
import sistema.os.API.DTOs.Responses.OrdemServicoResponse;
import sistema.os.domain.Entidades.OrdemServico.OrdemServico;
import sistema.os.domain.Entidades.OrdemServico.OrdemServicoProduto;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;
import java.util.UUID;

public class AdicionarProdutoOrdemServicoUseCase {
    private final IOrdemServicoRepository repository;

    public AdicionarProdutoOrdemServicoUseCase(IOrdemServicoRepository repository) {
        this.repository = repository;
    }

    public OrdemServicoResponse executar(String ordemServicoId, AdicionarProdutoOrdemServicoRequest request) {
        UUID osId = UUID.fromString(ordemServicoId);
        UUID produtoId = UUID.fromString(request.produtoId());
        
        OrdemServico os = repository.buscarPorId(osId);
        
        if (os == null) {
            throw new IllegalArgumentException("Ordem de serviço não encontrada com ID: " + ordemServicoId);
        }

        OrdemServicoProduto osp = new OrdemServicoProduto(osId, produtoId, request.valorUnitario(), request.quantidade());
        repository.adicionarProduto(osp);
        os.adicionarProduto(osp);

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
