package sistema.os.Application.UseCase.Pessoas;

import sistema.os.domain.Entidades.Pessoa;
import sistema.os.domain.Interfaces.IPessoaRepository;
import sistema.os.API.DTOs.Requests.Pessoas.CriarPessoaRequest;
import sistema.os.API.DTOs.Responses.Pessoas.CriarPessoaResponse;

public class CriarPessoaUseCase {
    private final IPessoaRepository repository;

    public CriarPessoaUseCase(IPessoaRepository repository) {
        this.repository = repository;
    }

    // Cria e persiste nova pessoa
    public CriarPessoaResponse executar(CriarPessoaRequest request) {
        Pessoa pessoa = new Pessoa(
            request.tipoPessoa(),
            request.nome(),
            request.cpfCnpj(),
            request.telefone(),
            request.email(),
            request.cep(),
            request.logradouro(),
            request.numero(),
            request.bairro(),
            request.cidade(),
            request.uf()
        );

        try {
            repository.salvar(pessoa);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar pessoa no banco de dados", e);
        }

        return new CriarPessoaResponse(
            pessoa.getId().toString(),
            pessoa.getNome(),
            pessoa.getCpfCnpj(),
            pessoa.getTelefone(),
            pessoa.getTipoPessoa(),
            pessoa.getEmail(),
            pessoa.getCep(),
            pessoa.getLogradouro(),
            pessoa.getNumero(),
            pessoa.getBairro(),
            pessoa.getCidade(),
            pessoa.getUf(),
            pessoa.getDataCadastro()
        );
    }
}