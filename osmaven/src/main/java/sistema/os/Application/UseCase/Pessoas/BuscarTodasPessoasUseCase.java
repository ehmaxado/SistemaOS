package sistema.os.Application.UseCase.Pessoas;

import java.util.List;
import java.util.stream.Collectors;

import sistema.os.API.DTOs.Responses.Pessoas.ListaPessoaResponse;
import sistema.os.API.DTOs.Responses.Pessoas.ListarPessoasResponse;
import sistema.os.domain.Entidades.Pessoa;
import sistema.os.domain.Interfaces.IPessoaRepository;

public class BuscarTodasPessoasUseCase {
    private final IPessoaRepository repository;

    public BuscarTodasPessoasUseCase(IPessoaRepository repository) {
        this.repository = repository;
    }

    public ListarPessoasResponse executar() {
        List<Pessoa> pessoas = repository.buscarTodas();
        
        List<ListaPessoaResponse> pessoasResponse = pessoas.stream()
            .map(pessoa -> new ListaPessoaResponse(
                pessoa.getId().toString(),
                pessoa.getNome(),
                pessoa.getCpfCnpj(),
                pessoa.getTelefone(),
                pessoa.getTipoPessoa(),
                pessoa.getEmail(),
                pessoa.getCep(),
                pessoa.getLogradouro(),
                pessoa.getNumero(),
                pessoa.getBairro(),
                pessoa.getCidade(),
                pessoa.getUf(),
                pessoa.getDataCadastro()
            ))
            .collect(Collectors.toList());
        
        return new ListarPessoasResponse(pessoasResponse, pessoasResponse.size());
    }
}
