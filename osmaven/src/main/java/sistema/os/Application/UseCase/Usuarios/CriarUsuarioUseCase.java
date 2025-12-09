package sistema.os.Application.UseCase.Usuarios;

import sistema.os.API.DTOs.Requests.Usuarios.CriarUsuarioRequest;
import sistema.os.API.DTOs.Responses.Usuarios.UsuarioResponse;
import sistema.os.domain.Entidades.Usuario;
import sistema.os.domain.Interfaces.IUsuarioRepository;

public class CriarUsuarioUseCase {
    private final IUsuarioRepository repository;

    public CriarUsuarioUseCase(IUsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioResponse executar(CriarUsuarioRequest request) {
        // Verificar se email já existe
        Usuario usuarioExistente = repository.buscarPorEmail(request.getEmail());
        if (usuarioExistente != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Usuario usuario = new Usuario(
            request.getNome(),
            request.getEmail(),
            request.getSenha(),
            request.getPerfil()
        );

        repository.salvar(usuario);

        return new UsuarioResponse(
            usuario.getId().toString(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getPerfil(),
            usuario.isAtivo(),
            usuario.getDataCriacao()
        );
    }
}
