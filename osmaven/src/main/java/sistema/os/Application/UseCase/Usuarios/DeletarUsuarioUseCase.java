package sistema.os.Application.UseCase.Usuarios;

import sistema.os.API.DTOs.Responses.Usuarios.DeletarUsuarioResponse;
import sistema.os.domain.Interfaces.IUsuarioRepository;

import java.util.UUID;

public class DeletarUsuarioUseCase {
    private final IUsuarioRepository repository;

    public DeletarUsuarioUseCase(IUsuarioRepository repository) {
        this.repository = repository;
    }

    public DeletarUsuarioResponse executar(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do usuário é obrigatório");
        }

        UUID usuarioId;
        try {
            usuarioId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID inválido");
        }

        boolean deletado = repository.deletar(usuarioId);

        if (!deletado) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        return new DeletarUsuarioResponse("Usuário deletado com sucesso", id);
    }
}
