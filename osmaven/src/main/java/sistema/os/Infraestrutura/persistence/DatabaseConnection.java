package sistema.os.Infraestrutura.persistence;

import java.sql.*;

public class DatabaseConnection {
  
    private static final String URL = "jdbc:postgresql://localhost:5432/sistemaos"; 
    private static final String USER = "postgres";                             
    private static final String PASSWORD = "123123";  

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados PostgreSQL", e);
        }
    }

    public static class DatabaseInitializer {

        public static void inicializar() {
            String checkTableSql = """
                SELECT to_regclass('public.pessoas')
                """;

            String createTableSql = """
                CREATE TABLE pessoas (
                    id VARCHAR(36) PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    cpf_cnpj VARCHAR(14) NOT NULL UNIQUE,
                    telefone VARCHAR(16) NOT NULL,
                    tipo VARCHAR(20) NOT NULL,
                    status VARCHAR(10) NOT NULL,
                    data_cadastro TIMESTAMP NOT NULL
                )
                """;

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement checkPs = conn.prepareStatement(checkTableSql);
                 ResultSet rs = checkPs.executeQuery()) {

                boolean tableExists = false;
                if (rs.next()) {
                    tableExists = rs.getString(1) != null;
                }

                if (!tableExists) {
                    try (PreparedStatement createPs = conn.prepareStatement(createTableSql)) {
                        createPs.executeUpdate();
                        System.out.println("Tabela 'pessoas' criada com sucesso.");
                    }
                } else {
                    System.out.println("Tabela 'pessoas' já existe.");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Falha ao inicializar o banco de dados", e);
            }
        }
    }
}