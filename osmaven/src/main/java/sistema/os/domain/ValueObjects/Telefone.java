package sistema.os.domain.ValueObjects;

public class Telefone {
    private final String valor;

    public Telefone(String valor) {
        if (valor == null || !valor.matches("\\(\\d{2}\\) 9 \\d{4}-\\d{4}")) {
            throw new IllegalArgumentException("Telefone deve estar no formato (99) 9 9999-9999");
        }
        this.valor = valor;
    }

    public String getValor() { return valor; }
    @Override public String toString() { return valor; }
}
