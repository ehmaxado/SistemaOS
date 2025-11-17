package sistema.os.API.Routes;

import io.javalin.Javalin;
import sistema.os.API.Controller.PessoaController;
import sistema.os.API.DTOs.Requests.CriarPessoaRequest;
import sistema.os.API.DTOs.Responses.CriarPessoaResponse;
import sistema.os.API.DTOs.Responses.ErroResponse;
import sistema.os.Application.UseCase.CriarPessoaUseCase;
import sistema.os.Infraestrutura.persistence.repository.PessoaRepository;
import sistema.os.domain.Interfaces.IPessoaRepository;

public class PessoaRoutes {

    private final PessoaController controller;

    // Recebe a implementação real (não a interface!)
    public PessoaRoutes() {
        // AQUI É O SEGREDO: instanciar a CLASSE que implementa a interface
        IPessoaRepository repository = new PessoaRepository();  // ← CERTO
        var useCase = new CriarPessoaUseCase(repository);
        this.controller = new PessoaController(useCase);
    }

    public void register(Javalin app) {
        app.post("/api/pessoas", ctx -> {
            try {
                CriarPessoaRequest request = ctx.bodyAsClass(CriarPessoaRequest.class);
                CriarPessoaResponse response = controller.criar(request);
                ctx.status(201).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });
    }
}