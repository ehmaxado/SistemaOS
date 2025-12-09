package sistema.os.API.DTOs.Requests.Pessoas;

public class BuscarPessoaRequest {
    public String id;

    public BuscarPessoaRequest() {
    }

    public BuscarPessoaRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
