package sistema.os.Application.UseCase.OrdensServico;

import sistema.os.API.DTOs.Responses.OrdensServico.OrdemServicoResponse;
import sistema.os.domain.Entidades.OrdemServico.OrdemServico;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;
import java.util.UUID;

public class RemoverServicoOrdemServicoUseCase {
    private final IOrdemServicoRepository repository;

    public RemoverServicoOrdemServicoUseCase(IOrdemServicoRepository repository) {
        this.repository = repository;
    }

    public OrdemServicoResponse executar(String ordemServicoId, String servicoId) {
        UUID osId = UUID.fromString(ordemServicoId);
        UUID sId = UUID.fromString(servicoId);
        
        OrdemServico os = repository.buscarPorId(osId);
        
        if (os == null) {
            throw new IllegalArgumentException("Ordem de serviço não encontrada com ID: " + ordemServicoId);
        }

        repository.removerServico(osId, sId);
        os.removerServico(sId);

        // Recarregar servicos e produtos do banco para retornar dados completos
        var servicos = repository.listarServicos(osId).stream()
            .map(s -> new OrdemServicoResponse.ServicoOSDTO(
                s.getServicoId().toString(),
                s.getDescricao(),
                s.getQuantidade(),
                s.getValorUnitario(),
                s.getValorTotal()
            ))
            .toList();

        var produtos = repository.listarProdutos(osId).stream()
            .map(p -> new OrdemServicoResponse.ProdutoOSDTO(
                p.getProdutoId().toString(),
                p.getNome(),
                p.getQuantidade(),
                p.getValorUnitario(),
                p.getValorTotal()
            ))
            .toList();

        return new OrdemServicoResponse(
            os.getId().toString(),
            os.getNumeroOS(),
            os.getId_usuario() != null ? os.getId_usuario().toString() : null,
            os.getClienteId().toString(),
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
