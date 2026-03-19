package sistema.os.API.DTOs.Responses.Usuarios;

public class DeletarUsuarioResponse {
    private String mensagem;
    private String usuarioId;

    public DeletarUsuarioResponse(String mensagem, String usuarioId) {
        this.mensagem = mensagem;
        this.usuarioId = usuarioId;
    }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
}
