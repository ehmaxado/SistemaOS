package sistema.os.Application.UseCase.Produtos;

import sistema.os.API.DTOs.Requests.Produtos.EditarProdutoRequest;
import sistema.os.API.DTOs.Responses.Produtos.ProdutoResponse;
import sistema.os.domain.Entidades.Produto;
import sistema.os.domain.Interfaces.IProdutoRepository;
import java.util.UUID;

public class EditarProdutoUseCase {
    private final IProdutoRepository repository;

    public EditarProdutoUseCase(IProdutoRepository repository) {
        this.repository = repository;
    }

    public ProdutoResponse executar(String id, EditarProdutoRequest request) {
        UUID produtoId = UUID.fromString(id);
        Produto produto = repository.buscarPorId(produtoId);
        
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado com ID: " + id);
        }

        // Cria novo produto com os dados atualizados
        Produto produtoAtualizado = new Produto(
            produtoId,
            request.nome(),
            request.descricao(),
            request.marca(),
            request.unidade(),
            request.estoqueAtual(),
            request.valorCusto(),
            request.valorVenda(),
            produto.isAtivo()  // Mantém o ativo do produto existente
        );

        repository.editar(produtoAtualizado);

        return new ProdutoResponse(
            produtoAtualizado.getId().toString(),
            produtoAtualizado.getNome(),
            produtoAtualizado.getDescricao(),
            produtoAtualizado.getMarca(),
            produtoAtualizado.getUnidade(),
            produtoAtualizado.getEstoqueAtual(),
            produtoAtualizado.getValorCusto(),
            produtoAtualizado.getValorVenda(),
            produtoAtualizado.isAtivo()
        );
    }
}
