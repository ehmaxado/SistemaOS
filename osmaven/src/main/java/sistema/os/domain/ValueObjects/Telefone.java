package sistema.os.domain.ValueObjects;

public class Telefone {

    private final String valor;

    public Telefone(String valor) {
        String telefoneLimpo = limpar(valor);

        // Valida apenas dígitos e tamanho correto
        if (telefoneLimpo == null || !telefoneLimpo.matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Telefone inválido. Use um número com DDD (10 ou 11 dígitos)");
        }

        // Verifica se é celular (11 dígitos) → deve começar com 9 (após 2012)
        if (telefoneLimpo.length() == 11 && !telefoneLimpo.matches("\\d{2}9\\d{8}")) {
            throw new IllegalArgumentException("Celular deve ter 9 dígitos após o DDD (ex: (11) 98765-4321)");
        }

        // Formata bonitinho para armazenar
        this.valor = formatar(telefoneLimpo);
    }

    // Remove tudo que não for número
    private String limpar(String telefone) {
        if (telefone == null) return null;
        return telefone.replaceAll("\\D", ""); // remove (), espaço, -, etc.
    }

    // Formata para o padrão bonito: (11) 98765-4321
    private String formatar(String numeros) {
        if (numeros.length() == 11) {
            return String.format("(%s) %s-%s",
                numeros.substring(0, 2),
                numeros.substring(2, 7),
                numeros.substring(7));
        } else { // 10 dígitos (fixo ou celular antigo)
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
    public String toString() {
        return valor;
    }
}