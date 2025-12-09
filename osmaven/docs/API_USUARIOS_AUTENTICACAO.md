# 👥 API de Usuários e Autenticação

## 📋 Rotas de Usuários

### 1. Criar Usuário
**POST** `/api/usuarios`

**Body:**
```json
{
  "nome": "Admin Sistema",
  "email": "admin@sistemaos.com",
  "senha": "senha123",
  "perfil": "ADMIN"
}
```

**Perfis disponíveis:**
- `ADMIN` - Administrador do sistema
- `TECNICO` - Técnico de manutenção
- `ATENDENTE` - Atendente/Recepcionista

**Response 201:**
```json
{
  "id": "uuid-gerado",
  "nome": "Admin Sistema",
  "email": "admin@sistemaos.com",
  "perfil": "ADMIN",
  "ativo": true,
  "dataCriacao": "2025-12-08T..."
}
```

**Response 400 (Email já cadastrado):**
```json
{
  "tipo": "VALIDACAO",
  "mensagem": "Email já cadastrado"
}
```

---

### 2. Listar Todos os Usuários
**GET** `/api/usuarios`

**Response 200:**
```json
{
  "usuarios": [
    {
      "id": "uuid",
      "nome": "Admin Sistema",
      "email": "admin@sistemaos.com",
      "perfil": "ADMIN",
      "ativo": true,
      "dataCriacao": "2025-12-08T..."
    },
    {
      "id": "uuid",
      "nome": "João Técnico",
      "email": "joao@sistemaos.com",
      "perfil": "TECNICO",
      "ativo": true,
      "dataCriacao": "2025-12-08T..."
    }
  ]
}
```

---

## 🔐 Rota de Autenticação

### Login
**POST** `/api/login`

**Body:**
```json
{
  "email": "admin@sistemaos.com",
  "senha": "senha123"
}
```

**Response 200 (Login bem-sucedido):**
```json
{
  "sucesso": true,
  "mensagem": "Login realizado com sucesso",
  "usuario": {
    "id": "uuid",
    "nome": "Admin Sistema",
    "email": "admin@sistemaos.com",
    "perfil": "ADMIN",
    "ativo": true,
    "dataCriacao": "2025-12-08T..."
  }
}
```

**Response 401 (Senha incorreta):**
```json
{
  "sucesso": false,
  "mensagem": "Senha incorreta",
  "usuario": null
}
```

**Response 401 (Usuário não encontrado):**
```json
{
  "sucesso": false,
  "mensagem": "Usuário não encontrado",
  "usuario": null
}
```

**Response 401 (Usuário inativo):**
```json
{
  "sucesso": false,
  "mensagem": "Usuário inativo",
  "usuario": null
}
```

---

## 🧪 Exemplos de Teste

### Criar primeiro usuário (Admin)
```bash
curl -X POST http://localhost:8080/api/usuarios -H "Content-Type: application/json" -d "{\"nome\":\"Admin Sistema\",\"email\":\"admin@sistemaos.com\",\"senha\":\"admin123\",\"perfil\":\"ADMIN\"}"
```

### Criar usuário técnico
```bash
curl -X POST http://localhost:8080/api/usuarios -H "Content-Type: application/json" -d "{\"nome\":\"João Silva\",\"email\":\"joao@sistemaos.com\",\"senha\":\"joao123\",\"perfil\":\"TECNICO\"}"
```

### Criar usuário atendente
```bash
curl -X POST http://localhost:8080/api/usuarios -H "Content-Type: application/json" -d "{\"nome\":\"Maria Santos\",\"email\":\"maria@sistemaos.com\",\"senha\":\"maria123\",\"perfil\":\"ATENDENTE\"}"
```

### Fazer login
```bash
curl -X POST http://localhost:8080/api/login -H "Content-Type: application/json" -d "{\"email\":\"admin@sistemaos.com\",\"senha\":\"admin123\"}"
```

### Listar todos os usuários
```bash
curl http://localhost:8080/api/usuarios
```

---

## 📊 Estrutura da Tabela SQL

```sql
CREATE TABLE usuarios (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil VARCHAR(50) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,
    data_criacao TIMESTAMP NOT NULL
)
```

---

## ⚠️ Observações Importantes

1. **Segurança Simplificada**: Este sistema NÃO usa tokens (JWT) e as senhas são armazenadas em **texto puro** no banco de dados. 
   - ⚠️ **IMPORTANTE**: Isso é adequado apenas para desenvolvimento/aprendizado
   - ❌ **NÃO USE EM PRODUÇÃO**

2. **Validação de Login**: O sistema apenas verifica se o email existe e se a senha está correta, retornando sucesso/falha

3. **Perfis de Usuário**: 
   - ADMIN - Acesso total ao sistema
   - TECNICO - Acesso a ordens de serviço e serviços
   - ATENDENTE - Acesso a cadastros e atendimento

4. **Email Único**: O sistema não permite cadastrar dois usuários com o mesmo email

5. **Usuários Inativos**: Usuários marcados como inativos não podem fazer login

---

## 🎯 Fluxo de Uso Recomendado

1. **Primeira execução**: Criar usuário ADMIN
2. **Login**: Autenticar com o usuário criado
3. **Usar o sistema**: Com base no perfil do usuário logado, permitir acesso às funcionalidades

### Exemplo de Fluxo Completo:

```bash
# 1. Criar usuário admin
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"Admin","email":"admin@test.com","senha":"123","perfil":"ADMIN"}'

# 2. Fazer login
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@test.com","senha":"123"}'

# 3. Se login bem-sucedido (sucesso: true), usar o sistema normalmente
```

---

## 📝 JSON de Teste Completo

### Criar Usuários de Teste

```json
// Admin
{
  "nome": "Administrador",
  "email": "admin@sistemaos.com",
  "senha": "admin123",
  "perfil": "ADMIN"
}

// Técnico
{
  "nome": "Carlos Técnico",
  "email": "carlos@sistemaos.com",
  "senha": "carlos123",
  "perfil": "TECNICO"
}

// Atendente
{
  "nome": "Ana Atendente",
  "email": "ana@sistemaos.com",
  "senha": "ana123",
  "perfil": "ATENDENTE"
}
```

### Login de Teste

```json
{
  "email": "admin@sistemaos.com",
  "senha": "admin123"
}
```
