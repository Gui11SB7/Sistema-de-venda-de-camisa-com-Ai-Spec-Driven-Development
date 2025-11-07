# 🚀 Início Rápido - Testes do Sistema

## ⚡ Execução Rápida (3 passos)

### 1️⃣ Abra o terminal correto
- Use o terminal onde `mvn` e `java` funcionam

### 2️⃣ Execute o script
```batch
executar-testes.bat
```

### 3️⃣ Aguarde os resultados
Você verá algo como:
```
✓ TODOS OS TESTES PASSARAM!
```

---

## 📚 Documentação Disponível

| Arquivo | Descrição |
|---------|-----------|
| `COMO-EXECUTAR-TESTES.txt` | 📄 Guia rápido em texto simples |
| `TESTES.md` | 📖 Documentação completa |
| `CHECKLIST-TESTES.md` | ✅ Checklist de validação |
| `RESUMO-TAREFA-11.md` | 📊 Resumo da implementação |

---

## 🎯 O que será testado?

### ✅ Teste 11.1: Cadastro e Venda
- Cadastra produtos com/sem imagem
- Registra vendas
- Valida cálculos financeiros

### ✅ Teste 11.2: Clientes e Pagamentos
- Cadastra clientes
- Múltiplas vendas
- Pagamentos parciais/totais

### ✅ Teste 11.3: Persistência
- Salva dados no banco
- Recupera dados
- Testa imagens grandes

### ✅ Teste 11.4: Validações
- Campos obrigatórios
- Valores inválidos
- Mensagens de erro

---

## 🔧 Comandos Alternativos

Se o script não funcionar, use:

```bash
# Compilar
mvn clean compile

# Executar testes
mvn exec:java -Dexec.mainClass="com.vendas.TesteSistema"
```

---

## 🎨 Executar a Aplicação GUI

Para testar manualmente a interface:

```bash
mvn exec:java -Dexec.mainClass="com.vendas.Main"
```

---

## ❓ Problemas?

1. **Maven não encontrado?**
   - Verifique se está no terminal correto
   - Execute: `mvn -version`

2. **Testes falhando?**
   - Delete o arquivo `vendas.db`
   - Execute novamente

3. **Mais ajuda?**
   - Consulte `TESTES.md`
   - Veja `COMO-EXECUTAR-TESTES.txt`

---

## 📈 Resultado Esperado

```
=================================================
  RESUMO DOS TESTES
=================================================
Total de testes: 37+
Passaram: 37+ ✓
Falharam: 0 ✗
Taxa de sucesso: 100%
=================================================

✓ TODOS OS TESTES PASSARAM!
```

---

**Pronto para começar? Execute:** `executar-testes.bat` 🚀
