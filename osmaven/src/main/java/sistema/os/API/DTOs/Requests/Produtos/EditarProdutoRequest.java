package sistema.os.API.DTOs.Requests.Produtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EditarProdutoRequest(
    String nome,
    String descricao,
    String marca,
    String unidade,
    int estoqueAtual,
    double valorCusto,
    double valorVenda
) {}
