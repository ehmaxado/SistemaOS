package sistema.os.domain.Entidades;

import java.util.UUID;

public class Servico {
    private final UUID id;
    private final String codigo;
    private final String descricao;
    private final double valorPadrao;
    private final int tempoEstimadoMinutos;
    private final boolean ativo;

    // Cria novo serviço com validações de negócio
    public Servico(String descricao, double valorPadrao, int tempoEstimadoMinutos) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição do serviço é obrigatória");
        }
        if (valorPadrao < 0) {
            throw new IllegalArgumentException("Valor padrão não pode ser negativo");
        }
        if (tempoEstimadoMinutos < 0) {
            throw new IllegalArgumentException("Tempo estimado não pode ser negativo");
        }
        
        this.id = UUID.randomUUID();
        this.codigo = "SRV-" + System.currentTimeMillis();
        this.descricao = descricao.trim();
        this.valorPadrao = valorPadrao;
        this.tempoEstimadoMinutos = tempoEstimadoMinutos;
        this.ativo = true;
    }

    // Reconstrói serviço existente do banco de dados
    public Servico(UUID id, String codigo, String descricao, double valorPadrao, 
                   int tempoEstimadoMinutos, boolean ativo) {
        this.id = id;
        this.codigo = codigo;
        this.descricao = descricao;
        this.valorPadrao = valorPadrao;
        this.tempoEstimadoMinutos = tempoEstimadoMinutos;
        this.ativo = ativo;
    }

    // Getters
    public UUID getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }
    public double getValorPadrao() { return valorPadrao; }
    public int getTempoEstimadoMinutos() { return tempoEstimadoMinutos; }
    public boolean isAtivo() { return ativo; }
}
