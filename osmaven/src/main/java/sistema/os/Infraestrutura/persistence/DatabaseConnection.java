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
            criarTabelaPessoas();
            criarTabelaFormasPagamento();
            criarTabelaPagamentos();
        }

        private static void criarTabelaPessoas() {
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
                throw new RuntimeException("Falha ao criar tabela pessoas", e);
            }
        }

        private static void criarTabelaFormasPagamento() {
            String checkTableSql = """
                SELECT to_regclass('public.formas_pagamento')
                """;

            String createTableSql = """
                CREATE TABLE formas_pagamento (
                    id VARCHAR(36) PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    descricao TEXT,
                    ativo BOOLEAN NOT NULL DEFAULT true,
                    data_criacao TIMESTAMP NOT NULL
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
                        System.out.println("Tabela 'formas_pagamento' criada com sucesso.");
                    }
                } else {
                    System.out.println("Tabela 'formas_pagamento' já existe.");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Falha ao criar tabela formas_pagamento", e);
            }
        }

        private static void criarTabelaPagamentos() {
            String checkTableSql = """
                SELECT to_regclass('public.pagamentos')
                """;

            String createTableSql = """
                CREATE TABLE pagamentos (
                    id VARCHAR(36) PRIMARY KEY,
                    ordem_servico_id VARCHAR(36) NOT NULL,
                    valor DECIMAL(10, 2) NOT NULL,
                    status VARCHAR(20) NOT NULL,
                    data_pagamento TIMESTAMP,
                    data_criacao TIMESTAMP NOT NULL,
                    descricao TEXT
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
                        System.out.println("Tabela 'pagamentos' criada com sucesso.");
                    }
                } else {
                    System.out.println("Tabela 'pagamentos' já existe.");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Falha ao criar tabela pagamentos", e);
            }
        }
    }
}