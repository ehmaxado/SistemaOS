package sistema.os.API.DTOs.Responses.OrdensServico;

import java.time.LocalDateTime;
import java.util.List;

public record OrdemServicoResponse(
    String id,
    String numeroOS,
    String id_usuario,
    String clienteId,
    LocalDateTime dataAbertura,
    LocalDateTime dataFechamento,
    double valorTotalProdutos,
    double valorTotalServicos,
    double valorTotal,
    double valorTotalFinal,
    String formaPagamento,
    String observacaoGeral,
    List<ServicoOSDTO> servicos,
    List<ProdutoOSDTO> produtos
) {
    public record ServicoOSDTO(
        String servicoId,
        String descricao,
        int quantidade,
        double valorUnitario,
        double valorTotal
    ) {}
    
    public record ProdutoOSDTO(
        String produtoId,
        String nome,
        int quantidade,
        double valorUnitario,
        double valorTotal
    ) {}
}
