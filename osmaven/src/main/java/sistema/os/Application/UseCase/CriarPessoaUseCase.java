package sistema.os.Application.UseCase;

import sistema.os.domain.Entidades.Pessoa;
import sistema.os.domain.Enums.TipoPessoa;
import sistema.os.domain.Interfaces.IPessoaRepository;
import sistema.os.domain.ValueObjects.CpfCnpj;
import sistema.os.domain.ValueObjects.Telefone;

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
