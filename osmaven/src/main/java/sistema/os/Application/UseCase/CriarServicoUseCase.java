package sistema.os.Application.UseCase;

import sistema.os.API.DTOs.Requests.CriarServicoRequest;
import sistema.os.API.DTOs.Responses.ServicoResponse;
import sistema.os.domain.Entidades.Servico;
import sistema.os.domain.Interfaces.IServicoRepository;

public class CriarServicoUseCase {
    private final IServicoRepository repository;

    public CriarServicoUseCase(IServicoRepository repository) {
        this.repository = repository;
    }

    public ServicoResponse executar(CriarServicoRequest request) {
        Servico servico = new Servico(request.nome(), request.descricao(), request.preco());

        try {
            repository.salvar(servico);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar serviço no banco de dados", e);
        }

        return new ServicoResponse(
            servico.getId().toString(),
            servico.getNome(),
            servico.getDescricao(),
            servico.getPreco(),
            servico.getDataCadastro()
        );
    }
}
