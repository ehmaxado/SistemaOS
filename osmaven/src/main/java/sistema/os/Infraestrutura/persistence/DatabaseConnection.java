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
            criarTabelaServicos();
            criarTabelaProdutos();
            criarTabelaOrdemServico();
            criarTabelaOrdemServicoServicos();
            criarTabelaOrdemServicoProdutos();
        }

        private static void criarTabelaPessoas() {
            String checkTableSql = "SELECT to_regclass('public.pessoas')";
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
            criarTabela("pessoas", checkTableSql, createTableSql);
        }

        private static void criarTabelaServicos() {
            String checkTableSql = "SELECT to_regclass('public.servicos')";
            String createTableSql = """
                CREATE TABLE servicos (
                    id VARCHAR(36) PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    descricao TEXT,
                    preco DECIMAL(10, 2) NOT NULL,
                    data_cadastro TIMESTAMP NOT NULL
                )
                """;
            criarTabela("servicos", checkTableSql, createTableSql);
        }

        private static void criarTabelaProdutos() {
            String checkTableSql = "SELECT to_regclass('public.produtos')";
            String createTableSql = """
                CREATE TABLE produtos (
                    id VARCHAR(36) PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    descricao TEXT,
                    preco DECIMAL(10, 2) NOT NULL,
                    estoque INTEGER NOT NULL,
                    data_cadastro TIMESTAMP NOT NULL
                )
                """;
            criarTabela("produtos", checkTableSql, createTableSql);
        }

        private static void criarTabelaOrdemServico() {
            String checkTableSql = "SELECT to_regclass('public.ordem_servico')";
            String createTableSql = """
                CREATE TABLE ordem_servico (
                    id VARCHAR(36) PRIMARY KEY,
                    pessoa_cliente_id VARCHAR(36) NOT NULL,
                    pessoa_prestador_id VARCHAR(36) NOT NULL,
                    status VARCHAR(20) NOT NULL,
                    data_criacao TIMESTAMP NOT NULL,
                    data_atualizacao TIMESTAMP NOT NULL,
                    descricao TEXT,
                    FOREIGN KEY (pessoa_cliente_id) REFERENCES pessoas(id),
                    FOREIGN KEY (pessoa_prestador_id) REFERENCES pessoas(id)
                )
                """;
            criarTabela("ordem_servico", checkTableSql, createTableSql);
        }

        private static void criarTabelaOrdemServicoServicos() {
            String checkTableSql = "SELECT to_regclass('public.ordem_servico_servicos')";
            String createTableSql = """
                CREATE TABLE ordem_servico_servicos (
                    id VARCHAR(36) PRIMARY KEY,
                    ordem_servico_id VARCHAR(36) NOT NULL,
                    servico_id VARCHAR(36) NOT NULL,
                    valor_unitario DECIMAL(10, 2) NOT NULL,
                    FOREIGN KEY (ordem_servico_id) REFERENCES ordem_servico(id),
                    FOREIGN KEY (servico_id) REFERENCES servicos(id)
                )
                """;
            criarTabela("ordem_servico_servicos", checkTableSql, createTableSql);
        }

        private static void criarTabelaOrdemServicoProdutos() {
            String checkTableSql = "SELECT to_regclass('public.ordem_servico_produtos')";
            String createTableSql = """
                CREATE TABLE ordem_servico_produtos (
                    id VARCHAR(36) PRIMARY KEY,
                    ordem_servico_id VARCHAR(36) NOT NULL,
                    produto_id VARCHAR(36) NOT NULL,
                    valor_unitario DECIMAL(10, 2) NOT NULL,
                    quantidade INTEGER NOT NULL,
                    FOREIGN KEY (ordem_servico_id) REFERENCES ordem_servico(id),
                    FOREIGN KEY (produto_id) REFERENCES produtos(id)
                )
                """;
            criarTabela("ordem_servico_produtos", checkTableSql, createTableSql);
        }

        private static void criarTabela(String nomeTabelaLogDiag, String checkTableSql, String createTableSql) {
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
                        System.out.println("Tabela '" + nomeTabelaLogDiag + "' criada com sucesso.");
                    }
                } else {
                    System.out.println("Tabela '" + nomeTabelaLogDiag + "' já existe.");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Falha ao inicializar a tabela " + nomeTabelaLogDiag, e);
            }
        }
    }
}