package sistema.os.API.Routes;

import io.javalin.Javalin;
import sistema.os.API.Controller.FormaPagamentoController;
import sistema.os.API.DTOs.Requests.FormaPagamentos.CriarFormaPagamentoRequest;
import sistema.os.API.DTOs.Requests.FormaPagamentos.EditarFormaPagamentoRequest;
import sistema.os.API.DTOs.Responses.FormaPagamentos.BuscarFormaPagamentoResponse;
import sistema.os.API.DTOs.Responses.FormaPagamentos.CriarFormaPagamentoResponse;
import sistema.os.API.DTOs.Responses.FormaPagamentos.DeletarFormaPagamentoResponse;
import sistema.os.API.DTOs.Responses.FormaPagamentos.EditarFormaPagamentoResponse;
import sistema.os.API.DTOs.Responses.FormaPagamentos.ListarFormasPagamentoResponse;
import sistema.os.API.DTOs.Responses.ErroResponse;
import sistema.os.Application.UseCase.FormaPagamentos.BuscarFormaPagamentoPorIdUseCase;
import sistema.os.Application.UseCase.FormaPagamentos.CriarFormaPagamentoUseCase;
import sistema.os.Application.UseCase.FormaPagamentos.DeletarFormaPagamentoUseCase;
import sistema.os.Application.UseCase.FormaPagamentos.EditarFormaPagamentoUseCase;
import sistema.os.Application.UseCase.FormaPagamentos.ListarFormasPagamentoUseCase;
import sistema.os.Infraestrutura.persistence.repository.FormaPagamentoRepository;
import sistema.os.domain.Interfaces.IFormaPagamentoRepository;

public class FormaPagamentoRoutes {
    private final FormaPagamentoController controller;

    public FormaPagamentoRoutes() {
        IFormaPagamentoRepository repository = new FormaPagamentoRepository();
        var criarUseCase = new CriarFormaPagamentoUseCase(repository);
        var listarUseCase = new ListarFormasPagamentoUseCase(repository);
        var buscarPorIdUseCase = new BuscarFormaPagamentoPorIdUseCase(repository);
        var deletarUseCase = new DeletarFormaPagamentoUseCase(repository);
        var editarUseCase = new EditarFormaPagamentoUseCase(repository);
        this.controller = new FormaPagamentoController(criarUseCase, listarUseCase, buscarPorIdUseCase, deletarUseCase, editarUseCase);
    }

    public void register(Javalin app) {
        app.post("/api/formas-pagamento", ctx -> {
            try {
                CriarFormaPagamentoRequest request = ctx.bodyAsClass(CriarFormaPagamentoRequest.class);
                CriarFormaPagamentoResponse response = controller.criar(request);
                ctx.status(201).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        app.get("/api/formas-pagamento", ctx -> {
            try {
                ListarFormasPagamentoResponse response = controller.listar();
                ctx.status(200).json(response);

            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro ao listar formas de pagamento"));
            }
        });

        app.get("/api/formas-pagamento/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                BuscarFormaPagamentoResponse response = controller.buscarPorId(id);
                ctx.status(200).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro ao buscar forma de pagamento"));
            }
        });

        app.put("/api/formas-pagamento/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                EditarFormaPagamentoRequest request = ctx.bodyAsClass(EditarFormaPagamentoRequest.class);
                EditarFormaPagamentoResponse response = controller.editar(id, request);
                ctx.status(200).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro ao editar forma de pagamento"));
            }
        });

        app.delete("/api/formas-pagamento/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                DeletarFormaPagamentoResponse response = controller.deletar(id);
                ctx.status(200).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro ao deletar forma de pagamento"));
            }
        });
    }
}
