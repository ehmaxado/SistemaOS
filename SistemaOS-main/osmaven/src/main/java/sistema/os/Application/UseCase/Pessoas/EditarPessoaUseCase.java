package sistema.os.Application.UseCase.Pessoas;

import sistema.os.API.DTOs.Requests.Pessoas.EditarPessoaRequest;
import sistema.os.API.DTOs.Responses.Pessoas.EditarPessoaResponse;
import sistema.os.domain.Entidades.Pessoa;
import sistema.os.domain.Interfaces.IPessoaRepository;

import java.util.UUID;

public class EditarPessoaUseCase {
    private final IPessoaRepository repository;

    public EditarPessoaUseCase(IPessoaRepository repository) {
        this.repository = repository;
    }

    public EditarPessoaResponse executar(String id, EditarPessoaRequest request) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID da pessoa é obrigatório");
        }

        UUID pessoaId;
        try {
            pessoaId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID inválido");
        }

        Pessoa pessoaExistente = repository.buscarPorId(pessoaId);
        if (pessoaExistente == null) {
            throw new IllegalArgumentException("Pessoa não encontrada");
        }

        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (request.getCpfCnpj() == null || request.getCpfCnpj().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF/CNPJ é obrigatório");
        }
        if (request.getTelefone() == null || request.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone é obrigatório");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (request.getTipoPessoa() == null || request.getTipoPessoa().trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo é obrigatório");
        }

        // Criar nova instância com os dados atualizados (entidade imutável)
        Pessoa pessoaAtualizada = new Pessoa(
            pessoaId,
            request.getTipoPessoa(),
            request.getNome(),
            request.getCpfCnpj(),
            request.getTelefone(),
            request.getEmail(),
            request.getCep(),
            request.getLogradouro(),
            request.getNumero(),
            request.getBairro(),
            request.getCidade(),
            request.getUf(),
            pessoaExistente.getStatus(), // Mantém o status atual
            pessoaExistente.getDataCadastro()
        );

        repository.atualizar(pessoaAtualizada);

        return new EditarPessoaResponse(
            "Pessoa atualizada com sucesso",
            pessoaAtualizada.getId().toString(),
            pessoaAtualizada.getNome()
        );
    }
}
