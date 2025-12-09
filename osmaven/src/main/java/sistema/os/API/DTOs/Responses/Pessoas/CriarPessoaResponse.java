package sistema.os.API.DTOs.Responses.Pessoas;

import java.time.LocalDateTime;

public record CriarPessoaResponse(
    String id,
    String nome,
    String cpfCnpj,
    String telefone,
    String tipoPessoa,
    String email,
    String cep,
    String logradouro,
    String numero,
    String bairro,
    String cidade,
    String uf,
    LocalDateTime dataCadastro
) {}