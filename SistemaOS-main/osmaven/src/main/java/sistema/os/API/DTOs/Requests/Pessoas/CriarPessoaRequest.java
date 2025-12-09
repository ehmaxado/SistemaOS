package sistema.os.API.DTOs.Requests.Pessoas;

public record CriarPessoaRequest(
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
    String uf
) {}