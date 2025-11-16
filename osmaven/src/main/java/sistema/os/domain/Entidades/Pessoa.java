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

    public Pessoa(String nome, CpfCnpj cpfCnpj, Telefone telefone, TipoPessoa tipo) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.tipo = tipo;
        this.status = StatusPessoa.ATIVO;
        this.dataCadastro = LocalDateTime.now();
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
