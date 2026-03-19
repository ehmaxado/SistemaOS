package sistema.os.API.Routes;

import io.javalin.Javalin;
import sistema.os.API.Controller.ServicoController;
import sistema.os.API.DTOs.Requests.Servicos.CriarServicoRequest;
import sistema.os.API.DTOs.Requests.Servicos.EditarServicoRequest;
import sistema.os.API.DTOs.Responses.ErroResponse;
import sistema.os.Application.UseCase.Servicos.*;
import sistema.os.Infraestrutura.persistence.repository.ServicoRepository;
import sistema.os.domain.Interfaces.IServicoRepository;

public class ServicoRoutes {

    private final ServicoController controller;

    public ServicoRoutes() {
        IServicoRepository repository = new ServicoRepository();
        var criarUseCase = new CriarServicoUseCase(repository);
        var buscarUseCase = new BuscarServicoPorIdUseCase(repository);
        var listarUseCase = new ListarServicosUseCase(repository);
        var editarUseCase = new EditarServicoUseCase(repository);
        var deletarUseCase = new DeletarServicoUseCase(repository);
        this.controller = new ServicoController(criarUseCase, buscarUseCase, listarUseCase, editarUseCase, deletarUseCase);
    }

    public void register(Javalin app) {
        // POST /api/servicos - Criar novo serviço
        app.post("/api/servicos", ctx -> {
            try {
                CriarServicoRequest request = ctx.bodyAsClass(CriarServicoRequest.class);
                var response = controller.criar(request);
                ctx.status(201).json(response);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        // GET /api/servicos - Listar todos os serviços
        app.get("/api/servicos", ctx -> {
            try {
                var response = controller.listar();
                ctx.status(200).json(response);
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        // GET /api/servicos/{id} - Buscar serviço por ID
        app.get("/api/servicos/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                var response = controller.buscarPorId(id);
                ctx.status(200).json(response);
            } catch (IllegalArgumentException e) {
                ctx.status(404).json(new ErroResponse("NAO_ENCONTRADO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        // PUT /api/servicos/{id} - Editar serviço
        app.put("/api/servicos/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                EditarServicoRequest request = ctx.bodyAsClass(EditarServicoRequest.class);
                var response = controller.editar(id, request);
                ctx.status(200).json(response);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        // DELETE /api/servicos/{id} - Deletar serviço
        app.delete("/api/servicos/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                controller.deletar(id);
                ctx.status(204);
            } catch (IllegalArgumentException e) {
                ctx.status(404).json(new ErroResponse("NAO_ENCONTRADO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });
    }
}
