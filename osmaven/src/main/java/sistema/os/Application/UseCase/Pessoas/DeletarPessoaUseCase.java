package sistema.os.Application.UseCase;

import java.util.UUID;

import sistema.os.API.DTOs.Responses.DeletarPessoaResponse;
import sistema.os.domain.Interfaces.IPessoaRepository;

public class DeletarPessoaUseCase {
    private final IPessoaRepository repository;

    public DeletarPessoaUseCase(IPessoaRepository repository) {
        this.repository = repository;
    }

    public DeletarPessoaResponse executar(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID é obrigatório");
        }

        UUID pessoaId;
        try {
            pessoaId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID inválido");
        }

        boolean deletada = repository.deletar(pessoaId);

        if (!deletada) {
            throw new IllegalArgumentException("Pessoa não encontrada");
        }

        return new DeletarPessoaResponse(id, "Pessoa deletada com sucesso", true);
    }
}
