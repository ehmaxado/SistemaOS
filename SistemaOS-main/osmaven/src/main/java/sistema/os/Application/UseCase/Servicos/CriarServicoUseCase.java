package sistema.os.Application.UseCase.Servicos;

import sistema.os.API.DTOs.Requests.Servicos.CriarServicoRequest;
import sistema.os.API.DTOs.Responses.Servicos.ServicoResponse;
import sistema.os.domain.Entidades.Servico;
import sistema.os.domain.Interfaces.IServicoRepository;

public class CriarServicoUseCase {
    private final IServicoRepository repository;

    public CriarServicoUseCase(IServicoRepository repository) {
        this.repository = repository;
    }

    public ServicoResponse executar(CriarServicoRequest request) {
        Servico servico = new Servico(
            request.descricao(),
            request.valorPadrao(),
            request.tempoEstimadoMinutos()
        );

        try {
            repository.salvar(servico);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar serviço no banco de dados", e);
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
