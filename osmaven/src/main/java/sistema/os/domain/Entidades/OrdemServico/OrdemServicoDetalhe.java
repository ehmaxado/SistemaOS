package sistema.os.domain.Entidades.OrdemServico;

import java.time.LocalDateTime;

// DTO para retornar informações detalhadas de uma Ordem de Serviço
public record OrdemServicoDetalhe(
    String id,
    String pessoaClienteId,
    String pessoaPrestadorId,
    String status,
    LocalDateTime dataCriacao,
    LocalDateTime dataAtualizacao,
    String descricao,
    double valorTotal
) {}
