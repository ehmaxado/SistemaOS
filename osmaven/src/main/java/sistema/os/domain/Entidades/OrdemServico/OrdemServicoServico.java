package sistema.os.domain.Entidades.OrdemServico;

import java.util.UUID;

public class OrdemServicoServico {
    private final UUID id;
    private final UUID ordemServicoId;
    private final UUID servicoId;
    private final double valorUnitario;

    // Cria novo detalhe de serviço em uma ordem de serviço
    public OrdemServicoServico(UUID ordemServicoId, UUID servicoId, double valorUnitario) {
        if (ordemServicoId == null) {
            throw new IllegalArgumentException("ID da ordem de serviço é obrigatório");
        }
        if (servicoId == null) {
            throw new IllegalArgumentException("ID do serviço é obrigatório");
        }
        if (valorUnitario < 0) {
            throw new IllegalArgumentException("Valor unitário não pode ser negativo");
        }

        this.id = UUID.randomUUID();
        this.ordemServicoId = ordemServicoId;
        this.servicoId = servicoId;
        this.valorUnitario = valorUnitario;
    }

    // Reconstrói detalhe de serviço existente
    public OrdemServicoServico(UUID id, UUID ordemServicoId, UUID servicoId, double valorUnitario) {
        this.id = id;
        this.ordemServicoId = ordemServicoId;
        this.servicoId = servicoId;
        this.valorUnitario = valorUnitario;
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getOrdemServicoId() { return ordemServicoId; }
    public UUID getServicoId() { return servicoId; }
    public double getValorUnitario() { return valorUnitario; }
}
