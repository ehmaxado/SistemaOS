package API.view;

import java.util.Scanner;

public class MenuView {
    public final Scanner scanner = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println("=== SISTEMA DE CADASTRO DE PESSOAS ===");
        System.out.println("1. Cadastrar Pessoa");
        System.out.println("2. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public String[] capturarDadosPessoa() {
        System.out.println("\n--- CADASTRO DE PESSOA ---");
        
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF/CNPJ (apenas números): ");
        String cpfCnpj = scanner.nextLine();

        System.out.print("Telefone ((99) 9 9999-9999): ");
        String telefone = scanner.nextLine();

        System.out.print("Tipo (CLIENTE ou PRESTADOR): ");
        String tipo = scanner.nextLine().toUpperCase();

        return new String[]{nome, cpfCnpj, telefone, tipo};
    }

    public void mostrarSucesso(String id) {
        System.out.println("Pessoa cadastrada com sucesso! ID: " + id);
    }

    public void mostrarErro(String mensagem) {
        System.out.println("ERRO: " + mensagem);
    }

    public void mostrarEncerramento() {
        System.out.println("Sistema encerrado.");
    }
}