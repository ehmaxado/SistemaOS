package domain.Interfaces;

import domain.Entidades.Pessoa;

public interface IPessoaRepository {
    void salvar(Pessoa pessoa);
    Pessoa buscarPorCpfCnpj(String cpfCnpj);
}