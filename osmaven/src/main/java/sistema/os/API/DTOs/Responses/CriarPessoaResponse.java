package sistema.os.API.DTOs.Responses;

import java.time.LocalDateTime;

public record CriarPessoaResponse(
    String id,
    String nome,
    String cpfCnpj,
    String telefone,
    String tipo,
    String status,
    LocalDateTime dataCadastro
) {}