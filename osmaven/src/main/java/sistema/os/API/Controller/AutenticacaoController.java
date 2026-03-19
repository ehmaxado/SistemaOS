package sistema.os.API.Controller;

import sistema.os.API.DTOs.Requests.Usuarios.LoginRequest;
import sistema.os.API.DTOs.Responses.Usuarios.LoginResponse;
import sistema.os.Application.UseCase.Usuarios.AutenticarUsuarioUseCase;

public class AutenticacaoController {
    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;

    public AutenticacaoController(AutenticarUsuarioUseCase autenticarUsuarioUseCase) {
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
    }

    public LoginResponse login(LoginRequest request) {
        return autenticarUsuarioUseCase.executar(request);
    }
}
