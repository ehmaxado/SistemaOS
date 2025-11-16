package Application.UseCase;

import domain.Entidades.Pessoa;
import domain.Enums.TipoPessoa;
import domain.Interfaces.IPessoaRepository;
import domain.ValueObjects.CpfCnpj;
import domain.ValueObjects.Telefone;

public class CriarPessoaUseCase {
    private final IPessoaRepository repository;

    public CriarPessoaUseCase(IPessoaRepository repository) {
        this.repository = repository;
    }

    public Pessoa executar(String nome, String cpfCnpj, String telefone, String tipoStr) {
        // Validações de negócio
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        CpfCnpj cpfCnpjVO = new CpfCnpj(cpfCnpj);
        Telefone telefoneVO = new Telefone(telefone);

        TipoPessoa tipo = TipoPessoa.valueOf(tipoStr.toUpperCase());
        if (tipo != TipoPessoa.CLIENTE && tipo != TipoPessoa.PRESTADOR) {
            throw new IllegalArgumentException("Tipo deve ser CLIENTE ou PRESTADOR");
        }

        // Verifica duplicidade
        if (repository.buscarPorCpfCnpj(cpfCnpjVO.getValor()) != null) {
            throw new IllegalArgumentException("Já existe pessoa com este CPF/CNPJ");
        }

        Pessoa pessoa = new Pessoa(nome, cpfCnpjVO, telefoneVO, tipo);
        repository.salvar(pessoa);
        return pessoa;
    }
}
