package sistema.os.domain.ValueObjects;

public class Telefone {

    private final String valor;

    public Telefone(String valor) {
        String telefoneLimpo = limpar(valor);

        if (telefoneLimpo == null || !telefoneLimpo.matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Telefone inválido. Use um número com DDD (10 ou 11 dígitos)");
        }

        if (telefoneLimpo.length() == 11 && !telefoneLimpo.matches("\\d{2}9\\d{8}")) {
            throw new IllegalArgumentException("Celular deve ter 9 dígitos após o DDD (ex: (11) 98765-4321)");
        }

        this.valor = formatar(telefoneLimpo);
    }

    // Remove caracteres não numéricos
    private String limpar(String telefone) {
        if (telefone == null) return null;
        return telefone.replaceAll("\\D", "");
    }

    // Formata telefone no padrão (XX) XXXXX-XXXX
    private String formatar(String numeros) {
        if (numeros.length() == 11) {
            return String.format("(%s) %s-%s",
                numeros.substring(0, 2),
                numeros.substring(2, 7),
                numeros.substring(7));
        } else {
            return String.format("(%s) %s-%s",
                numeros.substring(0, 2),
                numeros.substring(2, 6),
                numeros.substring(6));
        }
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telefone telefone = (Telefone) o;
        return java.util.Objects.equals(valor, telefone.valor);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}