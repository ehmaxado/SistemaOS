package sistema.os.API.Routes;

import io.javalin.Javalin;
import sistema.os.API.Controller.PagamentoController;
import sistema.os.API.DTOs.Requests.CriarPagamentoRequest;
import sistema.os.API.DTOs.Requests.EditarStatusPagamentoRequest;
import sistema.os.API.DTOs.Responses.CriarPagamentoResponse;
import sistema.os.API.DTOs.Responses.BuscarPagamentoResponse;
import sistema.os.API.DTOs.Responses.EditarStatusPagamentoResponse;
import sistema.os.API.DTOs.Responses.DeletarPagamentoResponse;
import sistema.os.API.DTOs.Responses.ListarPagamentosResponse;
import sistema.os.API.DTOs.Responses.ErroResponse;
import sistema.os.Application.UseCase.CriarPagamentoUseCase;
import sistema.os.Application.UseCase.ListarPagamentosUseCase;
import sistema.os.Application.UseCase.BuscarPagamentoPorIdUseCase;
import sistema.os.Application.UseCase.DeletarPagamentoUseCase;
import sistema.os.Application.UseCase.EditarStatusPagamentoUseCase;
import sistema.os.Infraestrutura.persistence.repository.PagamentoRepository;
import sistema.os.domain.Interfaces.IPagamentoRepository;

public class PagamentoRoutes {
    private final PagamentoController controller;

    public PagamentoRoutes() {
        IPagamentoRepository repository = new PagamentoRepository();
        var criarUseCase = new CriarPagamentoUseCase(repository);
        var listarUseCase = new ListarPagamentosUseCase(repository);
        var buscarPorIdUseCase = new BuscarPagamentoPorIdUseCase(repository);
        var deletarUseCase = new DeletarPagamentoUseCase(repository);
        var editarStatusUseCase = new EditarStatusPagamentoUseCase(repository);
        this.controller = new PagamentoController(criarUseCase, listarUseCase, buscarPorIdUseCase, deletarUseCase, editarStatusUseCase);
    }

    public void register(Javalin app) {
        app.post("/api/pagamentos", ctx -> {
            try {
                CriarPagamentoRequest request = ctx.bodyAsClass(CriarPagamentoRequest.class);
                CriarPagamentoResponse response = controller.criar(request);
                ctx.status(201).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        app.get("/api/pagamentos", ctx -> {
            try {
                String status = ctx.queryParam("status");
                ListarPagamentosResponse response;
                
                if (status != null && !status.isEmpty()) {
                    response = controller.listarPorStatus(status);
                } else {
                    response = controller.listar();
                }
                
                ctx.status(200).json(response);

            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro ao listar pagamentos"));
            }
        });

        app.get("/api/pagamentos/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                BuscarPagamentoResponse response = controller.buscarPorId(id);
                ctx.status(200).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro ao buscar pagamento"));
            }
        });

        app.patch("/api/pagamentos/{id}/status", ctx -> {
            try {
                String id = ctx.pathParam("id");
                EditarStatusPagamentoRequest request = ctx.bodyAsClass(EditarStatusPagamentoRequest.class);
                EditarStatusPagamentoResponse response = controller.editarStatus(id, request);
                ctx.status(200).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro ao editar status do pagamento"));
            }
        });

        app.delete("/api/pagamentos/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                DeletarPagamentoResponse response = controller.deletar(id);
                ctx.status(200).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro ao deletar pagamento"));
            }
        });
    }
}
