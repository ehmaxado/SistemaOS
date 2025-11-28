package sistema.os.Application.UseCase;

import sistema.os.domain.Entidades.Pessoa;
import sistema.os.domain.Enums.TipoPessoa;
import sistema.os.domain.Interfaces.IPessoaRepository;
import sistema.os.domain.ValueObjects.CpfCnpj;
import sistema.os.domain.ValueObjects.Telefone;
import sistema.os.API.DTOs.Requests.CriarPessoaRequest;
import sistema.os.API.DTOs.Responses.CriarPessoaResponse;

public class CriarPessoaUseCase {
    private final IPessoaRepository repository;

    public CriarPessoaUseCase(IPessoaRepository repository) {
        this.repository = repository;
    }

    // Cria e persiste nova pessoa
    public CriarPessoaResponse executar(CriarPessoaRequest request) {
        CpfCnpj cpfCnpj = new CpfCnpj(request.cpfCnpj());
        Telefone telefone = new Telefone(request.telefone());
        TipoPessoa tipo = TipoPessoa.valueOf(request.tipo().toUpperCase());

        Pessoa pessoa = new Pessoa(request.nome(), cpfCnpj, telefone, tipo);

        try {
            repository.salvar(pessoa);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar pessoa no banco de dados", e);
        }

        return new CriarPessoaResponse(
            pessoa.getId().toString(),
            pessoa.getNome(),
            pessoa.getCpfCnpj().getValor(),
            pessoa.getTelefone().getValor(),
            pessoa.getTipo().name(),
            pessoa.getStatus().name(),
            pessoa.getDataCadastro()
        );
    }
}