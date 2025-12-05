package sistema.os.API.Controller;

import sistema.os.API.DTOs.Requests.*;
import sistema.os.API.DTOs.Responses.OrdemServicoResponse;
import sistema.os.Application.UseCase.*;
import java.util.List;

public class OrdemServicoController {

    private final CriarOrdemServicoUseCase criarOrdemServicoUseCase;
    private final BuscarOrdemServicoPorIdUseCase buscarOrdemServicoPorIdUseCase;
    private final ListarOrdensSerivicoUseCase listarOrdensSerivicoUseCase;
    private final EditarOrdemServicoUseCase editarOrdemServicoUseCase;
    private final AtualizarStatusOrdemServicoUseCase atualizarStatusOrdemServicoUseCase;
    private final DeletarOrdemServicoUseCase deletarOrdemServicoUseCase;
    private final AdicionarServicoOrdemServicoUseCase adicionarServicoOrdemServicoUseCase;
    private final RemoverServicoOrdemServicoUseCase removerServicoOrdemServicoUseCase;
    private final AdicionarProdutoOrdemServicoUseCase adicionarProdutoOrdemServicoUseCase;
    private final RemoverProdutoOrdemServicoUseCase removerProdutoOrdemServicoUseCase;

    public OrdemServicoController(
            CriarOrdemServicoUseCase criarOrdemServicoUseCase,
            BuscarOrdemServicoPorIdUseCase buscarOrdemServicoPorIdUseCase,
            ListarOrdensSerivicoUseCase listarOrdensSerivicoUseCase,
            EditarOrdemServicoUseCase editarOrdemServicoUseCase,
            AtualizarStatusOrdemServicoUseCase atualizarStatusOrdemServicoUseCase,
            DeletarOrdemServicoUseCase deletarOrdemServicoUseCase,
            AdicionarServicoOrdemServicoUseCase adicionarServicoOrdemServicoUseCase,
            RemoverServicoOrdemServicoUseCase removerServicoOrdemServicoUseCase,
            AdicionarProdutoOrdemServicoUseCase adicionarProdutoOrdemServicoUseCase,
            RemoverProdutoOrdemServicoUseCase removerProdutoOrdemServicoUseCase) {
        this.criarOrdemServicoUseCase = criarOrdemServicoUseCase;
        this.buscarOrdemServicoPorIdUseCase = buscarOrdemServicoPorIdUseCase;
        this.listarOrdensSerivicoUseCase = listarOrdensSerivicoUseCase;
        this.editarOrdemServicoUseCase = editarOrdemServicoUseCase;
        this.atualizarStatusOrdemServicoUseCase = atualizarStatusOrdemServicoUseCase;
        this.deletarOrdemServicoUseCase = deletarOrdemServicoUseCase;
        this.adicionarServicoOrdemServicoUseCase = adicionarServicoOrdemServicoUseCase;
        this.removerServicoOrdemServicoUseCase = removerServicoOrdemServicoUseCase;
        this.adicionarProdutoOrdemServicoUseCase = adicionarProdutoOrdemServicoUseCase;
        this.removerProdutoOrdemServicoUseCase = removerProdutoOrdemServicoUseCase;
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

    public OrdemServicoResponse editar(String id, CriarOrdemServicoRequest request) {
        return editarOrdemServicoUseCase.executar(id, request);
    }

    public OrdemServicoResponse mudarStatus(String id, MudarStatusOrdemServicoRequest request) {
        return atualizarStatusOrdemServicoUseCase.executar(id, request);
    }

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
}