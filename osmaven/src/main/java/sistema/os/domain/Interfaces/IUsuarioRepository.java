package sistema.os.domain.Interfaces;

import java.util.List;
import java.util.UUID;
import sistema.os.domain.Entidades.Usuario;

public interface IUsuarioRepository {
    void salvar(Usuario usuario);
    List<Usuario> buscarTodos();
    Usuario buscarPorId(UUID id);
    Usuario buscarPorEmail(String email);
    void atualizar(Usuario usuario);
    boolean deletar(UUID id);
}
