package sistema.os.Application.UseCase.OrdensServico;

import sistema.os.API.DTOs.Responses.OrdensServico.OrdemServicoResponse;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;
import java.util.List;
import java.util.stream.Collectors;

public class ListarOrdensSerivicoUseCase {
    private final IOrdemServicoRepository repository;

    public ListarOrdensSerivicoUseCase(IOrdemServicoRepository repository) {
        this.repository = repository;
    }

    public List<OrdemServicoResponse> executar() {
        return repository.listarTodas()
            .stream()
            .map(os -> {
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
            })
            .toList();
    }
}
