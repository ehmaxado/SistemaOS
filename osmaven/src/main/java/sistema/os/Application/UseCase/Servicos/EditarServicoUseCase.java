package sistema.os.Application.UseCase.Servicos;

import sistema.os.API.DTOs.Requests.Servicos.EditarServicoRequest;
import sistema.os.API.DTOs.Responses.Servicos.ServicoResponse;
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
            request.codigo(),
            request.descricao(),
            request.valorPadrao(),
            request.tempoEstimadoMinutos(),
            request.ativo()
        );

        repository.editar(servicoAtualizado);

        return new ServicoResponse(
            servicoAtualizado.getId().toString(),
            servicoAtualizado.getDescricao(),
            servicoAtualizado.getCodigo(),
            servicoAtualizado.getValorPadrao(),
            servicoAtualizado.getTempoEstimadoMinutos(),
            servicoAtualizado.isAtivo()
        );
    }
}
