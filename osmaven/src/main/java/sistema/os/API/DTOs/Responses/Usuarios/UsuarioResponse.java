package sistema.os.API.DTOs.Responses.Usuarios;

import java.time.LocalDateTime;

public class UsuarioResponse {
    public String id;
    public String nome;
    public String email;
    public String perfil;
    public boolean ativo;
    public LocalDateTime dataCriacao;

    public UsuarioResponse(String id, String nome, String email, String perfil, boolean ativo, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
    }
}
