package sistema.os.API.DTOs.Responses.Produtos;

public record ProdutoResponse(
    String id,
    String nome,
    String descricao,
    String marca,
    String unidade,
    int estoqueAtual,
    double valorCusto,
    double valorVenda,
    boolean ativo
) {}
