package sistema.os.API.DTOs.Requests;

public record MudarStatusOrdemServicoRequest(
    String status  // ABERTA, EM_PROGRESSO, CONCLUIDA, CANCELADA
) {}
