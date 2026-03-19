package sistema.os.domain.Interfaces;

import java.util.List;
import java.util.UUID;
import sistema.os.domain.Entidades.FormaPagamento;

public interface IFormaPagamentoRepository {
    void salvar(FormaPagamento formaPagamento);
    List<FormaPagamento> buscarTodas();
    FormaPagamento buscarPorId(UUID id);
    boolean deletar(UUID id);
    void atualizar(FormaPagamento formaPagamento);
}
