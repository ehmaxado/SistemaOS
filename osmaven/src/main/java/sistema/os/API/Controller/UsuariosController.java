package sistema.os.API.Controller;

import sistema.os.API.DTOs.Requests.Usuarios.CriarUsuarioRequest;
import sistema.os.API.DTOs.Requests.Usuarios.EditarUsuarioRequest;
import sistema.os.API.DTOs.Responses.Usuarios.UsuarioResponse;
import sistema.os.API.DTOs.Responses.Usuarios.ListarUsuariosResponse;
import sistema.os.API.DTOs.Responses.Usuarios.BuscarUsuarioResponse;
import sistema.os.API.DTOs.Responses.Usuarios.EditarUsuarioResponse;
import sistema.os.API.DTOs.Responses.Usuarios.DeletarUsuarioResponse;
import sistema.os.Application.UseCase.Usuarios.CriarUsuarioUseCase;
import sistema.os.Application.UseCase.Usuarios.ListarUsuariosUseCase;
import sistema.os.Application.UseCase.Usuarios.BuscarUsuarioUseCase;
import sistema.os.Application.UseCase.Usuarios.EditarUsuarioUseCase;
import sistema.os.Application.UseCase.Usuarios.DeletarUsuarioUseCase;

public class UsuariosController {
    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final ListarUsuariosUseCase listarUsuariosUseCase;
    private final BuscarUsuarioUseCase buscarUsuarioUseCase;
    private final EditarUsuarioUseCase editarUsuarioUseCase;
    private final DeletarUsuarioUseCase deletarUsuarioUseCase;

    public UsuariosController(CriarUsuarioUseCase criarUsuarioUseCase, ListarUsuariosUseCase listarUsuariosUseCase, BuscarUsuarioUseCase buscarUsuarioUseCase, EditarUsuarioUseCase editarUsuarioUseCase, DeletarUsuarioUseCase deletarUsuarioUseCase) {
        this.criarUsuarioUseCase = criarUsuarioUseCase;
        this.listarUsuariosUseCase = listarUsuariosUseCase;
        this.buscarUsuarioUseCase = buscarUsuarioUseCase;
        this.editarUsuarioUseCase = editarUsuarioUseCase;
        this.deletarUsuarioUseCase = deletarUsuarioUseCase;
    }

    public UsuarioResponse criar(CriarUsuarioRequest request) {
        return criarUsuarioUseCase.executar(request);
    }

    public ListarUsuariosResponse listar() {
        return listarUsuariosUseCase.executar();
    }

    public BuscarUsuarioResponse buscar(String id) {
        return buscarUsuarioUseCase.executar(id);
    }

    public EditarUsuarioResponse editar(String id, EditarUsuarioRequest request) {
        return editarUsuarioUseCase.executar(id, request);
    }

    public DeletarUsuarioResponse deletar(String id) {
        return deletarUsuarioUseCase.executar(id);
    }
}
