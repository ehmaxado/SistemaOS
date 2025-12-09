package sistema.os.domain.Interfaces;

import java.util.List;
import java.util.UUID;
import sistema.os.domain.Entidades.Pessoa;

public interface IPessoaRepository {
    void salvar(Pessoa pessoa);
    List<Pessoa> buscarTodas();
    Pessoa buscarPorId(UUID id);
    void atualizar(Pessoa pessoa);
    boolean deletar(UUID id);
}