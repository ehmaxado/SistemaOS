package sistema.os.API.DTOs.Requests.OrdensServico;

public record CriarOrdemServicoRequest(
    String numeroOS,
    String id_usuario,
    String clienteId,
    String formaPagamento,
    String observacaoGeral
) {}
