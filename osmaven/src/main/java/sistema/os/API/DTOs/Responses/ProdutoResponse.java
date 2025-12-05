package sistema.os.API.DTOs.Responses;

import java.time.LocalDateTime;

public record ProdutoResponse(
    String id,
    String nome,
    String descricao,
    double preco,
    int estoque,
    LocalDateTime dataCadastro
) {}
