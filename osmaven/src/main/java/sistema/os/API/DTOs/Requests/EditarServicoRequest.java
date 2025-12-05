package sistema.os.API.DTOs.Requests;

public record EditarServicoRequest(
    String nome,
    String descricao,
    double preco
) {}
