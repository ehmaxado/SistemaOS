package sistema.os.Application.UseCase;

import sistema.os.domain.Entidades.Pessoa;
import sistema.os.domain.Enums.TipoPessoa;
import sistema.os.domain.Interfaces.IPessoaRepository;
import sistema.os.domain.ValueObjects.CpfCnpj;
import sistema.os.domain.ValueObjects.Telefone;

// Nova exceção customizada (só para erros de persistência)
class PersistenciaException extends RuntimeException {
    public PersistenciaException(String message) {
        super(message);
    }
    public PersistenciaException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class CriarPessoaUseCase {
    private final IPessoaRepository repository;

    public CriarPessoaUseCase(IPessoaRepository repository) {
        this.repository = repository;
    }

    public Pessoa executar(String nome, String cpfCnpj, String telefone, String tipoStr) {
        // === Validações de negócio (mantidas 100%) ===
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        CpfCnpj cpfCnpjVO;
        try {
            cpfCnpjVO = new CpfCnpj(cpfCnpj);
        } catch (Exception e) {
            throw new IllegalArgumentException("CPF/CNPJ inválido: " + e.getMessage());
        }

        Telefone telefoneVO;
        try {
            telefoneVO = new Telefone(telefone);
        } catch (Exception e) {
            throw new IllegalArgumentException("Telefone inválido: " + e.getMessage());
        }

        TipoPessoa tipo;
        try {
            tipo = TipoPessoa.valueOf(tipoStr.toUpperCase());
            if (tipo != TipoPessoa.CLIENTE && tipo != TipoPessoa.PRESTADOR) {
                throw new IllegalArgumentException("Tipo deve ser CLIENTE ou PRESTADOR");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo inválido. Use CLIENTE ou PRESTADOR");
        }

        // Verifica duplicidade
        if (repository.buscarPorCpfCnpj(cpfCnpjVO.getValor()) != null) {
            throw new IllegalArgumentException("Já existe pessoa com este CPF/CNPJ");
        }

        // === Criação e salvamento com tratamento de erro do banco ===
        Pessoa pessoa = new Pessoa(nome.trim(), cpfCnpjVO, telefoneVO, tipo);

        try {
            repository.salvar(pessoa);
        } catch (Exception e) {
            // Aqui capturamos QUALQUER erro do INSERT no banco
            throw new PersistenciaException("Falha ao salvar pessoa no banco de dados", e);
        }

        return pessoa;
    }
}