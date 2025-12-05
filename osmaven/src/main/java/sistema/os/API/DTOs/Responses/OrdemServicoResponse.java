package sistema.os.API.DTOs.Responses;

import java.time.LocalDateTime;

public record OrdemServicoResponse(
    String id,
    String pessoaClienteId,
    String pessoaPrestadorId,
    String status,
    LocalDateTime dataCriacao,
    LocalDateTime dataAtualizacao,
    String descricao,
    double valorTotal
) {}
