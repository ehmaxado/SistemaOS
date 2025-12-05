package sistema.os.API.DTOs.Requests;

public record CriarProdutoRequest(
    String nome,
    String descricao,
    double preco,
    int estoque
) {}
