package sistema.os.API.Controller;

import sistema.os.API.DTOs.Requests.FormaPagamentos.CriarFormaPagamentoRequest;
import sistema.os.API.DTOs.Requests.FormaPagamentos.EditarFormaPagamentoRequest;
import sistema.os.API.DTOs.Responses.FormaPagamentos.BuscarFormaPagamentoResponse;
import sistema.os.API.DTOs.Responses.FormaPagamentos.CriarFormaPagamentoResponse;
import sistema.os.API.DTOs.Responses.FormaPagamentos.DeletarFormaPagamentoResponse;
import sistema.os.API.DTOs.Responses.FormaPagamentos.EditarFormaPagamentoResponse;
import sistema.os.API.DTOs.Responses.FormaPagamentos.ListarFormasPagamentoResponse;
import sistema.os.Application.UseCase.FormaPagamentos.BuscarFormaPagamentoPorIdUseCase;
import sistema.os.Application.UseCase.FormaPagamentos.CriarFormaPagamentoUseCase;
import sistema.os.Application.UseCase.FormaPagamentos.DeletarFormaPagamentoUseCase;
import sistema.os.Application.UseCase.FormaPagamentos.EditarFormaPagamentoUseCase;
import sistema.os.Application.UseCase.FormaPagamentos.ListarFormasPagamentoUseCase;

public class FormaPagamentoController {
    private final CriarFormaPagamentoUseCase criarUseCase;
    private final ListarFormasPagamentoUseCase listarUseCase;
    private final BuscarFormaPagamentoPorIdUseCase buscarPorIdUseCase;
    private final DeletarFormaPagamentoUseCase deletarUseCase;
    private final EditarFormaPagamentoUseCase editarUseCase;

    public FormaPagamentoController(
            CriarFormaPagamentoUseCase criarUseCase,
            ListarFormasPagamentoUseCase listarUseCase,
            BuscarFormaPagamentoPorIdUseCase buscarPorIdUseCase,
            DeletarFormaPagamentoUseCase deletarUseCase,
            EditarFormaPagamentoUseCase editarUseCase) {
        this.criarUseCase = criarUseCase;
        this.listarUseCase = listarUseCase;
        this.buscarPorIdUseCase = buscarPorIdUseCase;
        this.deletarUseCase = deletarUseCase;
        this.editarUseCase = editarUseCase;
    }

    public CriarFormaPagamentoResponse criar(CriarFormaPagamentoRequest request) {
        return criarUseCase.executar(request);
    }

    public ListarFormasPagamentoResponse listar() {
        return listarUseCase.executar();
    }

    public BuscarFormaPagamentoResponse buscarPorId(String id) {
        return buscarPorIdUseCase.executar(id);
    }

    public DeletarFormaPagamentoResponse deletar(String id) {
        return deletarUseCase.executar(id);
    }

    public EditarFormaPagamentoResponse editar(String id, EditarFormaPagamentoRequest request) {
        return editarUseCase.executar(id, request);
    }
}
