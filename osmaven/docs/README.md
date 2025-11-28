# 📚 Documentação do Sistema

## Documentos Disponíveis

### 1. [ARQUITETURA.md](./ARQUITETURA.md)
Documentação completa sobre a arquitetura do sistema:
- Estrutura de pastas e camadas
- Fluxo de dados (Request → Response)
- Responsabilidades de cada camada
- Princípios DDD aplicados
- Exemplos detalhados de implementação
- Erros comuns e como evitá-los

**Quando consultar**: Para entender a arquitetura geral, princípios DDD e como as camadas se comunicam.

---

### 2. [GUIA_RAPIDO.md](./GUIA_RAPIDO.md)
Template prático para implementação rápida:
- Checklist de implementação
- Templates de código prontos para copiar
- Exemplo completo: Cadastro de Produto
- Padrões de nomenclatura
- Como testar com cURL

**Quando consultar**: Para implementar novas funcionalidades rapidamente seguindo o padrão do projeto.

---

## 🎯 Como Usar Esta Documentação

### Se você é novo no projeto:
1. Leia o **ARQUITETURA.md** completo
2. Entenda o exemplo de `Pessoa` (já implementado)
3. Use o **GUIA_RAPIDO.md** para implementar sua primeira funcionalidade

### Se você já conhece o projeto:
- Use o **GUIA_RAPIDO.md** como template
- Consulte o **ARQUITETURA.md** quando tiver dúvidas sobre princípios

---

## 📖 Índice Rápido

### Conceitos Importantes
- [Camadas da Aplicação](./ARQUITETURA.md#-estrutura-de-pastas)
- [Fluxo de Dados](./ARQUITETURA.md#-fluxo-de-dados-request--response)
- [Entidades vs Value Objects](./ARQUITETURA.md#-domain-camada-de-domínio---coração-do-sistema)
- [Princípios DDD](./ARQUITETURA.md#-princípios-ddd-aplicados)

### Implementação Prática
- [Como criar uma Entidade](./GUIA_RAPIDO.md#1-entidade-domainentidadesprodutojava)
- [Como criar um UseCase](./GUIA_RAPIDO.md#4-usecase-applicationusecasecriarproductousecasejava)
- [Como criar um Repository](./GUIA_RAPIDO.md#5-repository-infraestruturapersistencerepositoryproductorepositoryjavajava)
- [Checklist completo](./GUIA_RAPIDO.md#-checklist-de-implementação)

---

## 🔍 Busca Rápida

**Precisa de:**
- Um exemplo completo? → [GUIA_RAPIDO.md](./GUIA_RAPIDO.md)
- Entender arquitetura? → [ARQUITETURA.md](./ARQUITETURA.md)
- Ver fluxo de dados? → [ARQUITETURA.md - Fluxo](./ARQUITETURA.md#-fluxo-de-dados-request--response)
- Implementar CRUD? → [GUIA_RAPIDO.md - Próximos Passos](./GUIA_RAPIDO.md#-próximos-passos)
- Entender DDD? → [ARQUITETURA.md - Princípios](./ARQUITETURA.md#-princípios-ddd-aplicados)

---

## 💡 Dicas

1. **Sempre comece pelo domínio** (Entidade + Interface)
2. **Suba as camadas** até chegar na API
3. **Use os templates** do GUIA_RAPIDO.md
4. **Siga o padrão** de nomenclatura
5. **Teste** após cada implementação

---

## 🆘 Ajuda

Se você não encontrou o que precisa:
1. Consulte o código implementado em `Pessoa` (exemplo funcional)
2. Revise a seção de [Erros Comuns](./ARQUITETURA.md#-erros-comuns-a-evitar)
3. Verifique o [Checklist](./GUIA_RAPIDO.md#-checklist-de-implementação)

---

**Versão da Documentação**: 1.0  
**Última Atualização**: Novembro 2025
