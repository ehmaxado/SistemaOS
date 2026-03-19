package sistema.os.API.Routes;

import io.javalin.Javalin;
import sistema.os.API.Controller.AutenticacaoController;
import sistema.os.API.DTOs.Requests.Usuarios.LoginRequest;
import sistema.os.API.DTOs.Responses.Usuarios.LoginResponse;
import sistema.os.API.DTOs.Responses.ErroResponse;
import sistema.os.Application.UseCase.Usuarios.AutenticarUsuarioUseCase;
import sistema.os.Infraestrutura.persistence.repository.UsuarioRepository;
import sistema.os.domain.Interfaces.IUsuarioRepository;

public class AutenticacaoRoutes {
    private final AutenticacaoController controller;

    public AutenticacaoRoutes() {
        IUsuarioRepository repository = new UsuarioRepository();
        var autenticarUseCase = new AutenticarUsuarioUseCase(repository);
        this.controller = new AutenticacaoController(autenticarUseCase);
    }

    public void register(Javalin app) {
        app.post("/api/login", ctx -> {
            try {
                LoginRequest request = ctx.bodyAsClass(LoginRequest.class);
                System.out.println("[LOGIN] Tentativa de login para email: " + request.getEmail());
                LoginResponse response = controller.login(request);
                System.out.println("[LOGIN] Resultado: " + (response.sucesso ? "SUCESSO" : "FALHA - " + response.mensagem));
                
                if (response.sucesso) {
                    ctx.status(200).json(response);
                } else {
                    ctx.status(401).json(response);
                }

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        // Rota alternativa para compatibilidade
        app.post("/api/auth/login", ctx -> {
            try {
                LoginRequest request = ctx.bodyAsClass(LoginRequest.class);
                System.out.println("[AUTH/LOGIN] Tentativa de login para email: " + request.getEmail());
                LoginResponse response = controller.login(request);
                System.out.println("[AUTH/LOGIN] Resultado: " + (response.sucesso ? "SUCESSO" : "FALHA - " + response.mensagem));
                
                if (response.sucesso) {
                    ctx.status(200).json(response);
                } else {
                    ctx.status(401).json(response);
                }

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });
    }
}
