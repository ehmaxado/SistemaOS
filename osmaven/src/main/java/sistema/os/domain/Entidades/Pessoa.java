package sistema.os.domain.Entidades;

import java.time.LocalDateTime;
import java.util.UUID;

public class Pessoa {
    private final UUID id;
    private final String tipoPessoa;
    private final String nome;
    private final String cpfCnpj;
    private final String telefone;
    private final String email;
    private final String cep;
    private final String logradouro;
    private final String numero;
    private final String bairro;
    private final String cidade;
    private final String uf;
    private final String status;
    private final LocalDateTime dataCadastro;

    // Cria nova pessoa com validações de negócio
    public Pessoa(String tipoPessoa, String nome, String cpfCnpj, String telefone, String email,
                  String cep, String logradouro, String numero, String bairro, String cidade, String uf) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (cpfCnpj == null || cpfCnpj.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF/CNPJ é obrigatório");
        }
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone é obrigatório");
        }
        
        this.id = UUID.randomUUID();
        this.tipoPessoa = tipoPessoa != null ? tipoPessoa.trim() : "";
        this.nome = nome.trim();
        this.cpfCnpj = cpfCnpj.trim();
        this.telefone = telefone.trim();
        this.email = email != null ? email.trim() : "";
        this.cep = cep != null ? cep.trim() : "";
        this.logradouro = logradouro != null ? logradouro.trim() : "";
        this.numero = numero != null ? numero.trim() : "";
        this.bairro = bairro != null ? bairro.trim() : "";
        this.cidade = cidade != null ? cidade.trim() : "";
        this.uf = uf != null ? uf.trim() : "";
        this.status = "ATIVO";
        this.dataCadastro = LocalDateTime.now();
    }

    // Reconstrói pessoa existente do banco de dados
    public Pessoa(UUID id, String tipoPessoa, String nome, String cpfCnpj, String telefone,
                  String email, String cep, String logradouro, String numero, String bairro,
                  String cidade, String uf, String status, LocalDateTime dataCadastro) {
        this.id = id;
        this.tipoPessoa = tipoPessoa;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.email = email;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.status = status;
        this.dataCadastro = dataCadastro;
    }

    // Getters
    public UUID getId() { return id; }
    public String getTipoPessoa() { return tipoPessoa; }
    public String getNome() { return nome; }
    public String getCpfCnpj() { return cpfCnpj; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
    public String getCep() { return cep; }
    public String getLogradouro() { return logradouro; }
    public String getNumero() { return numero; }
    public String getBairro() { return bairro; }
    public String getCidade() { return cidade; }
    public String getUf() { return uf; }
    public String getStatus() { return status; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }
}
