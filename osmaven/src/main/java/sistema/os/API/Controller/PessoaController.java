package sistema.os.API.Controller;

import sistema.os.API.DTOs.Requests.CriarPessoaRequest;
import sistema.os.API.DTOs.Responses.CriarPessoaResponse;
import sistema.os.API.DTOs.Responses.BuscarPessoaResponse;
import sistema.os.API.DTOs.Responses.DeletarPessoaResponse;
import sistema.os.API.DTOs.Responses.ListarPessoasResponse;
import sistema.os.Application.UseCase.CriarPessoaUseCase;
import sistema.os.Application.UseCase.BuscarTodasPessoasUseCase;
import sistema.os.Application.UseCase.BuscaPessoaUseCase;
import sistema.os.Application.UseCase.DeletarPessoaUseCase;

public class PessoaController {

    private final CriarPessoaUseCase criarPessoaUseCase;
    private final BuscarTodasPessoasUseCase buscarTodasPessoasUseCase;
    private final BuscaPessoaUseCase buscaPessoaUseCase;
    private final DeletarPessoaUseCase deletarPessoaUseCase;

    public PessoaController(CriarPessoaUseCase criarPessoaUseCase, BuscarTodasPessoasUseCase buscarTodasPessoasUseCase, BuscaPessoaUseCase buscaPessoaUseCase, DeletarPessoaUseCase deletarPessoaUseCase) {
        this.criarPessoaUseCase = criarPessoaUseCase;
        this.buscarTodasPessoasUseCase = buscarTodasPessoasUseCase;
        this.buscaPessoaUseCase = buscaPessoaUseCase;
        this.deletarPessoaUseCase = deletarPessoaUseCase;
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

    public DeletarPessoaResponse deletar(String id) {
        return deletarPessoaUseCase.executar(id);
    }
}