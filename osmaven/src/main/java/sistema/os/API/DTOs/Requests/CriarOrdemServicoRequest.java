package sistema.os.API.DTOs.Requests;

public record CriarOrdemServicoRequest(
    String pessoaClienteId,
    String pessoaPrestadorId,
    String descricao
) {}
