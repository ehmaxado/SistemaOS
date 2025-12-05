package sistema.os.API.Controller;

import sistema.os.API.DTOs.Requests.CriarServicoRequest;
import sistema.os.API.DTOs.Requests.EditarServicoRequest;
import sistema.os.API.DTOs.Responses.ServicoResponse;
import sistema.os.Application.UseCase.*;
import java.util.List;

public class ServicoController {

    private final CriarServicoUseCase criarServicoUseCase;
    private final BuscarServicoPorIdUseCase buscarServicoPorIdUseCase;
    private final ListarServicosUseCase listarServicosUseCase;
    private final EditarServicoUseCase editarServicoUseCase;
    private final DeletarServicoUseCase deletarServicoUseCase;

    public ServicoController(CriarServicoUseCase criarServicoUseCase,
                           BuscarServicoPorIdUseCase buscarServicoPorIdUseCase,
                           ListarServicosUseCase listarServicosUseCase,
                           EditarServicoUseCase editarServicoUseCase,
                           DeletarServicoUseCase deletarServicoUseCase) {
        this.criarServicoUseCase = criarServicoUseCase;
        this.buscarServicoPorIdUseCase = buscarServicoPorIdUseCase;
        this.listarServicosUseCase = listarServicosUseCase;
        this.editarServicoUseCase = editarServicoUseCase;
        this.deletarServicoUseCase = deletarServicoUseCase;
    }

    public ServicoResponse criar(CriarServicoRequest request) {
        return criarServicoUseCase.executar(request);
    }

    public ServicoResponse buscarPorId(String id) {
        return buscarServicoPorIdUseCase.executar(id);
    }

    public List<ServicoResponse> listar() {
        return listarServicosUseCase.executar();
    }

    public ServicoResponse editar(String id, EditarServicoRequest request) {
        return editarServicoUseCase.executar(id, request);
    }

    public void deletar(String id) {
        deletarServicoUseCase.executar(id);
    }
}
