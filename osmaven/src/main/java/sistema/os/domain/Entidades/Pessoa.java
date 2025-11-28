package sistema.os.domain.Entidades;


import java.time.LocalDateTime;
import java.util.UUID;

import sistema.os.domain.Enums.StatusPessoa;
import sistema.os.domain.Enums.TipoPessoa;
import sistema.os.domain.ValueObjects.CpfCnpj;
import sistema.os.domain.ValueObjects.Telefone;



public class Pessoa {
    private final UUID id;
    private final String nome;
    private final CpfCnpj cpfCnpj;
    private final Telefone telefone;
    private final TipoPessoa tipo;
    private final StatusPessoa status;
    private final LocalDateTime dataCadastro;

    // Cria nova pessoa com validações de negócio
    public Pessoa(String nome, CpfCnpj cpfCnpj, Telefone telefone, TipoPessoa tipo) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        
        if (cpfCnpj == null) {
            throw new IllegalArgumentException("CPF/CNPJ é obrigatório");
        }
        
        if (telefone == null) {
            throw new IllegalArgumentException("Telefone é obrigatório");
        }
        
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de pessoa é obrigatório");
        }
        
        if (tipo != TipoPessoa.CLIENTE && tipo != TipoPessoa.PRESTADOR) {
            throw new IllegalArgumentException("Tipo deve ser CLIENTE ou PRESTADOR");
        }
        
        this.id = UUID.randomUUID();
        this.nome = nome.trim();
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.tipo = tipo;
        this.status = StatusPessoa.ATIVO;
        this.dataCadastro = LocalDateTime.now();
    }

    // Reconstrói pessoa existente do banco de dados
    public Pessoa(UUID id, String nome, CpfCnpj cpfCnpj, Telefone telefone, 
                  TipoPessoa tipo, StatusPessoa status, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.tipo = tipo;
        this.status = status;
        this.dataCadastro = dataCadastro;
    }

    // Getters
    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public CpfCnpj getCpfCnpj() { return cpfCnpj; }
    public Telefone getTelefone() { return telefone; }
    public TipoPessoa getTipo() { return tipo; }
    public StatusPessoa getStatus() { return status; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }
}
