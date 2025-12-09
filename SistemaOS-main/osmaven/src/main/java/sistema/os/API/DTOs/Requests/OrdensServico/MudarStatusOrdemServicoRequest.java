package sistema.os.API.DTOs.Requests.OrdensServico;

public record MudarStatusOrdemServicoRequest(
    String status  // ABERTA, EM_PROGRESSO, CONCLUIDA, CANCELADA
) {}
