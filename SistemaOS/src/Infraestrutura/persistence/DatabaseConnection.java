package Infraestrutura.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, "sa", "");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco", e);
        }
    }
    public class DatabaseInitializer {
    public static void inicializar() {
        String sql = """
            CREATE TABLE IF NOT EXISTS pessoas (
                id VARCHAR(36) PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                cpf_cnpj VARCHAR(14) NOT NULL UNIQUE,
                telefone VARCHAR(16) NOT NULL,
                tipo VARCHAR(20) NOT NULL,
                status VARCHAR(10) NOT NULL,
                data_cadastro TIMESTAMP NOT NULL
            )
            """;
        try (var conn = DatabaseConnection.getConnection();
             var ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
            System.out.println("Tabela 'pessoas' criada ou já existe.");
        } catch (Exception e) {
            throw new RuntimeException("Falha ao inicializar banco", e);
        }
    }
}
}