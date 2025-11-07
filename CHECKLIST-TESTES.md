# ✅ Checklist de Testes - Sistema de Vendas de Camisas

## 📋 Preparação

- [ ] Abrir terminal onde Maven funciona
- [ ] Navegar até a pasta do projeto
- [ ] Verificar que Java está disponível: `java -version`
- [ ] Verificar que Maven está disponível: `mvn -version`

## 🧪 Execução dos Testes Automatizados

### Opção 1: Script Batch
- [ ] Executar: `executar-testes.bat`
- [ ] Aguardar compilação
- [ ] Verificar resultados

### Opção 2: Maven Direto
- [ ] Compilar: `mvn clean compile`
- [ ] Executar: `mvn exec:java -Dexec.mainClass="com.vendas.TesteSistema"`
- [ ] Verificar resultados

## ✅ Validação dos Resultados

### Teste 11.1 - Cadastro e Venda
- [ ] ✓ Cadastrar cliente para vendas
- [ ] ✓ Cadastrar produto SEM imagem
- [ ] ✓ Cadastrar produto COM imagem
- [ ] ✓ Cadastrar terceiro produto
- [ ] ✓ Produtos aparecem na lista de disponíveis
- [ ] ✓ Registrar venda do primeiro produto
- [ ] ✓ Produto removido do estoque após venda
- [ ] ✓ Cálculo de total gasto
- [ ] ✓ Cálculo de total recebido
- [ ] ✓ Cálculo de lucro/prejuízo
- [ ] ✓ Listar produtos em estoque

### Teste 11.2 - Clientes e Pagamentos
- [ ] ✓ Cadastrar cliente
- [ ] ✓ Registrar primeira venda para cliente
- [ ] ✓ Registrar segunda venda para cliente
- [ ] ✓ Histórico de vendas do cliente
- [ ] ✓ Cálculo de saldo devedor inicial
- [ ] ✓ Registrar pagamento parcial
- [ ] ✓ Saldo atualizado após pagamento parcial
- [ ] ✓ Registrar pagamento total
- [ ] ✓ Saldo zerado após pagamento total
- [ ] ✓ Listar pagamentos do cliente

### Teste 11.3 - Persistência
- [ ] ✓ Cadastrar produto com imagem grande (1MB)
- [ ] ✓ Produtos persistidos
- [ ] ✓ Clientes persistidos
- [ ] ✓ Vendas persistidas
- [ ] ✓ Recuperar produto com imagem grande
- [ ] ✓ Imagem grande mantida
- [ ] ✓ Dados do cliente mantidos

### Teste 11.4 - Validações
- [ ] ✓ Rejeitar produto sem descrição
- [ ] ✓ Rejeitar produto sem tamanho
- [ ] ✓ Rejeitar produto sem valor
- [ ] ✓ Rejeitar produto sem data
- [ ] ✓ Rejeitar cliente sem nome
- [ ] ✓ Rejeitar venda sem produto
- [ ] ✓ Rejeitar venda sem cliente
- [ ] ✓ Rejeitar venda sem valor
- [ ] ✓ Rejeitar venda com valor negativo
- [ ] ✓ Rejeitar pagamento sem valor
- [ ] ✓ Rejeitar pagamento com valor negativo
- [ ] ✓ Rejeitar pagamento com valor zero

## 📊 Resultado Final

- [ ] Total de testes: 37+
- [ ] Taxa de sucesso: 100%
- [ ] Nenhum teste falhou
- [ ] Mensagem: "✓ TODOS OS TESTES PASSARAM!"

## 🖥️ Testes Manuais (Opcional)

### Interface Gráfica
- [ ] Executar aplicação: `mvn exec:java -Dexec.mainClass="com.vendas.Main"`
- [ ] Testar navegação entre telas
- [ ] Cadastrar produto com imagem real
- [ ] Registrar venda completa
- [ ] Verificar painel financeiro
- [ ] Cadastrar cliente
- [ ] Registrar pagamento
- [ ] Verificar saldo devedor

### Fluxo Completo End-to-End
- [ ] Cadastrar 3 produtos diferentes
- [ ] Cadastrar 2 clientes
- [ ] Vender 2 produtos para cliente 1
- [ ] Vender 1 produto para cliente 2
- [ ] Registrar pagamento parcial cliente 1
- [ ] Verificar saldo devedor atualizado
- [ ] Verificar painel financeiro com lucro/prejuízo
- [ ] Fechar e reabrir aplicação
- [ ] Verificar que todos os dados foram mantidos

## 🐛 Troubleshooting

Se encontrar problemas:

- [ ] Verificar se está no terminal correto
- [ ] Verificar se está na pasta raiz do projeto
- [ ] Deletar `vendas.db` e tentar novamente
- [ ] Verificar logs de erro
- [ ] Consultar `TESTES.md` para mais detalhes

## 📝 Notas

- Todos os testes são cumulativos (adicionam dados ao banco)
- Para começar limpo, delete `vendas.db` antes de executar
- Os testes validam tanto funcionalidade quanto validações
- Cobertura completa dos requisitos 1.7, 1.8, 2.2, 2.8, 3.1, 3.4, 3.5, 3.7, 3.8, 4.2, 4.3, 4.4, 4.5, 5.2, 5.3, 5.4, 6.3, 6.4

---

**Última atualização:** 2025-11-07
**Status da Tarefa 11:** ✅ COMPLETO
