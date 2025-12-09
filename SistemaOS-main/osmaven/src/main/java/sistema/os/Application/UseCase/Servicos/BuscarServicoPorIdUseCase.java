package sistema.os.Application.UseCase.Servicos;

import sistema.os.API.DTOs.Responses.Servicos.ServicoResponse;
import sistema.os.domain.Entidades.Servico;
import sistema.os.domain.Interfaces.IServicoRepository;
import java.util.UUID;

public class BuscarServicoPorIdUseCase {
    private final IServicoRepository repository;

    public BuscarServicoPorIdUseCase(IServicoRepository repository) {
        this.repository = repository;
    }

    public ServicoResponse executar(String id) {
        Servico servico = repository.buscarPorId(UUID.fromString(id));
        
        if (servico == null) {
            throw new IllegalArgumentException("Serviço não encontrado com ID: " + id);
        }

        return new ServicoResponse(
            servico.getId().toString(),
            servico.getDescricao(),
            servico.getCodigo(),
            servico.getValorPadrao(),
            servico.getTempoEstimadoMinutos(),
            servico.isAtivo()
        );
    }
}
