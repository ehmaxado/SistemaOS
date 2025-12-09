package sistema.os.API.DTOs.Responses.Usuarios;

import sistema.os.API.DTOs.Responses.Usuarios.UsuarioResponse;

public class BuscarUsuarioResponse {
    private UsuarioResponse usuario;

    public BuscarUsuarioResponse(UsuarioResponse usuario) {
        this.usuario = usuario;
    }

    public UsuarioResponse getUsuario() { return usuario; }
    public void setUsuario(UsuarioResponse usuario) { this.usuario = usuario; }
}
