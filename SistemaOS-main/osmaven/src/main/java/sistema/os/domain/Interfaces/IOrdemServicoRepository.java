package sistema.os.domain.Interfaces;

import sistema.os.domain.Entidades.OrdemServico.OrdemServico;
import sistema.os.domain.Entidades.OrdemServico.OrdemServicoServico;
import sistema.os.domain.Entidades.OrdemServico.OrdemServicoProduto;
import java.util.List;
import java.util.UUID;

public interface IOrdemServicoRepository {
    void salvar(OrdemServico ordemServico);
    OrdemServico buscarPorId(UUID id);
    List<OrdemServico> listarTodas();
    void editar(OrdemServico ordemServico);
    void deletar(UUID id);
    
    // Métodos para gerenciar serviços em uma ordem
    void adicionarServico(OrdemServicoServico servico);
    void removerServico(UUID ordemServicoId, UUID servicoId);
    List<OrdemServicoServico> listarServicos(UUID ordemServicoId);
    
    // Métodos para gerenciar produtos em uma ordem
    void adicionarProduto(OrdemServicoProduto produto);
    void removerProduto(UUID ordemServicoId, UUID produtoId);
    List<OrdemServicoProduto> listarProdutos(UUID ordemServicoId);
    
    // Método para concluir uma ordem de serviço
    void concluirOrdemServico(UUID ordemServicoId);
}
