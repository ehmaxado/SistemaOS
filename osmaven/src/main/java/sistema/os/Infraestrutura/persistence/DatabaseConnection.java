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
            criarTabelaProdutos();
            criarTabelaServicos();
            criarTabelaFormasPagamento();
            criarTabelaPagamentos();
            criarTabelaUsuarios();
            criarTabelaOrdemServico();
            criarTabelaOrdemServicoDetalhe();
            criarTabelaOrdemServicoProdutos();
            criarTabelaOrdemServicoServicos();
            criarUsuarioPadrao();
        }

        private static void criarTabelaPessoas() {
            String checkTableSql = """
                SELECT to_regclass('public.pessoas')
                """;

            String createTableSql = """
                CREATE TABLE pessoas (
                    id VARCHAR(36) PRIMARY KEY,
                    tipo_pessoa VARCHAR(20) NOT NULL,
                    nome VARCHAR(100) NOT NULL,
                    cpf_cnpj VARCHAR(14) NOT NULL UNIQUE,
                    telefone VARCHAR(16) NOT NULL,
                    email VARCHAR(100),
                    cep VARCHAR(8),
                    logradouro VARCHAR(200),
                    numero VARCHAR(10),
                    bairro VARCHAR(100),
                    cidade VARCHAR(100),
                    uf VARCHAR(2),
                    status VARCHAR(20) NOT NULL,
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

        private static void criarTabelaUsuarios() {
            String checkTableSql = """
                SELECT to_regclass('public.usuarios')
                """;

            String createTableSql = """
                CREATE TABLE usuarios (
                    id VARCHAR(36) PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    email VARCHAR(100) NOT NULL UNIQUE,
                    senha VARCHAR(255) NOT NULL,
                    perfil VARCHAR(50) NOT NULL,
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
                        System.out.println("Tabela 'usuarios' criada com sucesso.");
                    }
                } else {
                    System.out.println("Tabela 'usuarios' já existe.");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Falha ao criar tabela usuarios", e);
            }
        }

        private static void criarTabelaProdutos() {
            String checkTableSql = """
                SELECT to_regclass('public.produtos')
                """;

            String createTableSql = """
                CREATE TABLE produtos (
                    id VARCHAR(36) PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    descricao VARCHAR(200),
                    marca VARCHAR(100),
                    unidade VARCHAR(10),
                    estoque_atual INTEGER NOT NULL DEFAULT 0,
                    valor_custo DECIMAL(10, 2) NOT NULL,
                    valor_venda DECIMAL(10, 2) NOT NULL,
                    ativo BOOLEAN NOT NULL DEFAULT true
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
                        System.out.println("Tabela 'produtos' criada com sucesso.");
                    }
                } else {
                    System.out.println("Tabela 'produtos' já existe.");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Falha ao criar tabela produtos", e);
            }
        }

        private static void criarTabelaServicos() {
            String checkTableSql = """
                SELECT to_regclass('public.servicos')
                """;

            String createTableSql = """
                CREATE TABLE servicos (
                    id VARCHAR(36) PRIMARY KEY,
                    descricao VARCHAR(200) NOT NULL,
                    codigo VARCHAR(50),
                    valor_padrao DECIMAL(10, 2) NOT NULL,
                    tempo_estimado_minutos INTEGER,
                    ativo BOOLEAN NOT NULL DEFAULT true
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
                        System.out.println("Tabela 'servicos' criada com sucesso.");
                    }
                } else {
                    System.out.println("Tabela 'servicos' já existe.");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Falha ao criar tabela servicos", e);
            }
        }

        private static void criarTabelaOrdemServico() {
            String checkTableSql = """
                SELECT to_regclass('public.ordem_servico')
                """;

            String createTableSql = """
                CREATE TABLE ordem_servico (
                    id VARCHAR(36) PRIMARY KEY,
                    numero_os VARCHAR(50),
                    id_usuario VARCHAR(36) NOT NULL,
                    cliente_id VARCHAR(36) NOT NULL,
                    data_criacao TIMESTAMP NOT NULL,
                    data_atualizacao TIMESTAMP NOT NULL,
                    descricao TEXT,
                    valor_total_produtos DECIMAL(10, 2) DEFAULT 0,
                    valor_total_servicos DECIMAL(10, 2) DEFAULT 0,
                    valor_total DECIMAL(10, 2) DEFAULT 0,
                    valor_total_final DECIMAL(10, 2) DEFAULT 0,
                    forma_pagamento VARCHAR(100),
                    observacao_geral TEXT,
                    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
                    FOREIGN KEY (cliente_id) REFERENCES pessoas(id)
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
                        System.out.println("Tabela 'ordem_servico' criada com sucesso.");
                    }
                } else {
                    System.out.println("Tabela 'ordem_servico' já existe.");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Falha ao criar tabela ordem_servico", e);
            }
        }

        private static void criarTabelaOrdemServicoDetalhe() {
            String checkTableSql = """
                SELECT to_regclass('public.ordem_servico_detalhe')
                """;

            String createTableSql = """
                CREATE TABLE ordem_servico_detalhe (
                    id VARCHAR(36) PRIMARY KEY,
                    ordem_servico_id VARCHAR(36) NOT NULL,
                    descricao_objeto TEXT,
                    marca VARCHAR(100),
                    numeroserie VARCHAR(100),
                    defeitorelacionado TEXT,
                    acessorios_entregues TEXT,
                    FOREIGN KEY (ordem_servico_id) REFERENCES ordem_servico(id) ON DELETE CASCADE
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
                        System.out.println("Tabela 'ordem_servico_detalhe' criada com sucesso.");
                    }
                } else {
                    System.out.println("Tabela 'ordem_servico_detalhe' já existe.");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Falha ao criar tabela ordem_servico_detalhe", e);
            }
        }

        private static void criarTabelaOrdemServicoProdutos() {
            String checkTableSql = """
                SELECT to_regclass('public.ordem_servico_produtos')
                """;

            String createTableSql = """
                CREATE TABLE ordem_servico_produtos (
                    id VARCHAR(36) PRIMARY KEY,
                    ordem_servico_id VARCHAR(36) NOT NULL,
                    produto_id VARCHAR(36) NOT NULL,
                    quantidade INTEGER NOT NULL,
                    valor_total DECIMAL(10, 2) NOT NULL,
                    FOREIGN KEY (ordem_servico_id) REFERENCES ordem_servico(id) ON DELETE CASCADE,
                    FOREIGN KEY (produto_id) REFERENCES produtos(id)
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
                        System.out.println("Tabela 'ordem_servico_produtos' criada com sucesso.");
                    }
                } else {
                    System.out.println("Tabela 'ordem_servico_produtos' já existe.");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Falha ao criar tabela ordem_servico_produtos", e);
            }
        }

        private static void criarTabelaOrdemServicoServicos() {
            String checkTableSql = """
                SELECT to_regclass('public.ordem_servico_servicos')
                """;

            String createTableSql = """
                CREATE TABLE ordem_servico_servicos (
                    id VARCHAR(36) PRIMARY KEY,
                    ordem_servico_id VARCHAR(36) NOT NULL,
                    servico_id VARCHAR(36) NOT NULL,
                    quantidade INTEGER NOT NULL,
                    valor_total DECIMAL(10, 2) NOT NULL,
                    FOREIGN KEY (ordem_servico_id) REFERENCES ordem_servico(id) ON DELETE CASCADE,
                    FOREIGN KEY (servico_id) REFERENCES servicos(id)
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
                        System.out.println("Tabela 'ordem_servico_servicos' criada com sucesso.");
                    }
                } else {
                    System.out.println("Tabela 'ordem_servico_servicos' já existe.");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Falha ao criar tabela ordem_servico_servicos", e);
            }
        }

        private static void criarUsuarioPadrao() {
            String checkUserSql = """
                SELECT COUNT(*) FROM usuarios WHERE email = 'adm@sistemaos.com'
                """;

            String insertUserSql = """
                INSERT INTO usuarios (id, nome, email, senha, perfil, ativo, data_criacao)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement checkPs = conn.prepareStatement(checkUserSql);
                 ResultSet rs = checkPs.executeQuery()) {

                boolean userExists = false;
                if (rs.next()) {
                    userExists = rs.getInt(1) > 0;
                }

                if (!userExists) {
                    try (PreparedStatement insertPs = conn.prepareStatement(insertUserSql)) {
                        insertPs.setString(1, java.util.UUID.randomUUID().toString());
                        insertPs.setString(2, "ADM");
                        insertPs.setString(3, "adm@sistemaos.com");
                        insertPs.setString(4, "123"); // Em produção, usar hash de senha
                        insertPs.setString(5, "ADMINISTRADOR");
                        insertPs.setBoolean(6, true);
                        insertPs.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                        insertPs.executeUpdate();
                        System.out.println("Usuário padrão 'ADM' criado com sucesso.");
                    }
                } else {
                    System.out.println("Usuário 'ADM' já existe.");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Falha ao criar usuário padrão", e);
            }
        }
        
        public static void corrigirConstraintOrdemServico() {
            System.out.println("Verificando constraint da tabela ordem_servico...");
            
            try (Connection conn = DatabaseConnection.getConnection()) {
                // Remove a constraint antiga se existir
                String dropConstraint = "ALTER TABLE ordem_servico DROP CONSTRAINT IF EXISTS ordem_servico_id_usuario_fkey";
                try (PreparedStatement ps = conn.prepareStatement(dropConstraint)) {
                    ps.executeUpdate();
                    System.out.println("Constraint antiga removida.");
                }
                
                // Adiciona a constraint correta (id_usuario referencia pessoas, não usuarios)
                String addConstraint = "ALTER TABLE ordem_servico ADD CONSTRAINT ordem_servico_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES pessoas(id)";
                try (PreparedStatement ps = conn.prepareStatement(addConstraint)) {
                    ps.executeUpdate();
                    System.out.println("Nova constraint criada: id_usuario -> pessoas(id)");
                }
                
            } catch (SQLException e) {
                System.err.println("Aviso: Não foi possível corrigir a constraint: " + e.getMessage());
                // Não lança exceção para não impedir a inicialização
            }
        }
        
        public static void adicionarColunaDataFechamento() {
            System.out.println("Verificando coluna data_fechamento na tabela ordem_servico...");
            
            try (Connection conn = DatabaseConnection.getConnection()) {
                // Verifica se a coluna já existe
                String checkColumn = """
                    SELECT column_name 
                    FROM information_schema.columns 
                    WHERE table_name = 'ordem_servico' 
                    AND column_name = 'data_fechamento'
                    """;
                
                boolean colunaExiste = false;
                try (PreparedStatement ps = conn.prepareStatement(checkColumn);
                     ResultSet rs = ps.executeQuery()) {
                    colunaExiste = rs.next();
                }
                
                if (!colunaExiste) {
                    // Adiciona a coluna data_fechamento
                    String addColumn = "ALTER TABLE ordem_servico ADD COLUMN data_fechamento TIMESTAMP NULL";
                    try (PreparedStatement ps = conn.prepareStatement(addColumn)) {
                        ps.executeUpdate();
                        System.out.println("Coluna data_fechamento adicionada com sucesso.");
                    }
                } else {
                    System.out.println("Coluna data_fechamento já existe.");
                }
                
            } catch (SQLException e) {
                System.err.println("Aviso: Não foi possível adicionar coluna data_fechamento: " + e.getMessage());
                // Não lança exceção para não impedir a inicialização
            }
        }
    }
}