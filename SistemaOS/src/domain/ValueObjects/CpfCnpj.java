package domain.ValueObjects;

public class CpfCnpj {
    private final String valor;

    public CpfCnpj(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("CPF/CNPJ é obrigatório");
        }
        String apenasNumeros = valor.replaceAll("\\D", "");
        if (apenasNumeros.length() != 11 && apenasNumeros.length() != 14) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos e CNPJ 14 dígitos");
        }
        this.valor = apenasNumeros;
    }

    public String getValor() { return valor; }
    @Override public String toString() { return valor; }
}