package sistema.os.domain.Entidades.OrdemServico;

import java.util.UUID;

public class OrdemServicoDetalhe {
    private final UUID id;
    private final UUID ordemServicoId;
    private final String descricaoObjeto;
    private final String marca;
    private final String numeroserie;
    private final String defeitorelacionado;
    private final String acessoriosEntregues;

    // Cria novo detalhe de ordem de serviço
    public OrdemServicoDetalhe(UUID ordemServicoId, String descricaoObjeto, String marca, 
                              String numeroserie, String defeitorelacionado, String acessoriosEntregues) {
        if (ordemServicoId == null) {
            throw new IllegalArgumentException("ID da ordem de serviço é obrigatório");
        }

        this.id = UUID.randomUUID();
        this.ordemServicoId = ordemServicoId;
        this.descricaoObjeto = descricaoObjeto != null ? descricaoObjeto.trim() : "";
        this.marca = marca != null ? marca.trim() : "";
        this.numeroserie = numeroserie != null ? numeroserie.trim() : "";
        this.defeitorelacionado = defeitorelacionado != null ? defeitorelacionado.trim() : "";
        this.acessoriosEntregues = acessoriosEntregues != null ? acessoriosEntregues.trim() : "";
    }

    // Reconstrói detalhe existente
    public OrdemServicoDetalhe(UUID id, UUID ordemServicoId, String descricaoObjeto, String marca,
                              String numeroserie, String defeitorelacionado, String acessoriosEntregues) {
        this.id = id;
        this.ordemServicoId = ordemServicoId;
        this.descricaoObjeto = descricaoObjeto;
        this.marca = marca;
        this.numeroserie = numeroserie;
        this.defeitorelacionado = defeitorelacionado;
        this.acessoriosEntregues = acessoriosEntregues;
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getOrdemServicoId() { return ordemServicoId; }
    public String getDescricaoObjeto() { return descricaoObjeto; }
    public String getMarca() { return marca; }
    public String getNumeroserie() { return numeroserie; }
    public String getDefeitorelacionado() { return defeitorelacionado; }
    public String getAcessoriosEntregues() { return acessoriosEntregues; }
}

