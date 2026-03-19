package sistema.os.Application.UseCase.Usuarios;

import sistema.os.API.DTOs.Responses.Usuarios.UsuarioResponse;
import sistema.os.API.DTOs.Responses.Usuarios.BuscarUsuarioResponse;
import sistema.os.domain.Entidades.Usuario;
import sistema.os.domain.Interfaces.IUsuarioRepository;

import java.util.UUID;

public class BuscarUsuarioUseCase {
    private final IUsuarioRepository repository;

    public BuscarUsuarioUseCase(IUsuarioRepository repository) {
        this.repository = repository;
    }

    public BuscarUsuarioResponse executar(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do usuário é obrigatório");
        }

        UUID usuarioId;
        try {
            usuarioId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID inválido");
        }

        Usuario usuario = repository.buscarPorId(usuarioId);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        UsuarioResponse usuarioResponse = new UsuarioResponse(
            usuario.getId().toString(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getPerfil(),
            usuario.isAtivo(),
            usuario.getDataCriacao()
        );

        return new BuscarUsuarioResponse(usuarioResponse);
    }
}
