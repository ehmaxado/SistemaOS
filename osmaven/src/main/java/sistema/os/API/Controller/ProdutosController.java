package sistema.os.API.Controller;

import sistema.os.API.DTOs.Requests.Produtos.CriarProdutoRequest;
import sistema.os.API.DTOs.Requests.Produtos.EditarProdutoRequest;
import sistema.os.API.DTOs.Responses.Produtos.ProdutoResponse;
import sistema.os.Application.UseCase.Produtos.*;
import java.util.List;

public class ProdutosController {
    
    private final CriarProdutoUseCase criarUseCase;
    private final BuscarProdutoPorIdUseCase buscarPorIdUseCase;
    private final ListarProdutosUseCase listarUseCase;
    private final EditarProdutoUseCase editarUseCase;
    private final DeletarProdutoUseCase deletarUseCase;

    public ProdutosController(CriarProdutoUseCase criarUseCase, 
                             BuscarProdutoPorIdUseCase buscarPorIdUseCase,
                             ListarProdutosUseCase listarUseCase,
                             EditarProdutoUseCase editarUseCase,
                             DeletarProdutoUseCase deletarUseCase) {
        this.criarUseCase = criarUseCase;
        this.buscarPorIdUseCase = buscarPorIdUseCase;
        this.listarUseCase = listarUseCase;
        this.editarUseCase = editarUseCase;
        this.deletarUseCase = deletarUseCase;
    }

    public ProdutoResponse criar(CriarProdutoRequest request) {
        return criarUseCase.executar(request);
    }

    public ProdutoResponse buscarPorId(String id) {
        return buscarPorIdUseCase.executar(id);
    }

    public List<ProdutoResponse> listar() {
        return listarUseCase.executar();
    }

    public ProdutoResponse editar(String id, EditarProdutoRequest request) {
        return editarUseCase.executar(id, request);
    }

    public void deletar(String id) {
        deletarUseCase.executar(id);
    }
}
