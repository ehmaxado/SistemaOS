package sistema.os.Application.UseCase.Usuarios;

import sistema.os.API.DTOs.Requests.Usuarios.LoginRequest;
import sistema.os.API.DTOs.Responses.Usuarios.LoginResponse;
import sistema.os.API.DTOs.Responses.Usuarios.UsuarioResponse;
import sistema.os.domain.Entidades.Usuario;
import sistema.os.domain.Interfaces.IUsuarioRepository;

public class AutenticarUsuarioUseCase {
    private final IUsuarioRepository repository;

    public AutenticarUsuarioUseCase(IUsuarioRepository repository) {
        this.repository = repository;
    }

    public LoginResponse executar(LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (request.getSenha() == null || request.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }

        Usuario usuario = repository.buscarPorEmail(request.getEmail());

        if (usuario == null) {
            return new LoginResponse(false, "Usuário não encontrado", null);
        }

        if (!usuario.isAtivo()) {
            return new LoginResponse(false, "Usuário inativo", null);
        }

        if (!usuario.verificarSenha(request.getSenha())) {
            return new LoginResponse(false, "Senha incorreta", null);
        }

        UsuarioResponse usuarioResponse = new UsuarioResponse(
            usuario.getId().toString(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getPerfil(),
            usuario.isAtivo(),
            usuario.getDataCriacao()
        );

        return new LoginResponse(true, "Login realizado com sucesso", usuarioResponse);
    }
}
