package sistema.os.API.DTOs.Responses.Usuarios;

import java.util.List;

public class ListarUsuariosResponse {
    public List<UsuarioResponse> usuarios;

    public ListarUsuariosResponse(List<UsuarioResponse> usuarios) {
        this.usuarios = usuarios;
    }
}
