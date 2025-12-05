package sistema.os.API.Controller;

import sistema.os.API.DTOs.Requests.CriarProdutoRequest;
import sistema.os.API.DTOs.Requests.EditarProdutoRequest;
import sistema.os.API.DTOs.Responses.ProdutoResponse;
import sistema.os.Application.UseCase.*;
import java.util.List;

public class ProdutoController {

    private final CriarProdutoUseCase criarProdutoUseCase;
    private final BuscarProdutoPorIdUseCase buscarProdutoPorIdUseCase;
    private final ListarProdutosUseCase listarProdutosUseCase;
    private final EditarProdutoUseCase editarProdutoUseCase;
    private final DeletarProdutoUseCase deletarProdutoUseCase;

    public ProdutoController(CriarProdutoUseCase criarProdutoUseCase,
                           BuscarProdutoPorIdUseCase buscarProdutoPorIdUseCase,
                           ListarProdutosUseCase listarProdutosUseCase,
                           EditarProdutoUseCase editarProdutoUseCase,
                           DeletarProdutoUseCase deletarProdutoUseCase) {
        this.criarProdutoUseCase = criarProdutoUseCase;
        this.buscarProdutoPorIdUseCase = buscarProdutoPorIdUseCase;
        this.listarProdutosUseCase = listarProdutosUseCase;
        this.editarProdutoUseCase = editarProdutoUseCase;
        this.deletarProdutoUseCase = deletarProdutoUseCase;
    }

    public ProdutoResponse criar(CriarProdutoRequest request) {
        return criarProdutoUseCase.executar(request);
    }

    public ProdutoResponse buscarPorId(String id) {
        return buscarProdutoPorIdUseCase.executar(id);
    }

    public List<ProdutoResponse> listar() {
        return listarProdutosUseCase.executar();
    }

    public ProdutoResponse editar(String id, EditarProdutoRequest request) {
        return editarProdutoUseCase.executar(id, request);
    }

    public void deletar(String id) {
        deletarProdutoUseCase.executar(id);
    }
}
