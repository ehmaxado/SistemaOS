package sistema.os.domain.Interfaces;

import sistema.os.domain.Entidades.Produto;
import java.util.List;
import java.util.UUID;

public interface IProdutoRepository {
    void salvar(Produto produto);
    Produto buscarPorId(UUID id);
    List<Produto> listarTodos();
    void editar(Produto produto);
    void deletar(UUID id);
}
