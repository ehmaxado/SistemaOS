package sistema.os.API.Controller;

import sistema.os.API.DTOs.Requests.CriarPessoaRequest;
import sistema.os.API.DTOs.Responses.CriarPessoaResponse;
import sistema.os.Application.UseCase.CriarPessoaUseCase;

public class PessoaController {

    private final CriarPessoaUseCase criarPessoaUseCase;

    public PessoaController(CriarPessoaUseCase criarPessoaUseCase) {
        this.criarPessoaUseCase = criarPessoaUseCase;
    }

    // Delega criação de pessoa para o use case
    public CriarPessoaResponse criar(CriarPessoaRequest request) {
        return criarPessoaUseCase.executar(request);
    }
}