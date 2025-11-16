package sistema.os;

import sistema.os.Infraestrutura.persistence.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        DatabaseConnection.DatabaseInitializer.inicializar();
    }
}