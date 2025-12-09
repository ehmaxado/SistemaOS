package sistema.os.API.DTOs.Responses.Pessoas;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "nome", "cpfCnpj", "telefone", "tipoPessoa", "email", "cep", "logradouro", "numero", "bairro", "cidade", "uf", "dataCadastro"})
public class ListaPessoaResponse {
    public String id;
    public String nome;
    public String cpfCnpj;
    public String telefone;
    public String tipoPessoa;
    public String email;
    public String cep;
    public String logradouro;
    public String numero;
    public String bairro;
    public String cidade;
    public String uf;
    public LocalDateTime dataCadastro;

    public ListaPessoaResponse() {
    }

    public ListaPessoaResponse(String id, String nome, String cpfCnpj, String telefone,
                               String tipoPessoa, String email, String cep, String logradouro,
                               String numero, String bairro, String cidade, String uf,
                               LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.tipoPessoa = tipoPessoa;
        this.email = email;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.dataCadastro = dataCadastro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
