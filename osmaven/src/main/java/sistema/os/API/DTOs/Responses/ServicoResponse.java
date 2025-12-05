package sistema.os.API.DTOs.Responses;

import java.time.LocalDateTime;

public record ServicoResponse(
    String id,
    String nome,
    String descricao,
    double preco,
    LocalDateTime dataCadastro
) {}
