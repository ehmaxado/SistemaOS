package sistema.os.API.Controller;

import sistema.os.API.DTOs.Requests.Pessoas.CriarPessoaRequest;
import sistema.os.API.DTOs.Requests.Pessoas.EditarPessoaRequest;
import sistema.os.API.DTOs.Responses.Pessoas.BuscarPessoaResponse;
import sistema.os.API.DTOs.Responses.Pessoas.CriarPessoaResponse;
import sistema.os.API.DTOs.Responses.Pessoas.DeletarPessoaResponse;
import sistema.os.API.DTOs.Responses.Pessoas.EditarPessoaResponse;
import sistema.os.API.DTOs.Responses.Pessoas.ListarPessoasResponse;
import sistema.os.Application.UseCase.Pessoas.BuscaPessoaUseCase;
import sistema.os.Application.UseCase.Pessoas.BuscarTodasPessoasUseCase;
import sistema.os.Application.UseCase.Pessoas.CriarPessoaUseCase;
import sistema.os.Application.UseCase.Pessoas.DeletarPessoaUseCase;
import sistema.os.Application.UseCase.Pessoas.EditarPessoaUseCase;

public class PessoaController {

    private final CriarPessoaUseCase criarPessoaUseCase;
    private final BuscarTodasPessoasUseCase buscarTodasPessoasUseCase;
    private final BuscaPessoaUseCase buscaPessoaUseCase;
    private final DeletarPessoaUseCase deletarPessoaUseCase;
    private final EditarPessoaUseCase editarPessoaUseCase;

    public PessoaController(CriarPessoaUseCase criarPessoaUseCase, BuscarTodasPessoasUseCase buscarTodasPessoasUseCase, BuscaPessoaUseCase buscaPessoaUseCase, DeletarPessoaUseCase deletarPessoaUseCase, EditarPessoaUseCase editarPessoaUseCase) {
        this.criarPessoaUseCase = criarPessoaUseCase;
        this.buscarTodasPessoasUseCase = buscarTodasPessoasUseCase;
        this.buscaPessoaUseCase = buscaPessoaUseCase;
        this.deletarPessoaUseCase = deletarPessoaUseCase;
        this.editarPessoaUseCase = editarPessoaUseCase;
    }

    public CriarPessoaResponse criar(CriarPessoaRequest request) {
        return criarPessoaUseCase.executar(request);
    }

    public ListarPessoasResponse listar() {
        return buscarTodasPessoasUseCase.executar();
    }

    public BuscarPessoaResponse buscar(String id) {
        return buscaPessoaUseCase.executar(id);
    }

    public EditarPessoaResponse editar(String id, EditarPessoaRequest request) {
        return editarPessoaUseCase.executar(id, request);
    }

    public DeletarPessoaResponse deletar(String id) {
        return deletarPessoaUseCase.executar(id);
    }
}