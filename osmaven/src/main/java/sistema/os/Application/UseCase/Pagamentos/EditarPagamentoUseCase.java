package sistema.os.Application.UseCase.Pagamentos;

import sistema.os.API.DTOs.Requests.Pagamentos.EditarPagamentoRequest;
import sistema.os.API.DTOs.Responses.Pagamentos.EditarPagamentoResponse;
import sistema.os.domain.Entidades.Pagamento;
import sistema.os.domain.Interfaces.IPagamentoRepository;

import java.util.UUID;

public class EditarPagamentoUseCase {
    private final IPagamentoRepository repository;

    public EditarPagamentoUseCase(IPagamentoRepository repository) {
        this.repository = repository;
    }

    public EditarPagamentoResponse executar(String id, EditarPagamentoRequest request) {
        try {
            UUID pagamentoId = UUID.fromString(id);
            
            // Busca o pagamento existente
            Pagamento pagamentoAntigo = repository.buscarPorId(pagamentoId);
            
            if (pagamentoAntigo == null) {
                return new EditarPagamentoResponse(false, "Pagamento não encontrado", null);
            }
            
            // Cria um novo objeto com os valores atualizados
            Pagamento pagamentoAtualizado = new Pagamento(
                pagamentoAntigo.getId(),
                pagamentoAntigo.getOrdemServicoId(),
                request.getValor(),
                pagamentoAntigo.getStatus(),
                pagamentoAntigo.getDataPagamento(),
                pagamentoAntigo.getDataCriacao(),
                request.getDescricao()
            );
            
            // Salva as alterações
            repository.atualizar(pagamentoAtualizado);
            
            return new EditarPagamentoResponse(true, "Pagamento editado com sucesso", id);
            
        } catch (IllegalArgumentException e) {
            return new EditarPagamentoResponse(false, "ID inválido", null);
        } catch (Exception e) {
            return new EditarPagamentoResponse(false, "Erro ao editar pagamento: " + e.getMessage(), null);
        }
    }
}
