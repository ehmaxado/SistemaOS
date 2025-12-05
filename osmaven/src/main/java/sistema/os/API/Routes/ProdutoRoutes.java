package sistema.os.API.Routes;

import io.javalin.Javalin;
import sistema.os.API.Controller.ProdutoController;
import sistema.os.API.DTOs.Requests.CriarProdutoRequest;
import sistema.os.API.DTOs.Requests.EditarProdutoRequest;
import sistema.os.API.DTOs.Responses.ErroResponse;
import sistema.os.Application.UseCase.*;
import sistema.os.Infraestrutura.persistence.repository.ProdutoRepository;
import sistema.os.domain.Interfaces.IProdutoRepository;

public class ProdutoRoutes {

    private final ProdutoController controller;

    public ProdutoRoutes() {
        IProdutoRepository repository = new ProdutoRepository();
        var criarUseCase = new CriarProdutoUseCase(repository);
        var buscarUseCase = new BuscarProdutoPorIdUseCase(repository);
        var listarUseCase = new ListarProdutosUseCase(repository);
        var editarUseCase = new EditarProdutoUseCase(repository);
        var deletarUseCase = new DeletarProdutoUseCase(repository);
        this.controller = new ProdutoController(criarUseCase, buscarUseCase, listarUseCase, editarUseCase, deletarUseCase);
    }

    public void register(Javalin app) {
        // POST /api/produtos - Criar novo produto
        app.post("/api/produtos", ctx -> {
            try {
                CriarProdutoRequest request = ctx.bodyAsClass(CriarProdutoRequest.class);
                var response = controller.criar(request);
                ctx.status(201).json(response);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        // GET /api/produtos - Listar todos os produtos
        app.get("/api/produtos", ctx -> {
            try {
                var response = controller.listar();
                ctx.status(200).json(response);
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        // GET /api/produtos/:id - Buscar produto por ID
        app.get("/api/produtos/:id", ctx -> {
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

        // PUT /api/produtos/:id - Editar produto
        app.put("/api/produtos/:id", ctx -> {
            try {
                String id = ctx.pathParam("id");
                EditarProdutoRequest request = ctx.bodyAsClass(EditarProdutoRequest.class);
                var response = controller.editar(id, request);
                ctx.status(200).json(response);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        // DELETE /api/produtos/:id - Deletar produto
        app.delete("/api/produtos/:id", ctx -> {
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
