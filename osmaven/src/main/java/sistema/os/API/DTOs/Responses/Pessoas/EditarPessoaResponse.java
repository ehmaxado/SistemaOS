package sistema.os.API.DTOs.Responses.Pessoas;

public class EditarPessoaResponse {
    private String mensagem;
    private String pessoaId;
    private String nome;

    public EditarPessoaResponse(String mensagem, String pessoaId, String nome) {
        this.mensagem = mensagem;
        this.pessoaId = pessoaId;
        this.nome = nome;
    }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public String getPessoaId() { return pessoaId; }
    public void setPessoaId(String pessoaId) { this.pessoaId = pessoaId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
