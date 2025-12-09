package sistema.os.API.Controller;

import sistema.os.API.DTOs.Requests.OrdensServico.*;
import sistema.os.API.DTOs.Responses.OrdensServico.OrdemServicoResponse;
import sistema.os.Application.UseCase.OrdensServico.*;
import java.util.List;

public class OrdemServicoController {

    private final CriarOrdemServicoUseCase criarOrdemServicoUseCase;
    private final BuscarOrdemServicoPorIdUseCase buscarOrdemServicoPorIdUseCase;
    private final ListarOrdensSerivicoUseCase listarOrdensSerivicoUseCase;
    // private final EditarOrdemServicoUseCase editarOrdemServicoUseCase; // DEPRECATED
    // private final AtualizarStatusOrdemServicoUseCase atualizarStatusOrdemServicoUseCase; // DEPRECATED
    private final DeletarOrdemServicoUseCase deletarOrdemServicoUseCase;
    private final AdicionarServicoOrdemServicoUseCase adicionarServicoOrdemServicoUseCase;
    private final RemoverServicoOrdemServicoUseCase removerServicoOrdemServicoUseCase;
    private final AdicionarProdutoOrdemServicoUseCase adicionarProdutoOrdemServicoUseCase;
    private final RemoverProdutoOrdemServicoUseCase removerProdutoOrdemServicoUseCase;
    private final ConcluirOrdemServicoUseCase concluirOrdemServicoUseCase;

    public OrdemServicoController(
            CriarOrdemServicoUseCase criarOrdemServicoUseCase,
            BuscarOrdemServicoPorIdUseCase buscarOrdemServicoPorIdUseCase,
            ListarOrdensSerivicoUseCase listarOrdensSerivicoUseCase,
            // EditarOrdemServicoUseCase editarOrdemServicoUseCase, // DEPRECATED
            // AtualizarStatusOrdemServicoUseCase atualizarStatusOrdemServicoUseCase, // DEPRECATED
            DeletarOrdemServicoUseCase deletarOrdemServicoUseCase,
            AdicionarServicoOrdemServicoUseCase adicionarServicoOrdemServicoUseCase,
            RemoverServicoOrdemServicoUseCase removerServicoOrdemServicoUseCase,
            AdicionarProdutoOrdemServicoUseCase adicionarProdutoOrdemServicoUseCase,
            RemoverProdutoOrdemServicoUseCase removerProdutoOrdemServicoUseCase,
            ConcluirOrdemServicoUseCase concluirOrdemServicoUseCase) {
        this.criarOrdemServicoUseCase = criarOrdemServicoUseCase;
        this.buscarOrdemServicoPorIdUseCase = buscarOrdemServicoPorIdUseCase;
        this.listarOrdensSerivicoUseCase = listarOrdensSerivicoUseCase;
        // this.editarOrdemServicoUseCase = editarOrdemServicoUseCase; // DEPRECATED
        // this.atualizarStatusOrdemServicoUseCase = atualizarStatusOrdemServicoUseCase; // DEPRECATED
        this.deletarOrdemServicoUseCase = deletarOrdemServicoUseCase;
        this.adicionarServicoOrdemServicoUseCase = adicionarServicoOrdemServicoUseCase;
        this.removerServicoOrdemServicoUseCase = removerServicoOrdemServicoUseCase;
        this.adicionarProdutoOrdemServicoUseCase = adicionarProdutoOrdemServicoUseCase;
        this.removerProdutoOrdemServicoUseCase = removerProdutoOrdemServicoUseCase;
        this.concluirOrdemServicoUseCase = concluirOrdemServicoUseCase;
    }

    public OrdemServicoResponse criar(CriarOrdemServicoRequest request) {
        return criarOrdemServicoUseCase.executar(request);
    }

    public OrdemServicoResponse buscarPorId(String id) {
        return buscarOrdemServicoPorIdUseCase.executar(id);
    }

    public List<OrdemServicoResponse> listar() {
        return listarOrdensSerivicoUseCase.executar();
    }

    // DEPRECATED: OrdemServico não tem mais status nem edição direta
    // public OrdemServicoResponse editar(String id, CriarOrdemServicoRequest request) {
    //     return editarOrdemServicoUseCase.executar(id, request);
    // }

    // public OrdemServicoResponse mudarStatus(String id, MudarStatusOrdemServicoRequest request) {
    //     return atualizarStatusOrdemServicoUseCase.executar(id, request);
    // }

    public void deletar(String id) {
        deletarOrdemServicoUseCase.executar(id);
    }

    public OrdemServicoResponse adicionarServico(String id, AdicionarServicoOrdemServicoRequest request) {
        return adicionarServicoOrdemServicoUseCase.executar(id, request);
    }

    public OrdemServicoResponse removerServico(String id, String servicoId) {
        return removerServicoOrdemServicoUseCase.executar(id, servicoId);
    }

    public OrdemServicoResponse adicionarProduto(String id, AdicionarProdutoOrdemServicoRequest request) {
        return adicionarProdutoOrdemServicoUseCase.executar(id, request);
    }

    public OrdemServicoResponse removerProduto(String id, String produtoId) {
        return removerProdutoOrdemServicoUseCase.executar(id, produtoId);
    }

    public OrdemServicoResponse concluir(String id) {
        concluirOrdemServicoUseCase.executar(id);
        return buscarOrdemServicoPorIdUseCase.executar(id);
    }
}