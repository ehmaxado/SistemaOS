package sistema.os.API.Routes;

import io.javalin.Javalin;
import sistema.os.API.Controller.PessoaController;
import sistema.os.API.DTOs.Requests.Pessoas.CriarPessoaRequest;
import sistema.os.API.DTOs.Requests.Pessoas.EditarPessoaRequest;
import sistema.os.API.DTOs.Responses.ErroResponse;
import sistema.os.API.DTOs.Responses.Pessoas.BuscarPessoaResponse;
import sistema.os.API.DTOs.Responses.Pessoas.CriarPessoaResponse;
import sistema.os.API.DTOs.Responses.Pessoas.DeletarPessoaResponse;
import sistema.os.API.DTOs.Responses.Pessoas.EditarPessoaResponse;
import sistema.os.API.DTOs.Responses.Pessoas.ListarPessoasResponse;
import sistema.os.Application.UseCase.Pessoas.BuscaPessoaUseCase;
import sistema.os.Application.UseCase.Pessoas.BuscarTodasPessoasUseCase;
import sistema.os.Application.UseCase.Pessoas.CriarPessoaUseCase;
import sistema.os.Application.UseCase.Pessoas.DeletarPessoaUseCase;
import sistema.os.Application.UseCase.Pessoas.EditarPessoaUseCase;
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
        var editarUseCase = new EditarPessoaUseCase(repository);
        this.controller = new PessoaController(criarUseCase, buscarUseCase, buscarPorIdUseCase, deletarUseCase, editarUseCase);
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
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno: " + e.getMessage()));
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

        app.get("/api/pessoas/{id}", ctx -> {
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

        app.put("/api/pessoas/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                EditarPessoaRequest request = ctx.bodyAsClass(EditarPessoaRequest.class);
                EditarPessoaResponse response = controller.editar(id, request);
                ctx.status(200).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro ao editar pessoa: " + e.getMessage()));
            }
        });

        app.delete("/api/pessoas/{id}", ctx -> {
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