package sistema.os.domain.Interfaces;

import sistema.os.domain.Entidades.Servico;
import java.util.List;
import java.util.UUID;

public interface IServicoRepository {
    void salvar(Servico servico);
    Servico buscarPorId(UUID id);
    List<Servico> listarTodos();
    void editar(Servico servico);
    void deletar(UUID id);
}
