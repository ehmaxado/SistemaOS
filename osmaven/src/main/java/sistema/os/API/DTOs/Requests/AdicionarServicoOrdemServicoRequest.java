package sistema.os.API.DTOs.Requests;

public record AdicionarServicoOrdemServicoRequest(
    String servicoId,
    double valorUnitario
) {}
