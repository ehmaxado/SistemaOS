package sistema.os.API.DTOs.Requests;

public record EditarProdutoRequest(
    String nome,
    String descricao,
    double preco,
    int estoque
) {}
