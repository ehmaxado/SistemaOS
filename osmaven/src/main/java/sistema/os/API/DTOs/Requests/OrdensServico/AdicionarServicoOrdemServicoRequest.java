package sistema.os.API.DTOs.Requests.OrdensServico;

public record AdicionarServicoOrdemServicoRequest(
    String servicoId,
    int quantidade,
    double valorTotal
) {}
