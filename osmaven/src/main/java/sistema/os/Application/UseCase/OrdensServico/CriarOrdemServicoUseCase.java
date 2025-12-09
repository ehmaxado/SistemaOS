package sistema.os.Application.UseCase.OrdensServico;

import sistema.os.API.DTOs.Requests.OrdensServico.CriarOrdemServicoRequest;
import sistema.os.API.DTOs.Responses.OrdensServico.OrdemServicoResponse;
import sistema.os.domain.Entidades.OrdemServico.OrdemServico;
import sistema.os.domain.Interfaces.IOrdemServicoRepository;
import java.util.Collections;

public class CriarOrdemServicoUseCase {
    private final IOrdemServicoRepository repository;

    public CriarOrdemServicoUseCase(IOrdemServicoRepository repository) {
        this.repository = repository;
    }

    public OrdemServicoResponse executar(CriarOrdemServicoRequest request) {
        OrdemServico os = new OrdemServico(
            request.numeroOS(),
            request.id_usuario(),
            request.clienteId(),
            request.formaPagamento(),
            request.observacaoGeral()
        );

        repository.salvar(os);

        return new OrdemServicoResponse(
            os.getId().toString(),
            os.getNumeroOS(),
            os.getId_usuario(),
            os.getClienteId(),
            os.getDataAbertura(),
            os.getDataFechamento(),
            os.getValorTotalProdutos(),
            os.getValorTotalServicos(),
            os.getValorTotal(),
            os.getValorTotalFinal(),
            os.getFormaPagamento(),
            os.getObservacaoGeral(),
            Collections.emptyList(), // Servicos vazios no momento da criação
            Collections.emptyList()  // Produtos vazios no momento da criação
        );
    }
}
