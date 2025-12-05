package sistema.os.API.DTOs.Requests;

public record CriarPessoaRequest(
    String nome,
    String cpfCnpj,
    String telefone,
    String tipo  // "CLIENTE" ou "PRESTADOR"
) {}