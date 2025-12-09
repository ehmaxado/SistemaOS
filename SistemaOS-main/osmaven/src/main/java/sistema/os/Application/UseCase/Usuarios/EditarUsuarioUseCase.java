package sistema.os.Application.UseCase.Usuarios;

import sistema.os.API.DTOs.Requests.Usuarios.EditarUsuarioRequest;
import sistema.os.API.DTOs.Responses.Usuarios.EditarUsuarioResponse;
import sistema.os.domain.Entidades.Usuario;
import sistema.os.domain.Interfaces.IUsuarioRepository;

import java.util.UUID;

public class EditarUsuarioUseCase {
    private final IUsuarioRepository repository;

    public EditarUsuarioUseCase(IUsuarioRepository repository) {
        this.repository = repository;
    }

    public EditarUsuarioResponse executar(String id, EditarUsuarioRequest request) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do usuário é obrigatório");
        }

        UUID usuarioId;
        try {
            usuarioId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID inválido");
        }

        Usuario usuarioExistente = repository.buscarPorId(usuarioId);
        if (usuarioExistente == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        // Usar valores existentes se não fornecidos na request
        String novoNome = (request.getNome() != null && !request.getNome().trim().isEmpty()) 
            ? request.getNome() : usuarioExistente.getNome();
        
        String novoEmail = (request.getEmail() != null && !request.getEmail().trim().isEmpty()) 
            ? request.getEmail() : usuarioExistente.getEmail();
        
        String novaSenha = (request.getSenha() != null && !request.getSenha().trim().isEmpty()) 
            ? request.getSenha() : usuarioExistente.getSenha();
        
        String novoPerfil = (request.getPerfil() != null && !request.getPerfil().trim().isEmpty()) 
            ? request.getPerfil() : usuarioExistente.getPerfil();
        
        boolean novoAtivo = (request.getAtivo() != null) 
            ? request.getAtivo() : usuarioExistente.isAtivo();

        // Criar nova instância com os dados atualizados (entidade imutável)
        Usuario usuarioAtualizado = new Usuario(
            usuarioId,
            novoNome,
            novoEmail,
            novaSenha,
            novoPerfil,
            novoAtivo,
            usuarioExistente.getDataCriacao()
        );

        repository.atualizar(usuarioAtualizado);

        return new EditarUsuarioResponse(
            "Usuário atualizado com sucesso",
            usuarioAtualizado.getId().toString(),
            usuarioAtualizado.getNome()
        );
    }
}
