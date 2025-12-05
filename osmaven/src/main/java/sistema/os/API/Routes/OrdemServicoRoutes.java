package sistema.os.API.Routes;

import io.javalin.Javalin;
import sistema.os.API.Controller.OrdemServicoController;
import sistema.os.API.DTOs.Requests.*;
import sistema.os.API.DTOs.Responses.ErroResponse;
import sistema.os.Application.UseCase.*;
import sistema.os.Infraestrutura.persistence.repository.OrdemServicoRepository;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;

public class OrdemServicoRoutes {

    private final OrdemServicoController controller;

    public OrdemServicoRoutes() {
        IOrdemServicoRepository repository = new OrdemServicoRepository();
        var criarUseCase = new CriarOrdemServicoUseCase(repository);
        var buscarUseCase = new BuscarOrdemServicoPorIdUseCase(repository);
        var listarUseCase = new ListarOrdensSerivicoUseCase(repository);
        var editarUseCase = new EditarOrdemServicoUseCase(repository);
        var atualizarStatusUseCase = new AtualizarStatusOrdemServicoUseCase(repository);
        var deletarUseCase = new DeletarOrdemServicoUseCase(repository);
        var adicionarServicoUseCase = new AdicionarServicoOrdemServicoUseCase(repository);
        var removerServicoUseCase = new RemoverServicoOrdemServicoUseCase(repository);
        var adicionarProdutoUseCase = new AdicionarProdutoOrdemServicoUseCase(repository);
        var removerProdutoUseCase = new RemoverProdutoOrdemServicoUseCase(repository);
        
        this.controller = new OrdemServicoController(
            criarUseCase, buscarUseCase, listarUseCase, editarUseCase, atualizarStatusUseCase,
            deletarUseCase, adicionarServicoUseCase, removerServicoUseCase,
            adicionarProdutoUseCase, removerProdutoUseCase
        );
    }

    public void register(Javalin app) {
        // POST /api/ordens-servico - Criar nova ordem de serviço
        app.post("/api/ordens-servico", ctx -> {
            try {
                CriarOrdemServicoRequest request = ctx.bodyAsClass(CriarOrdemServicoRequest.class);
                var response = controller.criar(request);
                ctx.status(201).json(response);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        // GET /api/ordens-servico - Listar todas as ordens
        app.get("/api/ordens-servico", ctx -> {
            try {
                var response = controller.listar();
                ctx.status(200).json(response);
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        // GET /api/ordens-servico/:id - Buscar ordem por ID
        app.get("/api/ordens-servico/:id", ctx -> {
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

        // PUT /api/ordens-servico/:id - Editar ordem
        app.put("/api/ordens-servico/:id", ctx -> {
            try {
                String id = ctx.pathParam("id");
                CriarOrdemServicoRequest request = ctx.bodyAsClass(CriarOrdemServicoRequest.class);
                var response = controller.editar(id, request);
                ctx.status(200).json(response);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        // PATCH /api/ordens-servico/:id/status - Mudar status da ordem
        app.patch("/api/ordens-servico/:id/status", ctx -> {
            try {
                String id = ctx.pathParam("id");
                MudarStatusOrdemServicoRequest request = ctx.bodyAsClass(MudarStatusOrdemServicoRequest.class);
                var response = controller.mudarStatus(id, request);
                ctx.status(200).json(response);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        // DELETE /api/ordens-servico/:id - Deletar ordem
        app.delete("/api/ordens-servico/:id", ctx -> {
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

        // POST /api/ordens-servico/:id/servicos - Adicionar serviço à ordem
        app.post("/api/ordens-servico/:id/servicos", ctx -> {
            try {
                String id = ctx.pathParam("id");
                AdicionarServicoOrdemServicoRequest request = ctx.bodyAsClass(AdicionarServicoOrdemServicoRequest.class);
                var response = controller.adicionarServico(id, request);
                ctx.status(201).json(response);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        // DELETE /api/ordens-servico/:id/servicos/:servicoId - Remover serviço da ordem
        app.delete("/api/ordens-servico/:id/servicos/:servicoId", ctx -> {
            try {
                String id = ctx.pathParam("id");
                String servicoId = ctx.pathParam("servicoId");
                var response = controller.removerServico(id, servicoId);
                ctx.status(200).json(response);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        // POST /api/ordens-servico/:id/produtos - Adicionar produto à ordem
        app.post("/api/ordens-servico/:id/produtos", ctx -> {
            try {
                String id = ctx.pathParam("id");
                AdicionarProdutoOrdemServicoRequest request = ctx.bodyAsClass(AdicionarProdutoOrdemServicoRequest.class);
                var response = controller.adicionarProduto(id, request);
                ctx.status(201).json(response);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        // DELETE /api/ordens-servico/:id/produtos/:produtoId - Remover produto da ordem
        app.delete("/api/ordens-servico/:id/produtos/:produtoId", ctx -> {
            try {
                String id = ctx.pathParam("id");
                String produtoId = ctx.pathParam("produtoId");
                var response = controller.removerProduto(id, produtoId);
                ctx.status(200).json(response);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });
    }
}
