package sistema.os.domain.Interfaces;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import sistema.os.domain.Entidades.Pagamento;
import sistema.os.domain.Enums.StatusPagamento;

public interface IPagamentoRepository {
    void salvar(Pagamento pagamento);
    List<Pagamento> buscarTodas();
    Pagamento buscarPorId(UUID id);
    boolean deletar(UUID id);
    void atualizar(Pagamento pagamento);
    List<Pagamento> buscarPorStatus(StatusPagamento status);
    List<Pagamento> buscarPorData(LocalDateTime dataInicio, LocalDateTime dataFim);
}
