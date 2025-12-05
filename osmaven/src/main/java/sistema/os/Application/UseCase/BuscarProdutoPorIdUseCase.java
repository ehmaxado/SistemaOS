package sistema.os.Application.UseCase;

import sistema.os.API.DTOs.Responses.ProdutoResponse;
import sistema.os.domain.Entidades.Produto;
import sistema.os.domain.Interfaces.IProdutoRepository;
import java.util.UUID;

public class BuscarProdutoPorIdUseCase {
    private final IProdutoRepository repository;

    public BuscarProdutoPorIdUseCase(IProdutoRepository repository) {
        this.repository = repository;
    }

    public ProdutoResponse executar(String id) {
        Produto produto = repository.buscarPorId(UUID.fromString(id));
        
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado com ID: " + id);
        }

        return new ProdutoResponse(
            produto.getId().toString(),
            produto.getNome(),
            produto.getDescricao(),
            produto.getPreco(),
            produto.getEstoque(),
            produto.getDataCadastro()
        );
    }
}
