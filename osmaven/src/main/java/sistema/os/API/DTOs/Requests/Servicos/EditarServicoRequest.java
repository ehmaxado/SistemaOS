package sistema.os.API.DTOs.Requests.Servicos;

public record EditarServicoRequest(
    String codigo,
    String descricao,
    double valorPadrao,
    int tempoEstimadoMinutos,
    boolean ativo
) {}
