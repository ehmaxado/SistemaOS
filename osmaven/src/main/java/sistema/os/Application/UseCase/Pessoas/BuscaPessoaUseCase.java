package sistema.os.Application.UseCase.Pessoas;

import java.util.UUID;

import sistema.os.API.DTOs.Responses.Pessoas.BuscarPessoaResponse;
import sistema.os.domain.Entidades.Pessoa;
import sistema.os.domain.Interfaces.IPessoaRepository;

public class BuscaPessoaUseCase {
    private final IPessoaRepository repository;

    public BuscaPessoaUseCase(IPessoaRepository repository) {
        this.repository = repository;
    }

    public BuscarPessoaResponse executar(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID é obrigatório");
        }

        UUID pessoaId;
        try {
            pessoaId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID inválido");
        }

        Pessoa pessoa = repository.buscarPorId(pessoaId);

        if (pessoa == null) {
            throw new IllegalArgumentException("Pessoa não encontrada");
        }

        return new BuscarPessoaResponse(
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
