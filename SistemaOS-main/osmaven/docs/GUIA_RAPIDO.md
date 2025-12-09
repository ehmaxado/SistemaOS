# 🎯 Guia Rápido - Implementação de Funcionalidades

## 📋 Template para Criar Nova Funcionalidade

Use este guia como checklist ao implementar qualquer nova funcionalidade (Produto, Serviço, OrdemServico, etc.)

---

## 🔄 Ordem de Implementação

### 1️⃣ Domain (Domínio)
### 2️⃣ Application (Aplicação)
### 3️⃣ Infrastructure (Infraestrutura)
### 4️⃣ API (Apresentação)

---

## 📝 Template: Cadastro de Produto

### 1. Entidade (domain/Entidades/Produto.java)

```java
package sistema.os.domain.Entidades;

import java.time.LocalDateTime;
import java.util.UUID;

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

### 2. Interface do Repositório (domain/Interfaces/IProdutoRepository.java)

```java
package sistema.os.domain.Interfaces;

import sistema.os.domain.Entidades.Produto;
import java.util.List;
import java.util.UUID;

public interface IProdutoRepository {
    void salvar(Produto produto);
    Produto buscarPorId(UUID id);
    List<Produto> listarTodos();
}
```

### 3. DTOs (API/DTOs/)

#### Request (API/DTOs/Requests/CriarProdutoRequest.java)
```java
package sistema.os.API.DTOs.Requests;

public record CriarProdutoRequest(
    String nome,
    String descricao,
    double preco
) {}
```

#### Response (API/DTOs/Responses/CriarProdutoResponse.java)
```java
package sistema.os.API.DTOs.Responses;

import java.time.LocalDateTime;

public record CriarProdutoResponse(
    String id,
    String nome,
    String descricao,
    double preco,
    LocalDateTime dataCadastro
) {}
```

### 4. UseCase (Application/UseCase/CriarProdutoUseCase.java)

```java
package sistema.os.Application.UseCase;

import sistema.os.domain.Entidades.Produto;
import sistema.os.domain.Interfaces.IProdutoRepository;
import sistema.os.API.DTOs.Requests.CriarProdutoRequest;
import sistema.os.API.DTOs.Responses.CriarProdutoResponse;

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
            throw new RuntimeException("Falha ao salvar produto no banco de dados", e);
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

### 5. Repository (Infraestrutura/persistence/repository/ProdutoRepository.java)

```java
package sistema.os.Infraestrutura.persistence.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sistema.os.Infraestrutura.persistence.DatabaseConnection;
import sistema.os.domain.Entidades.Produto;
import sistema.os.domain.Interfaces.IProdutoRepository;

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

### 6. Controller (API/Controller/ProdutoController.java)

```java
package sistema.os.API.Controller;

import sistema.os.API.DTOs.Requests.CriarProdutoRequest;
import sistema.os.API.DTOs.Responses.CriarProdutoResponse;
import sistema.os.Application.UseCase.CriarProdutoUseCase;

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

### 7. Routes (API/Routes/ProdutoRoutes.java)

```java
package sistema.os.API.Routes;

import io.javalin.Javalin;
import sistema.os.API.Controller.ProdutoController;
import sistema.os.API.DTOs.Requests.CriarProdutoRequest;
import sistema.os.API.DTOs.Responses.CriarProdutoResponse;
import sistema.os.API.DTOs.Responses.ErroResponse;
import sistema.os.Application.UseCase.CriarProdutoUseCase;
import sistema.os.Infraestrutura.persistence.repository.ProdutoRepository;
import sistema.os.domain.Interfaces.IProdutoRepository;

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

### 8. Registrar no Main.java

```java
package sistema.os;

import io.javalin.Javalin;
import sistema.os.API.Routes.PessoaRoutes;
import sistema.os.API.Routes.ProdutoRoutes;  // ← ADICIONAR
import sistema.os.Infraestrutura.persistence.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.DatabaseInitializer.inicializar();

        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new io.javalin.json.JavalinJackson());
        }).start(8080);

        // Registra rotas
        new PessoaRoutes().register(app);
        new ProdutoRoutes().register(app);  // ← ADICIONAR

        System.out.println("API rodando em http://localhost:8080");
    }
}
```

### 9. Criar Tabela no Banco (DatabaseConnection.java)

```java
// Adicionar no DatabaseInitializer.inicializar()

String createProdutosTableSql = """
    CREATE TABLE produtos (
        id VARCHAR(36) PRIMARY KEY,
        nome VARCHAR(100) NOT NULL,
        descricao TEXT,
        preco DECIMAL(10,2) NOT NULL CHECK (preco > 0),
        data_cadastro TIMESTAMP NOT NULL
    )
    """;

// Verificar se existe e criar se necessário
String checkProdutosTableSql = "SELECT to_regclass('public.produtos')";
// ... (mesmo padrão da tabela pessoas)
```

---

## 🧪 Teste com cURL

```bash
# Criar produto
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Mouse Gamer",
    "descricao": "Mouse RGB 16000 DPI",
    "preco": 150.00
  }'
```

---

## 📋 Checklist de Implementação

Ao implementar uma nova entidade, marque cada item:

- [ ] ✅ Entidade criada com validações
- [ ] ✅ Dois construtores (criação + reconstituição)
- [ ] ✅ Interface do repositório definida
- [ ] ✅ DTOs Request e Response criados
- [ ] ✅ UseCase implementado
- [ ] ✅ Repository implementado
- [ ] ✅ Controller criado
- [ ] ✅ Routes configuradas
- [ ] ✅ Registrado no Main.java
- [ ] ✅ Tabela criada no banco
- [ ] ✅ Testado com cURL/Postman

---

## 🎓 Padrões a Seguir

### Nomenclatura Consistente

| Tipo | Padrão | Exemplo |
|------|--------|---------|
| Entidade | `NomeEntidade` | `Produto`, `Servico` |
| Interface Repo | `INomeRepository` | `IProdutoRepository` |
| Implementação Repo | `NomeRepository` | `ProdutoRepository` |
| Request DTO | `CriarNomeRequest` | `CriarProdutoRequest` |
| Response DTO | `CriarNomeResponse` | `CriarProdutoResponse` |
| UseCase | `CriarNomeUseCase` | `CriarProdutoUseCase` |
| Controller | `NomeController` | `ProdutoController` |
| Routes | `NomeRoutes` | `ProdutoRoutes` |

### Estrutura de Pastas

```
domain/
  Entidades/
    Produto.java
  Interfaces/
    IProdutoRepository.java

Application/
  UseCase/
    CriarProdutoUseCase.java

Infraestrutura/
  persistence/
    repository/
      ProdutoRepository.java

API/
  DTOs/
    Requests/
      CriarProdutoRequest.java
    Responses/
      CriarProdutoResponse.java
  Controller/
    ProdutoController.java
  Routes/
    ProdutoRoutes.java
```

---

## 🚀 Próximos Passos

Após implementar o **Criar**, implemente:

1. **Buscar por ID** (GET `/api/produtos/{id}`)
2. **Listar Todos** (GET `/api/produtos`)
3. **Atualizar** (PUT `/api/produtos/{id}`)
4. **Excluir** (DELETE `/api/produtos/{id}`)

Para cada operação, siga o mesmo padrão:
- Criar UseCase
- Criar DTOs (se necessário)
- Adicionar método no Controller
- Adicionar rota no Routes

---

**Dica**: Sempre comece pelo **domínio** (entidade + interface) e vá subindo as camadas até a API.
