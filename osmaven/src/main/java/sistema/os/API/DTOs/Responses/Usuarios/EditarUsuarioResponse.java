package sistema.os.API.DTOs.Responses.Usuarios;

public class EditarUsuarioResponse {
    private String mensagem;
    private String usuarioId;
    private String nome;

    public EditarUsuarioResponse(String mensagem, String usuarioId, String nome) {
        this.mensagem = mensagem;
        this.usuarioId = usuarioId;
        this.nome = nome;
    }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
