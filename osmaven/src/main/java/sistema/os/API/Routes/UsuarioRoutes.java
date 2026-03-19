package sistema.os.API.Routes;

import io.javalin.Javalin;
import sistema.os.API.Controller.UsuariosController;
import sistema.os.API.DTOs.Requests.Usuarios.CriarUsuarioRequest;
import sistema.os.API.DTOs.Requests.Usuarios.EditarUsuarioRequest;
import sistema.os.API.DTOs.Responses.Usuarios.UsuarioResponse;
import sistema.os.API.DTOs.Responses.Usuarios.ListarUsuariosResponse;
import sistema.os.API.DTOs.Responses.Usuarios.BuscarUsuarioResponse;
import sistema.os.API.DTOs.Responses.Usuarios.EditarUsuarioResponse;
import sistema.os.API.DTOs.Responses.Usuarios.DeletarUsuarioResponse;
import sistema.os.API.DTOs.Responses.ErroResponse;
import sistema.os.Application.UseCase.Usuarios.CriarUsuarioUseCase;
import sistema.os.Application.UseCase.Usuarios.ListarUsuariosUseCase;
import sistema.os.Application.UseCase.Usuarios.BuscarUsuarioUseCase;
import sistema.os.Application.UseCase.Usuarios.EditarUsuarioUseCase;
import sistema.os.Application.UseCase.Usuarios.DeletarUsuarioUseCase;
import sistema.os.Infraestrutura.persistence.repository.UsuarioRepository;
import sistema.os.domain.Interfaces.IUsuarioRepository;

public class UsuarioRoutes {
    private final UsuariosController controller;

    public UsuarioRoutes() {
        IUsuarioRepository repository = new UsuarioRepository();
        var criarUseCase = new CriarUsuarioUseCase(repository);
        var listarUseCase = new ListarUsuariosUseCase(repository);
        var buscarUseCase = new BuscarUsuarioUseCase(repository);
        var editarUseCase = new EditarUsuarioUseCase(repository);
        var deletarUseCase = new DeletarUsuarioUseCase(repository);
        this.controller = new UsuariosController(criarUseCase, listarUseCase, buscarUseCase, editarUseCase, deletarUseCase);
    }

    public void register(Javalin app) {
        app.post("/api/usuarios", ctx -> {
            try {
                CriarUsuarioRequest request = ctx.bodyAsClass(CriarUsuarioRequest.class);
                UsuarioResponse response = controller.criar(request);
                ctx.status(201).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });

        app.get("/api/usuarios", ctx -> {
            try {
                ListarUsuariosResponse response = controller.listar();
                ctx.status(200).json(response);

            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro ao listar usuários"));
            }
        });

        app.get("/api/usuarios/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                BuscarUsuarioResponse response = controller.buscar(id);
                ctx.status(200).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro ao buscar usuário"));
            }
        });

        app.put("/api/usuarios/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                EditarUsuarioRequest request = ctx.bodyAsClass(EditarUsuarioRequest.class);
                EditarUsuarioResponse response = controller.editar(id, request);
                ctx.status(200).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro ao editar usuário"));
            }
        });

        app.delete("/api/usuarios/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                DeletarUsuarioResponse response = controller.deletar(id);
                ctx.status(200).json(response);

            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro ao deletar usuário"));
            }
        });
    }
}
