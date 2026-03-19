package sistema.os.API.DTOs.Requests.Servicos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CriarServicoRequest(
    String descricao,
    double valorPadrao,
    int tempoEstimadoMinutos
) {}
