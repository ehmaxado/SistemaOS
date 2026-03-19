package sistema.os.Application.UseCase.Produtos;

import sistema.os.API.DTOs.Requests.Produtos.CriarProdutoRequest;
import sistema.os.API.DTOs.Responses.Produtos.ProdutoResponse;
import sistema.os.domain.Entidades.Produto;
import sistema.os.domain.Interfaces.IProdutoRepository;

public class CriarProdutoUseCase {
    private final IProdutoRepository repository;

    public CriarProdutoUseCase(IProdutoRepository repository) {
        this.repository = repository;
    }

    public ProdutoResponse executar(CriarProdutoRequest request) {
        Produto produto = new Produto(
            request.nome(),
            request.descricao(),
            request.marca(),
            request.unidade(),
            request.estoqueAtual(),
            request.valorCusto(),
            request.valorVenda()
        );

        try {
            repository.salvar(produto);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar produto no banco de dados", e);
        }

        return new ProdutoResponse(
            produto.getId().toString(),
            produto.getNome(),
            produto.getDescricao(),
            produto.getMarca(),
            produto.getUnidade(),
            produto.getEstoqueAtual(),
            produto.getValorCusto(),
            produto.getValorVenda(),
            produto.isAtivo()
        );
    }
}
