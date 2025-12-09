package sistema.os.Application.UseCase.Produtos;

import sistema.os.domain.Interfaces.IProdutoRepository;
import java.util.UUID;

public class DeletarProdutoUseCase {
    private final IProdutoRepository repository;

    public DeletarProdutoUseCase(IProdutoRepository repository) {
        this.repository = repository;
    }

    public void executar(String id) {
        UUID produtoId = UUID.fromString(id);
        
        // Verifica se produto existe
        if (repository.buscarPorId(produtoId) == null) {
            throw new IllegalArgumentException("Produto não encontrado com ID: " + id);
        }

        repository.deletar(produtoId);
    }
}
