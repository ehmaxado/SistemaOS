package sistema.os.Application.UseCase;

import sistema.os.API.DTOs.Requests.EditarServicoRequest;
import sistema.os.API.DTOs.Responses.ServicoResponse;
import sistema.os.domain.Entidades.Servico;
import sistema.os.domain.Interfaces.IServicoRepository;
import java.util.UUID;

public class EditarServicoUseCase {
    private final IServicoRepository repository;

    public EditarServicoUseCase(IServicoRepository repository) {
        this.repository = repository;
    }

    public ServicoResponse executar(String id, EditarServicoRequest request) {
        UUID servicoId = UUID.fromString(id);
        Servico servico = repository.buscarPorId(servicoId);
        
        if (servico == null) {
            throw new IllegalArgumentException("Serviço não encontrado com ID: " + id);
        }

        // Cria novo serviço com os dados atualizados
        Servico servicoAtualizado = new Servico(
            servicoId,
            request.nome(),
            request.descricao(),
            request.preco(),
            servico.getDataCadastro()
        );

        repository.editar(servicoAtualizado);

        return new ServicoResponse(
            servicoAtualizado.getId().toString(),
            servicoAtualizado.getNome(),
            servicoAtualizado.getDescricao(),
            servicoAtualizado.getPreco(),
            servicoAtualizado.getDataCadastro()
        );
    }
}
