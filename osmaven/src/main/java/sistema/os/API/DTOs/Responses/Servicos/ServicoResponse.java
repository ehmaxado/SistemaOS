package sistema.os.API.DTOs.Responses.Servicos;

public record ServicoResponse(
    String id,
    String descricao,
    String codigo,
    double valorPadrao,
    int tempoEstimadoMinutos,
    boolean ativo
) {}
