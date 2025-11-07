# Sistema de Vendas de Camisas

Sistema de gerenciamento de vendas de camisas desenvolvido em Java com interface gráfica Swing e banco de dados SQLite local.

## Estrutura do Projeto

```
sistema-vendas-camisas/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── vendas/
│                   ├── Main.java              # Classe principal
│                   ├── model/                 # Entidades (Produto, Cliente, Venda, Pagamento)
│                   ├── dao/                   # Acesso ao banco de dados
│                   ├── controller/            # Lógica de negócio
│                   ├── view/                  # Interface gráfica (Swing)
│                   └── util/                  # Classes utilitárias
├── pom.xml                                    # Configuração Maven
└── README.md
```

## Tecnologias

- **Java 8+**
- **Swing** - Interface gráfica
- **SQLite** - Banco de dados local
- **Maven** - Gerenciamento de dependências

## Como Executar

### Pré-requisitos
- Java JDK 8 ou superior
- Maven 3.6 ou superior

### Compilar o projeto
```bash
mvn clean compile
```

### Executar a aplicação
```bash
mvn exec:java -Dexec.mainClass="com.vendas.Main"
```

### Gerar JAR executável
```bash
mvn clean package
java -jar target/sistema-vendas-camisas-1.0.0.jar
```

## Funcionalidades

- ✅ Cadastro de produtos no estoque
- ✅ Upload de imagens dos produtos
- ✅ Registro de vendas
- ✅ Gerenciamento de clientes
- ✅ Controle de pagamentos e saldo devedor
- ✅ Painel de gerenciamento financeiro
- ✅ Persistência local com SQLite
- ✅ Interface gráfica intuitiva

## 📦 Distribuição para Usuários Finais

### Gerar Executável Standalone

Para criar um executável que não precisa de Maven:

```bash
.\gerar-executavel.bat
```

Isso criará o arquivo `SistemaVendasCamisas.jar` que pode ser executado com duplo clique.

### Preparar Pacote Completo

Para criar um pacote pronto para distribuição:

```bash
.\preparar-distribuicao.bat
```

Isso criará uma pasta `Distribuicao/` com:
- ✅ Executável standalone
- ✅ Atalho de inicialização
- ✅ Manuais de usuário
- ✅ Guias de instalação

### Para Usuários Finais

Usuários finais só precisam:
1. Ter Java instalado (https://www.java.com/pt-BR/download/)
2. Dar duplo clique em `Iniciar Sistema.bat`

Consulte `GUIA-INSTALACAO-SIMPLES.md` para instruções detalhadas.

## Testes

O projeto inclui testes automatizados completos. Para executar:

### Opção 1: Script Batch (Windows)
```batch
executar-testes.bat
```

### Opção 2: Maven
```bash
mvn clean compile
mvn exec:java "-Dexec.mainClass=com.vendas.TesteSistema"
```

### Documentação de Testes
- `TESTES.md` - Documentação completa dos testes
- `COMO-EXECUTAR-TESTES.txt` - Guia rápido
- `CHECKLIST-TESTES.md` - Checklist de validação
- `RESUMO-TAREFA-11.md` - Resumo da implementação

### Cobertura de Testes
- ✅ Fluxo completo de cadastro e venda
- ✅ Gerenciamento de clientes e pagamentos
- ✅ Persistência de dados
- ✅ Validações e tratamento de erros

## Status do Desenvolvimento

✅ **Projeto Completo!** Todas as funcionalidades foram implementadas e testadas.

Consulte o arquivo `.kiro/specs/sistema-vendas-camisas/tasks.md` para ver o histórico de desenvolvimento.
