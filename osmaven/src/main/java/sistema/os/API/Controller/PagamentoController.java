package sistema.os.API.Controller;

import sistema.os.API.DTOs.Requests.CriarPagamentoRequest;
import sistema.os.API.DTOs.Requests.EditarStatusPagamentoRequest;
import sistema.os.API.DTOs.Responses.CriarPagamentoResponse;
import sistema.os.API.DTOs.Responses.BuscarPagamentoResponse;
import sistema.os.API.DTOs.Responses.EditarStatusPagamentoResponse;
import sistema.os.API.DTOs.Responses.DeletarPagamentoResponse;
import sistema.os.API.DTOs.Responses.ListarPagamentosResponse;
import sistema.os.Application.UseCase.CriarPagamentoUseCase;
import sistema.os.Application.UseCase.ListarPagamentosUseCase;
import sistema.os.Application.UseCase.BuscarPagamentoPorIdUseCase;
import sistema.os.Application.UseCase.DeletarPagamentoUseCase;
import sistema.os.Application.UseCase.EditarStatusPagamentoUseCase;

public class PagamentoController {
    private final CriarPagamentoUseCase criarUseCase;
    private final ListarPagamentosUseCase listarUseCase;
    private final BuscarPagamentoPorIdUseCase buscarPorIdUseCase;
    private final DeletarPagamentoUseCase deletarUseCase;
    private final EditarStatusPagamentoUseCase editarStatusUseCase;

    public PagamentoController(
            CriarPagamentoUseCase criarUseCase,
            ListarPagamentosUseCase listarUseCase,
            BuscarPagamentoPorIdUseCase buscarPorIdUseCase,
            DeletarPagamentoUseCase deletarUseCase,
            EditarStatusPagamentoUseCase editarStatusUseCase) {
        this.criarUseCase = criarUseCase;
        this.listarUseCase = listarUseCase;
        this.buscarPorIdUseCase = buscarPorIdUseCase;
        this.deletarUseCase = deletarUseCase;
        this.editarStatusUseCase = editarStatusUseCase;
    }

    public CriarPagamentoResponse criar(CriarPagamentoRequest request) {
        return criarUseCase.executar(request);
    }

    public ListarPagamentosResponse listar() {
        return listarUseCase.executar();
    }

    public ListarPagamentosResponse listarPorStatus(String status) {
        return listarUseCase.executarPorStatus(status);
    }

    public BuscarPagamentoResponse buscarPorId(String id) {
        return buscarPorIdUseCase.executar(id);
    }

    public DeletarPagamentoResponse deletar(String id) {
        return deletarUseCase.executar(id);
    }

    public EditarStatusPagamentoResponse editarStatus(String id, EditarStatusPagamentoRequest request) {
        return editarStatusUseCase.executar(id, request);
    }
}
