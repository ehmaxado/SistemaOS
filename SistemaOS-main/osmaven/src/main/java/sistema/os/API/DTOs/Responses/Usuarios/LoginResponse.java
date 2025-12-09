package sistema.os.API.DTOs.Responses.Usuarios;

public class LoginResponse {
    public boolean sucesso;
    public String mensagem;
    public UsuarioResponse usuario;

    public LoginResponse(boolean sucesso, String mensagem, UsuarioResponse usuario) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.usuario = usuario;
    }
}
