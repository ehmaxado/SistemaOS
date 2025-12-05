package sistema.os.API.DTOs.Requests;

public record CriarServicoRequest(
    String nome,
    String descricao,
    double preco
) {}
