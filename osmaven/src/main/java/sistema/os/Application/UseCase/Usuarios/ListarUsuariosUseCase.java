package sistema.os.Application.UseCase.Usuarios;

import sistema.os.API.DTOs.Responses.Usuarios.ListarUsuariosResponse;
import sistema.os.API.DTOs.Responses.Usuarios.UsuarioResponse;
import sistema.os.domain.Entidades.Usuario;
import sistema.os.domain.Interfaces.IUsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ListarUsuariosUseCase {
    private final IUsuarioRepository repository;

    public ListarUsuariosUseCase(IUsuarioRepository repository) {
        this.repository = repository;
    }

    public ListarUsuariosResponse executar() {
        List<Usuario> usuarios = repository.buscarTodos();

        List<UsuarioResponse> usuariosResponse = usuarios.stream()
            .map(u -> new UsuarioResponse(
                u.getId().toString(),
                u.getNome(),
                u.getEmail(),
                u.getPerfil(),
                u.isAtivo(),
                u.getDataCriacao()
            ))
            .collect(Collectors.toList());

        return new ListarUsuariosResponse(usuariosResponse);
    }
}
