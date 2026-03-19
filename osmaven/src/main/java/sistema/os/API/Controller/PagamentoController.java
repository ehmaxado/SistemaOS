package sistema.os.API.Controller;

import sistema.os.API.DTOs.Requests.Pagamentos.CriarPagamentoRequest;
import sistema.os.API.DTOs.Requests.Pagamentos.EditarPagamentoRequest;
import sistema.os.API.DTOs.Requests.Pagamentos.EditarStatusPagamentoRequest;
import sistema.os.API.DTOs.Responses.Pagamentos.BuscarPagamentoResponse;
import sistema.os.API.DTOs.Responses.Pagamentos.CriarPagamentoResponse;
import sistema.os.API.DTOs.Responses.Pagamentos.DeletarPagamentoResponse;
import sistema.os.API.DTOs.Responses.Pagamentos.EditarPagamentoResponse;
import sistema.os.API.DTOs.Responses.Pagamentos.EditarStatusPagamentoResponse;
import sistema.os.API.DTOs.Responses.Pagamentos.ListarPagamentosResponse;
import sistema.os.Application.UseCase.Pagamentos.BuscarPagamentoPorIdUseCase;
import sistema.os.Application.UseCase.Pagamentos.CriarPagamentoUseCase;
import sistema.os.Application.UseCase.Pagamentos.DeletarPagamentoUseCase;
import sistema.os.Application.UseCase.Pagamentos.EditarPagamentoUseCase;
import sistema.os.Application.UseCase.Pagamentos.EditarStatusPagamentoUseCase;
import sistema.os.Application.UseCase.Pagamentos.ListarPagamentosUseCase;

public class PagamentoController {
    private final CriarPagamentoUseCase criarUseCase;
    private final ListarPagamentosUseCase listarUseCase;
    private final BuscarPagamentoPorIdUseCase buscarPorIdUseCase;
    private final DeletarPagamentoUseCase deletarUseCase;
    private final EditarStatusPagamentoUseCase editarStatusUseCase;
    private final EditarPagamentoUseCase editarPagamentoUseCase;

    public PagamentoController(
            CriarPagamentoUseCase criarUseCase,
            ListarPagamentosUseCase listarUseCase,
            BuscarPagamentoPorIdUseCase buscarPorIdUseCase,
            DeletarPagamentoUseCase deletarUseCase,
            EditarStatusPagamentoUseCase editarStatusUseCase,
            EditarPagamentoUseCase editarPagamentoUseCase) {
        this.criarUseCase = criarUseCase;
        this.listarUseCase = listarUseCase;
        this.buscarPorIdUseCase = buscarPorIdUseCase;
        this.deletarUseCase = deletarUseCase;
        this.editarStatusUseCase = editarStatusUseCase;
        this.editarPagamentoUseCase = editarPagamentoUseCase;
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

    public EditarPagamentoResponse editar(String id, EditarPagamentoRequest request) {
        return editarPagamentoUseCase.executar(id, request);
    }
}
