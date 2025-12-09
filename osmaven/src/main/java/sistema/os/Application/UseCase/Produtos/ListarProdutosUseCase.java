package sistema.os.Application.UseCase.Produtos;

import sistema.os.API.DTOs.Responses.Produtos.ProdutoResponse;
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
                p.getMarca(),
                p.getUnidade(),
                p.getEstoqueAtual(),
                p.getValorCusto(),
                p.getValorVenda(),
                p.isAtivo()
            ))
            .toList();
    }
}
