package sistema.os.API.Routes;

import io.javalin.Javalin;
import sistema.os.API.Controller.PessoaController;
import sistema.os.API.DTOs.Requests.CriarPessoaRequest;
import sistema.os.API.DTOs.Responses.CriarPessoaResponse;
import sistema.os.API.DTOs.Responses.BuscarPessoaResponse;
import sistema.os.API.DTOs.Responses.DeletarPessoaResponse;
import sistema.os.API.DTOs.Responses.ErroResponse;
import sistema.os.API.DTOs.Responses.ListarPessoasResponse;
import sistema.os.Application.UseCase.CriarPessoaUseCase;
import sistema.os.Application.UseCase.BuscarTodasPessoasUseCase;
import sistema.os.Application.UseCase.BuscaPessoaUseCase;
import sistema.os.Application.UseCase.DeletarPessoaUseCase;
import sistema.os.Infraestrutura.persistence.repository.PessoaRepository;
import sistema.os.domain.Interfaces.IPessoaRepository;

public class PessoaRoutes {

    private final PessoaController controller;

    public PessoaRoutes() {
        IPessoaRepository repository = new PessoaRepository();
        var criarUseCase = new CriarPessoaUseCase(repository);
        var buscarUseCase = new BuscarTodasPessoasUseCase(repository);
        var buscarPorIdUseCase = new BuscaPessoaUseCase(repository);
        var deletarUseCase = new DeletarPessoaUseCase(repository);
        this.controller = new PessoaController(criarUseCase, buscarUseCase, buscarPorIdUseCase, deletarUseCase);
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

        app.get("/api/pessoas", ctx -> {
            try {
                ListarPessoasResponse response = controller.listar();
                ctx.status(200).json(response);

            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro ao listar pessoas"));
            }
        });

        app.get("/api/pessoas/:id", ctx -> {
            try {
                String id = ctx.pathParam("id");
                BuscarPessoaResponse response = controller.buscar(id);
                ctx.status(200).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro ao buscar pessoa"));
            }
        });

        app.delete("/api/pessoas/:id", ctx -> {
            try {
                String id = ctx.pathParam("id");
                DeletarPessoaResponse response = controller.deletar(id);
                ctx.status(200).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro ao deletar pessoa"));
            }
        });
    }
}