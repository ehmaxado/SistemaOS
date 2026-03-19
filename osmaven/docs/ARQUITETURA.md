# 📚 Documentação - Sistema de Ordem de Serviço

## 🏗️ Arquitetura do Sistema

Este projeto segue os princípios de **Domain-Driven Design (DDD)** e **Clean Architecture**, organizando o código em camadas bem definidas.

---

## 📂 Estrutura de Pastas

```
osmaven/
├── src/main/java/sistema/os/
│   ├── Main.java                          # Ponto de entrada da aplicação
│   ├── API/                               # Camada de Apresentação
│   │   ├── Controller/                    # Controllers (delegam para UseCases)
│   │   ├── DTOs/                          # Objetos de transferência de dados
│   │   │   ├── Requests/                  # DTOs de entrada (Request)
│   │   │   └── Responses/                 # DTOs de saída (Response)
│   │   ├── Routes/                        # Configuração de rotas HTTP
│   │   └── view/                          # Views (se necessário)
│   ├── Application/                       # Camada de Aplicação
│   │   └── UseCase/                       # Casos de uso (lógica de orquestração)
│   ├── domain/                            # Camada de Domínio (coração do sistema)
│   │   ├── Entidades/                     # Entidades do domínio
│   │   ├── ValueObjects/                  # Objetos de valor (imutáveis)
│   │   ├── Enums/                         # Enumerações
│   │   └── Interfaces/                    # Interfaces de repositórios
│   └── Infraestrutura/                    # Camada de Infraestrutura
│       └── persistence/                   # Persistência de dados
│           ├── DatabaseConnection.java    # Conexão com banco de dados
│           └── repository/                # Implementação dos repositórios
```

---

## 🔄 Fluxo de Dados (Request → Response)

```
┌─────────────┐
│   Cliente   │  (POST /api/pessoas)
└──────┬──────┘
       │
       ▼
┌─────────────────────────────────────────┐
│  1. PessoaRoutes                        │  ← Recebe requisição HTTP
│     - Converte JSON → CriarPessoaRequest│
│     - Trata exceções                    │
└──────┬──────────────────────────────────┘
       │
       ▼
┌─────────────────────────────────────────┐
│  2. PessoaController                    │  ← Delega para UseCase
│     - Chama UseCase                     │
└──────┬──────────────────────────────────┘
       │
       ▼
┌─────────────────────────────────────────┐
│  3. CriarPessoaUseCase                  │  ← Orquestra operação
│     - Converte Request → ValueObjects   │
│     - Cria Entidade Pessoa              │
│     - Chama Repository.salvar()         │
│     - Converte Pessoa → Response        │
└──────┬──────────────────────────────────┘
       │
       ▼
┌─────────────────────────────────────────┐
│  4. Pessoa (Entidade)                   │  ← Executa validações
│     - Valida regras de negócio          │
│     - Gera UUID, define status ATIVO    │
└──────┬──────────────────────────────────┘
       │
       ▼
┌─────────────────────────────────────────┐
│  5. PessoaRepository                    │  ← Persiste no banco
│     - INSERT no PostgreSQL              │
└──────┬──────────────────────────────────┘
       │
       ▼
┌─────────────────────────────────────────┐
│  6. Response (CriarPessoaResponse)      │  ← Retorna JSON
│     - Retorna HTTP 201 Created          │
└─────────────────────────────────────────┘
```

---

## 🎯 Responsabilidades de Cada Camada

### 1️⃣ **API (Camada de Apresentação)**

**Responsabilidade**: Comunicação com o mundo externo (HTTP, JSON)

#### **Routes** (`PessoaRoutes.java`)
- Registra endpoints HTTP (GET, POST, PUT, DELETE)
- Converte JSON → DTOs (Request)
- Trata exceções e retorna códigos HTTP apropriados
- Instancia Controllers e UseCases

**Exemplo de implementação:**
```java
public class ProdutoRoutes {
    private final ProdutoController controller;

    public ProdutoRoutes() {
        IProdutoRepository repository = new ProdutoRepository();
        var useCase = new CriarProdutoUseCase(repository);
        this.controller = new ProdutoController(useCase);
    }

    // Registra rota POST /api/produtos
    public void register(Javalin app) {
        app.post("/api/produtos", ctx -> {
            try {
                CriarProdutoRequest request = ctx.bodyAsClass(CriarProdutoRequest.class);
                CriarProdutoResponse response = controller.criar(request);
                ctx.status(201).json(response);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });
    }
}
```

#### **Controllers** (`PessoaController.java`)
- Recebe DTOs (Request)
- Delega para UseCases
- Retorna DTOs (Response)
- **NÃO contém lógica de negócio**

**Exemplo de implementação:**
```java
public class ProdutoController {
    private final CriarProdutoUseCase criarProdutoUseCase;

    public ProdutoController(CriarProdutoUseCase criarProdutoUseCase) {
        this.criarProdutoUseCase = criarProdutoUseCase;
    }

    // Delega criação de produto para o use case
    public CriarProdutoResponse criar(CriarProdutoRequest request) {
        return criarProdutoUseCase.executar(request);
    }
}
```

#### **DTOs** (Data Transfer Objects)
- **Requests**: Dados recebidos do cliente
- **Responses**: Dados enviados ao cliente
- Usam `record` do Java (imutáveis por padrão)

**Exemplo:**
```java
// Request
public record CriarProdutoRequest(
    String nome,
    String descricao,
    double preco
) {}

// Response
public record CriarProdutoResponse(
    String id,
    String nome,
    String descricao,
    double preco,
    LocalDateTime dataCadastro
) {}
```

---

### 2️⃣ **Application (Camada de Aplicação)**

**Responsabilidade**: Orquestrar casos de uso (não contém regras de negócio)

#### **UseCases** (`CriarPessoaUseCase.java`)
- Recebe DTOs (Request)
- Converte strings → Value Objects
- Cria entidades (onde as validações ocorrem)
- Chama repositórios
- Converte entidades → DTOs (Response)

**Regras:**
- ✅ Orquestra operações
- ✅ Chama múltiplos repositórios se necessário
- ❌ NÃO contém validações de negócio (isso é da Entidade)
- ❌ NÃO conhece HTTP, JSON ou banco de dados

**Exemplo de implementação:**
```java
public class CriarProdutoUseCase {
    private final IProdutoRepository repository;

    public CriarProdutoUseCase(IProdutoRepository repository) {
        this.repository = repository;
    }

    // Cria e persiste novo produto
    public CriarProdutoResponse executar(CriarProdutoRequest request) {
        // Criar entidade (validações executadas no construtor)
        Produto produto = new Produto(
            request.nome(), 
            request.descricao(), 
            request.preco()
        );

        // Persistir
        try {
            repository.salvar(produto);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar produto no banco de dados", e);
        }

        // Retornar DTO de resposta
        return new CriarProdutoResponse(
            produto.getId().toString(),
            produto.getNome(),
            produto.getDescricao(),
            produto.getPreco(),
            produto.getDataCadastro()
        );
    }
}
```

---

### 3️⃣ **Domain (Camada de Domínio) - Coração do Sistema**

**Responsabilidade**: Contém TODAS as regras de negócio

#### **Entidades** (`Pessoa.java`)
- Representam conceitos importantes do negócio
- Têm identidade única (UUID)
- **Contêm validações de negócio**
- **NÃO são anêmicas** (têm comportamento, não só getters/setters)

**Regras para criar uma Entidade:**
1. Todas as validações de negócio devem estar no **construtor**
2. Campos devem ser `final` (imutabilidade)
3. Ter dois construtores:
   - **Criação**: valida tudo e gera UUID
   - **Reconstituição**: para buscar do banco sem validar

**Exemplo de implementação:**
```java
public class Produto {
    private final UUID id;
    private final String nome;
    private final String descricao;
    private final double preco;
    private final LocalDateTime dataCadastro;

    // Cria novo produto com validações de negócio
    public Produto(String nome, String descricao, double preco) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        
        this.id = UUID.randomUUID();
        this.nome = nome.trim();
        this.descricao = descricao != null ? descricao.trim() : "";
        this.preco = preco;
        this.dataCadastro = LocalDateTime.now();
    }

    // Reconstrói produto existente do banco de dados
    public Produto(UUID id, String nome, String descricao, double preco, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.dataCadastro = dataCadastro;
    }

    // Getters
    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public double getPreco() { return preco; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }
}
```

#### **Value Objects** (`CpfCnpj.java`, `Telefone.java`)
- Objetos sem identidade própria (comparados por valor)
- **Sempre imutáveis** (campos `final`)
- **DEVEM implementar `equals()` e `hashCode()`**
- Validam seus próprios dados no construtor

**Regras para criar um Value Object:**
1. Todos os campos devem ser `final`
2. Implementar `equals()` e `hashCode()`
3. Validar no construtor
4. Ter apenas um getter: `getValor()`

**Exemplo de implementação:**
```java
public class Preco {
    private final double valor;

    public Preco(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        this.valor = valor;
    }

    public double getValor() { return valor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Preco preco = (Preco) o;
        return Double.compare(preco.valor, valor) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() { return String.format("R$ %.2f", valor); }
}
```

#### **Enums** (`StatusPessoa.java`, `TipoPessoa.java`)
- Valores fixos e pré-definidos
- Usados para status, tipos, categorias, etc.

**Exemplo:**
```java
public enum StatusProduto {
    DISPONIVEL, INDISPONIVEL, DESCONTINUADO
}
```

#### **Interfaces de Repositório** (`IPessoaRepository.java`)
- Definem **o que** o repositório deve fazer
- Não sabem **como** é implementado (SQL, NoSQL, arquivo, etc.)
- Usam tipos do domínio (Entidades, Value Objects)

**Exemplo:**
```java
public interface IProdutoRepository {
    void salvar(Produto produto);
    Produto buscarPorId(UUID id);
    List<Produto> listarTodos();
}
```

---

### 4️⃣ **Infraestrutura (Camada de Infraestrutura)**

**Responsabilidade**: Implementações técnicas (banco, APIs externas, arquivos)

#### **Repositories** (`PessoaRepository.java`)
- Implementam as interfaces do domínio
- Fazem SQL, MongoDB, arquivos, etc.
- Convertem dados técnicos → Entidades do domínio

**Regras:**
- ✅ Conhecem SQL/banco de dados
- ✅ Usam o **construtor de reconstituição** ao buscar do banco
- ❌ NÃO contém lógica de negócio

**Exemplo de implementação:**
```java
public class ProdutoRepository implements IProdutoRepository {
    
    // Persiste produto no banco de dados
    @Override
    public void salvar(Produto produto) {
        String sql = "INSERT INTO produtos (id, nome, descricao, preco, data_cadastro) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produto.getId().toString());
            ps.setString(2, produto.getNome());
            ps.setString(3, produto.getDescricao());
            ps.setDouble(4, produto.getPreco());
            ps.setTimestamp(5, Timestamp.valueOf(produto.getDataCadastro()));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto", e);
        }
    }

    // Busca produto por ID
    @Override
    public Produto buscarPorId(UUID id) {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id.toString());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                // Usa construtor de reconstituição
                return new Produto(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getDouble("preco"),
                    rs.getTimestamp("data_cadastro").toLocalDateTime()
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto", e);
        }
    }

    // Lista todos os produtos
    @Override
    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos ORDER BY nome";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                produtos.add(new Produto(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getDouble("preco"),
                    rs.getTimestamp("data_cadastro").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos", e);
        }
        
        return produtos;
    }
}
```

---

## 🚀 Como Implementar Novas Funcionalidades

### Exemplo: Cadastro de Produto

#### **Passo 1: Criar a Entidade no Domínio**

```java
// domain/Entidades/Produto.java
public class Produto {
    private final UUID id;
    private final String nome;
    private final String descricao;
    private final double preco;
    private final LocalDateTime dataCadastro;

    // Construtor de criação (com validações)
    public Produto(String nome, String descricao, double preco) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        this.id = UUID.randomUUID();
        this.nome = nome.trim();
        this.descricao = descricao;
        this.preco = preco;
        this.dataCadastro = LocalDateTime.now();
    }

    // Construtor de reconstituição (sem validações)
    public Produto(UUID id, String nome, String descricao, double preco, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.dataCadastro = dataCadastro;
    }

    // Getters
    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public double getPreco() { return preco; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }
}
```

#### **Passo 2: Criar a Interface do Repositório**

```java
// domain/Interfaces/IProdutoRepository.java
public interface IProdutoRepository {
    void salvar(Produto produto);
    Produto buscarPorId(UUID id);
    List<Produto> listarTodos();
}
```

#### **Passo 3: Criar os DTOs**

```java
// API/DTOs/Requests/CriarProdutoRequest.java
public record CriarProdutoRequest(
    String nome,
    String descricao,
    double preco
) {}

// API/DTOs/Responses/CriarProdutoResponse.java
public record CriarProdutoResponse(
    String id,
    String nome,
    String descricao,
    double preco,
    LocalDateTime dataCadastro
) {}
```

#### **Passo 4: Criar o UseCase**

```java
// Application/UseCase/CriarProdutoUseCase.java
public class CriarProdutoUseCase {
    private final IProdutoRepository repository;

    public CriarProdutoUseCase(IProdutoRepository repository) {
        this.repository = repository;
    }

    // Cria e persiste novo produto
    public CriarProdutoResponse executar(CriarProdutoRequest request) {
        Produto produto = new Produto(
            request.nome(), 
            request.descricao(), 
            request.preco()
        );

        try {
            repository.salvar(produto);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar produto", e);
        }

        return new CriarProdutoResponse(
            produto.getId().toString(),
            produto.getNome(),
            produto.getDescricao(),
            produto.getPreco(),
            produto.getDataCadastro()
        );
    }
}
```

#### **Passo 5: Implementar o Repositório**

```java
// Infraestrutura/persistence/repository/ProdutoRepository.java
public class ProdutoRepository implements IProdutoRepository {
    
    // Persiste produto no banco de dados
    @Override
    public void salvar(Produto produto) {
        String sql = "INSERT INTO produtos (id, nome, descricao, preco, data_cadastro) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produto.getId().toString());
            ps.setString(2, produto.getNome());
            ps.setString(3, produto.getDescricao());
            ps.setDouble(4, produto.getPreco());
            ps.setTimestamp(5, Timestamp.valueOf(produto.getDataCadastro()));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto", e);
        }
    }

    @Override
    public Produto buscarPorId(UUID id) {
        // Implementar conforme exemplo anterior
        return null;
    }

    @Override
    public List<Produto> listarTodos() {
        // Implementar conforme exemplo anterior
        return new ArrayList<>();
    }
}
```

#### **Passo 6: Criar o Controller**

```java
// API/Controller/ProdutoController.java
public class ProdutoController {
    private final CriarProdutoUseCase criarProdutoUseCase;

    public ProdutoController(CriarProdutoUseCase criarProdutoUseCase) {
        this.criarProdutoUseCase = criarProdutoUseCase;
    }

    // Delega criação de produto para o use case
    public CriarProdutoResponse criar(CriarProdutoRequest request) {
        return criarProdutoUseCase.executar(request);
    }
}
```

#### **Passo 7: Criar as Routes**

```java
// API/Routes/ProdutoRoutes.java
public class ProdutoRoutes {
    private final ProdutoController controller;

    public ProdutoRoutes() {
        IProdutoRepository repository = new ProdutoRepository();
        var useCase = new CriarProdutoUseCase(repository);
        this.controller = new ProdutoController(useCase);
    }

    // Registra rota POST /api/produtos
    public void register(Javalin app) {
        app.post("/api/produtos", ctx -> {
            try {
                CriarProdutoRequest request = ctx.bodyAsClass(CriarProdutoRequest.class);
                CriarProdutoResponse response = controller.criar(request);
                ctx.status(201).json(response);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(new ErroResponse("VALIDACAO", e.getMessage()));
            } catch (Exception e) {
                ctx.status(500).json(new ErroResponse("ERRO", "Erro interno"));
            }
        });
    }
}
```

#### **Passo 8: Registrar no Main.java**

```java
public class Main {
    public static void main(String[] args) {
        DatabaseConnection.DatabaseInitializer.inicializar();

        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new io.javalin.json.JavalinJackson());
        }).start(8080);

        // Registra rotas
        new PessoaRoutes().register(app);
        new ProdutoRoutes().register(app);  // ← ADICIONAR AQUI

        System.out.println("API rodando em http://localhost:8080");
    }
}
```

#### **Passo 9: Criar a tabela no banco**

Adicionar no `DatabaseInitializer`:

```java
String createProdutosTableSql = """
    CREATE TABLE produtos (
        id VARCHAR(36) PRIMARY KEY,
        nome VARCHAR(100) NOT NULL,
        descricao TEXT,
        preco DECIMAL(10,2) NOT NULL,
        data_cadastro TIMESTAMP NOT NULL
    )
    """;
```

---

## ✅ Checklist para Novas Funcionalidades

Ao implementar uma nova funcionalidade (ex: Produto, Serviço, OrdemServico), siga esta ordem:

- [ ] 1. Criar **Entidade** no `domain/Entidades/`
- [ ] 2. Criar **Interface do Repositório** no `domain/Interfaces/`
- [ ] 3. Criar **DTOs** (Request e Response) no `API/DTOs/`
- [ ] 4. Criar **UseCase** no `Application/UseCase/`
- [ ] 5. Implementar **Repository** no `Infraestrutura/persistence/repository/`
- [ ] 6. Criar **Controller** no `API/Controller/`
- [ ] 7. Criar **Routes** no `API/Routes/`
- [ ] 8. Registrar **Routes** no `Main.java`
- [ ] 9. Criar **tabela no banco** (no `DatabaseInitializer`)

---

## 🎓 Princípios DDD Aplicados

### ✅ **Entidades Ricas (Não Anêmicas)**
- Validações estão **dentro** da entidade
- Entidades têm comportamento, não são apenas dados

### ✅ **Value Objects Imutáveis**
- `CpfCnpj`, `Telefone` são imutáveis
- Comparados por valor (implementam `equals/hashCode`)

### ✅ **Inversão de Dependência**
- Domínio define **interfaces** (`IPessoaRepository`)
- Infraestrutura **implementa** essas interfaces
- Camadas externas dependem do domínio, não o contrário

### ✅ **Separação de Responsabilidades**
- **API**: recebe/envia dados
- **Application**: orquestra
- **Domain**: regras de negócio
- **Infraestrutura**: detalhes técnicos

---

## 🔧 Tecnologias Utilizadas

- **Java 21** (LTS)
- **Javalin** (framework web leve)
- **PostgreSQL** (banco de dados)
- **JDBC** (conexão com banco)
- **Records** (DTOs imutáveis)

---

## 📝 Convenções de Código

### Nomenclatura
- **Entidades**: `Pessoa`, `Produto`, `OrdemServico`
- **Value Objects**: `CpfCnpj`, `Telefone`, `Preco`
- **Interfaces**: `IPessoaRepository`, `IProdutoRepository`
- **Implementações**: `PessoaRepository`, `ProdutoRepository`
- **DTOs Request**: `CriarPessoaRequest`, `EditarPessoaRequest`
- **DTOs Response**: `CriarPessoaResponse`, `PessoaResponse`
- **UseCases**: `CriarPessoaUseCase`, `EditarPessoaUseCase`
- **Controllers**: `PessoaController`, `ProdutoController`
- **Routes**: `PessoaRoutes`, `ProdutoRoutes`

### Comentários
- Um comentário simples por método explicando o que ele faz
- Sem comentários inline desnecessários

### Estrutura de Métodos
```java
// Descrição simples do que o método faz
public ReturnType nomeMetodo(Parametros params) {
    // Código
}
```

---

## 🚨 Erros Comuns a Evitar

❌ **NÃO colocar validações de negócio no UseCase**
```java
// ERRADO
public class CriarPessoaUseCase {
    public void executar(String nome) {
        if (nome.isEmpty()) throw new Exception(); // ← ERRADO
        Pessoa p = new Pessoa(nome);
    }
}
```

✅ **Validações devem estar na Entidade**
```java
// CERTO
public class Pessoa {
    public Pessoa(String nome) {
        if (nome.isEmpty()) throw new Exception(); // ← CERTO
        this.nome = nome;
    }
}
```

❌ **NÃO retornar Entidade do UseCase**
```java
// ERRADO
public Pessoa executar(Request r) {
    return new Pessoa(...); // ← Retorna entidade
}
```

✅ **Retornar DTO (Response)**
```java
// CERTO
public CriarPessoaResponse executar(Request r) {
    Pessoa p = new Pessoa(...);
    return new CriarPessoaResponse(...); // ← Retorna DTO
}
```

❌ **NÃO esquecer `equals()` e `hashCode()` nos Value Objects**
```java
// ERRADO - sem equals/hashCode
public class Cpf {
    private final String valor;
}
```

✅ **Sempre implementar**
```java
// CERTO
public class Cpf {
    private final String valor;
    
    @Override
    public boolean equals(Object o) { ... }
    
    @Override
    public int hashCode() { ... }
}
```

---

## 📞 Suporte

Para dúvidas sobre a arquitetura, consulte:
1. Esta documentação
2. O código de exemplo implementado em `Pessoa`
3. Os princípios de DDD e Clean Architecture

---

**Versão**: 1.0  
**Data**: Novembro 2025  
**Autor**: Sistema de OS - Projeto Acadêmico
