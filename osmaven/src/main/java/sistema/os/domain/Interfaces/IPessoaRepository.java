package sistema.os.domain.Interfaces;

import sistema.os.domain.Entidades.Pessoa;

public interface IPessoaRepository {
    void salvar(Pessoa pessoa);
    Pessoa buscarPorCpfCnpj(String cpfCnpj);
}