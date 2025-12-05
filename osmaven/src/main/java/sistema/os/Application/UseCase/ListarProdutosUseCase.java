package sistema.os.Application.UseCase;

import sistema.os.API.DTOs.Responses.ProdutoResponse;
import sistema.os.domain.Interfaces.IProdutoRepository;
import java.util.List;

public class ListarProdutosUseCase {
    private final IProdutoRepository repository;

    public ListarProdutosUseCase(IProdutoRepository repository) {
        this.repository = repository;
    }

    public List<ProdutoResponse> executar() {
        return repository.listarTodos()
            .stream()
            .map(p -> new ProdutoResponse(
                p.getId().toString(),
                p.getNome(),
                p.getDescricao(),
                p.getPreco(),
                p.getEstoque(),
                p.getDataCadastro()
            ))
            .toList();
    }
}
