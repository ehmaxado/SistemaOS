package sistema.os.API.DTOs.Requests.OrdensServico;

public record AdicionarProdutoOrdemServicoRequest(
    String produtoId,
    int quantidade,
    double valorTotal
) {}
