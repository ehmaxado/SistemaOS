package sistema.os.API.DTOs.Requests;

public record AdicionarProdutoOrdemServicoRequest(
    String produtoId,
    double valorUnitario,
    int quantidade
) {}
