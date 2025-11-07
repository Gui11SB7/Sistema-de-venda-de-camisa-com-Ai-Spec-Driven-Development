# Guia de Testes - Sistema de Vendas de Camisas

## Visão Geral

Este documento descreve como executar os testes automatizados do sistema, conforme especificado na tarefa 11 do plano de implementação.

## Testes Implementados

### 11.1 - Fluxo Completo de Cadastro e Venda
- ✓ Cadastrar múltiplos produtos com e sem imagem
- ✓ Verificar que produtos aparecem na lista de disponíveis
- ✓ Registrar vendas e verificar remoção do estoque
- ✓ Validar cálculos no painel de gerenciamento

### 11.2 - Gerenciamento de Clientes e Pagamentos
- ✓ Cadastrar clientes
- ✓ Realizar múltiplas vendas para o mesmo cliente
- ✓ Verificar cálculo de saldo devedor
- ✓ Registrar pagamentos parciais e totais
- ✓ Validar atualização de saldo

### 11.3 - Persistência de Dados
- ✓ Cadastrar dados diversos
- ✓ Simular fechamento e reabertura da aplicação
- ✓ Verificar que todos os dados foram mantidos
- ✓ Testar com imagens grandes (1MB)

### 11.4 - Validações e Tratamento de Erros
- ✓ Tentar cadastrar produto sem campos obrigatórios
- ✓ Tentar registrar venda sem selecionar produto/cliente
- ✓ Verificar mensagens de erro
- ✓ Testar com valores inválidos (negativos, nulos, zero)

## Como Executar os Testes

### Opção 1: Usando o Script Batch (Windows)

1. Abra o terminal onde Maven está configurado
2. Execute o script:
   ```batch
   executar-testes.bat
   ```

### Opção 2: Usando Maven Diretamente

1. Compile o projeto:
   ```bash
   mvn clean compile
   ```

2. Execute a classe de testes:
   ```bash
   mvn exec:java "-Dexec.mainClass=com.vendas.TesteSistema"
   ```

### Opção 3: Usando Java Diretamente

1. Compile o projeto:
   ```bash
   javac -cp "target/classes;lib/*" src/main/java/com/vendas/TesteSistema.java
   ```

2. Execute os testes:
   ```bash
   java -cp "target/classes;lib/*" com.vendas.TesteSistema
   ```

## Interpretando os Resultados

Os testes exibem:
- ✓ para testes que passaram
- ✗ para testes que falharam
- Detalhes de cada verificação
- Resumo final com estatísticas

### Exemplo de Saída Esperada

```
=================================================
  SISTEMA DE VENDAS DE CAMISAS - TESTES
=================================================

Inicializando banco de dados de teste...
✓ Banco de dados inicializado

=================================================
  TESTE 11.1: Fluxo Completo de Cadastro e Venda
=================================================

✓ Cadastrar cliente para vendas
✓ Cadastrar produto SEM imagem
✓ Cadastrar produto COM imagem
✓ Cadastrar terceiro produto
✓ Produtos aparecem na lista de disponíveis
  → 3 produtos disponíveis
✓ Registrar venda do primeiro produto
✓ Produto removido do estoque após venda
  → 2 produtos disponíveis após venda
✓ Cálculo de total gasto
  → Total gasto: R$ 100.00
✓ Cálculo de total recebido
  → Total recebido: R$ 50.00
✓ Cálculo de lucro/prejuízo
  → Lucro: R$ -50.00
✓ Listar produtos em estoque
  → 2 produtos em estoque

...

=================================================
  RESUMO DOS TESTES
=================================================
Total de testes: 45
Passaram: 45 ✓
Falharam: 0 ✗
Taxa de sucesso: 100%
=================================================

✓ TODOS OS TESTES PASSARAM!
```

## Testes Manuais Adicionais

Além dos testes automatizados, recomenda-se testar manualmente:

1. **Interface Gráfica**
   - Navegação entre telas
   - Responsividade dos componentes
   - Formatação de valores monetários
   - Preview de imagens

2. **Fluxos Completos**
   - Cadastrar produto → Registrar venda → Verificar painel financeiro
   - Cadastrar cliente → Múltiplas vendas → Pagamentos → Verificar saldo

3. **Casos Extremos**
   - Imagens muito grandes (>5MB)
   - Muitos produtos cadastrados (>100)
   - Valores monetários com muitas casas decimais

## Requisitos Cobertos

Os testes cobrem os seguintes requisitos do documento de requisitos:

- **Requisito 1.7, 1.8**: Validação e confirmação de cadastro de produtos
- **Requisito 2.2, 2.8**: Lista de produtos disponíveis e confirmação de vendas
- **Requisito 3.1, 3.4, 3.5, 3.7, 3.8**: Gerenciamento de clientes e pagamentos
- **Requisito 4.2, 4.3, 4.4, 4.5**: Cálculos financeiros no painel de gerenciamento
- **Requisito 5.2, 5.3, 5.4**: Persistência de dados local
- **Requisito 6.3, 6.4**: Validações e mensagens de erro

## Banco de Dados de Teste

Os testes utilizam o mesmo banco de dados da aplicação (`vendas.db`). 

**IMPORTANTE**: Os testes adicionam dados ao banco. Se desejar começar com um banco limpo:

1. Feche a aplicação
2. Delete o arquivo `vendas.db`
3. Execute os testes novamente

## Troubleshooting

### Erro: "mvn não é reconhecido"
- Certifique-se de estar no terminal onde Maven está configurado
- Verifique se Maven está no PATH: `mvn -version`

### Erro: "java não é reconhecido"
- Certifique-se de que Java está instalado
- Verifique se Java está no PATH: `java -version`

### Testes falhando
- Verifique se o banco de dados não está corrompido
- Delete `vendas.db` e execute novamente
- Verifique os logs de erro para mais detalhes

## Próximos Passos

Após executar os testes com sucesso:

1. Revise os resultados
2. Execute a aplicação GUI para testes manuais
3. Verifique se todos os requisitos foram atendidos
4. Documente quaisquer problemas encontrados
