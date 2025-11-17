package sistema.os.API.Controller;

import sistema.os.API.DTOs.Requests.CriarPessoaRequest;
import sistema.os.API.DTOs.Responses.CriarPessoaResponse;
import sistema.os.Application.UseCase.CriarPessoaUseCase;
import sistema.os.domain.Entidades.Pessoa;

public class PessoaController {

    private final CriarPessoaUseCase criarPessoaUseCase;

    public PessoaController(CriarPessoaUseCase criarPessoaUseCase) {
        this.criarPessoaUseCase = criarPessoaUseCase;
    }

    public CriarPessoaResponse criar(CriarPessoaRequest request) {
        Pessoa pessoa = criarPessoaUseCase.executar(
            request.nome(),
            request.cpfCnpj(),
            request.telefone(),
            request.tipo()
        );

        // AQUI ESTAVA O PROBLEMA → id provavelmente é UUID
        return new CriarPessoaResponse(
            pessoa.getId().toString(),           // ← Converte UUID → String
            pessoa.getNome(),
            pessoa.getCpfCnpj().getValor(),
            pessoa.getTelefone().getValor(),
            pessoa.getTipo().name(),             // enum → String
            pessoa.getStatus().name(),            // enum → String
            pessoa.getDataCadastro()
        );
    }
}