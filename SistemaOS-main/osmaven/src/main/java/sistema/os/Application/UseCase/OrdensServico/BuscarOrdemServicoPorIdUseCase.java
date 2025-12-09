package sistema.os.Application.UseCase.OrdensServico;

import sistema.os.API.DTOs.Responses.OrdensServico.OrdemServicoResponse;
import sistema.os.domain.Entidades.OrdemServico.OrdemServico;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;
import java.util.UUID;
import java.util.stream.Collectors;

public class BuscarOrdemServicoPorIdUseCase {
    private final IOrdemServicoRepository repository;

    public BuscarOrdemServicoPorIdUseCase(IOrdemServicoRepository repository) {
        this.repository = repository;
    }

    public OrdemServicoResponse executar(String id) {
        OrdemServico os = repository.buscarPorId(UUID.fromString(id));
        
        if (os == null) {
            throw new IllegalArgumentException("Ordem de serviço não encontrada com ID: " + id);
        }

        var servicos = os.getServicos().stream()
            .map(s -> new OrdemServicoResponse.ServicoOSDTO(
                s.getServicoId().toString(),
                s.getDescricao(),
                s.getQuantidade(),
                s.getValorUnitario(),
                s.getValorTotal()
            ))
            .collect(Collectors.toList());
            
        var produtos = os.getProdutos().stream()
            .map(p -> new OrdemServicoResponse.ProdutoOSDTO(
                p.getProdutoId().toString(),
                p.getNome(),
                p.getQuantidade(),
                p.getValorUnitario(),
                p.getValorTotal()
            ))
            .collect(Collectors.toList());

        return new OrdemServicoResponse(
            os.getId().toString(),
            os.getNumeroOS(),
            os.getId_usuario(),
            os.getClienteId(),
            os.getDataAbertura(),
            os.getDataFechamento(),
            os.getValorTotalProdutos(),
            os.getValorTotalServicos(),
            os.getValorTotal(),
            os.getValorTotalFinal(),
            os.getFormaPagamento(),
            os.getObservacaoGeral(),
            servicos,
            produtos
        );
    }
}
