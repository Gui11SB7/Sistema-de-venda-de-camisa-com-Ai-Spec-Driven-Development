package com.vendas;

import com.vendas.controller.*;
import com.vendas.dao.*;
import com.vendas.model.*;
import com.vendas.util.DatabaseManager;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe para testes automatizados do sistema.
 * Valida os fluxos principais conforme requisitos.
 */
public class TesteSistema {
    
    private static int testesExecutados = 0;
    private static int testesPassaram = 0;
    private static int testesFalharam = 0;
    
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  SISTEMA DE VENDAS DE CAMISAS - TESTES");
        System.out.println("=================================================\n");
        
        try {
            // Inicializar banco de dados
            System.out.println("Inicializando banco de dados de teste...");
            DatabaseManager.inicializarBanco();
            System.out.println("✓ Banco de dados inicializado\n");
            
            // Executar testes
            testar11_1_FluxoCadastroVenda();
            testar11_2_GerenciamentoClientesPagamentos();
            testar11_3_PersistenciaDados();
            testar11_4_ValidacoesErros();
            
            // Resumo
            System.out.println("\n=================================================");
            System.out.println("  RESUMO DOS TESTES");
            System.out.println("=================================================");
            System.out.println("Total de testes: " + testesExecutados);
            System.out.println("Passaram: " + testesPassaram + " ✓");
            System.out.println("Falharam: " + testesFalharam + " ✗");
            System.out.println("Taxa de sucesso: " + 
                (testesExecutados > 0 ? (testesPassaram * 100 / testesExecutados) : 0) + "%");
            System.out.println("=================================================\n");
            
            if (testesFalharam == 0) {
                System.out.println("✓ TODOS OS TESTES PASSARAM!");
            } else {
                System.out.println("✗ ALGUNS TESTES FALHARAM - Verifique os detalhes acima");
            }
            
        } catch (Exception e) {
            System.err.println("✗ ERRO CRÍTICO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    /**
     * Teste 11.1: Fluxo completo de cadastro e venda
     * - Cadastrar múltiplos produtos com e sem imagem
     * - Verificar que produtos aparecem na lista de disponíveis
     * - Registrar vendas e verificar remoção do estoque
     * - Validar cálculos no painel de gerenciamento
     */
    private static void testar11_1_FluxoCadastroVenda() {
        System.out.println("=================================================");
        System.out.println("  TESTE 11.1: Fluxo Completo de Cadastro e Venda");
        System.out.println("=================================================\n");
        
        try {
            ProdutoController produtoController = new ProdutoController();
            VendaController vendaController = new VendaController();
            ClienteController clienteController = new ClienteController();
            GerenciamentoController gerenciamentoController = new GerenciamentoController();
            
            // Cadastrar cliente para as vendas
            Cliente cliente = new Cliente();
            cliente.setNome("Cliente Teste");
            cliente.setTelefone("11999999999");
            cliente.setEmail("teste@email.com");
            cliente.setEndereco("Rua Teste, 123");
            
            boolean clienteCadastrado = clienteController.cadastrarCliente(cliente);
            verificar("Cadastrar cliente para vendas", clienteCadastrado);
            
            // Teste 1: Cadastrar produto SEM imagem
            Produto produto1 = new Produto();
            produto1.setDescricao("Camisa Básica Preta");
            produto1.setTamanho("M");
            produto1.setImagem(null);
            produto1.setValorCompra(new BigDecimal("25.00"));
            produto1.setDataCompra(LocalDate.now());
            
            boolean cadastrado1 = produtoController.cadastrarProduto(produto1);
            verificar("Cadastrar produto SEM imagem", cadastrado1);
            
            // Teste 2: Cadastrar produto COM imagem (simulada)
            Produto produto2 = new Produto();
            produto2.setDescricao("Camisa Estampada Azul");
            produto2.setTamanho("G");
            produto2.setImagem(new byte[]{1, 2, 3, 4, 5}); // Imagem simulada
            produto2.setValorCompra(new BigDecimal("35.00"));
            produto2.setDataCompra(LocalDate.now());
            
            boolean cadastrado2 = produtoController.cadastrarProduto(produto2);
            verificar("Cadastrar produto COM imagem", cadastrado2);
            
            // Teste 3: Cadastrar mais produtos
            Produto produto3 = new Produto();
            produto3.setDescricao("Camisa Polo Branca");
            produto3.setTamanho("P");
            produto3.setImagem(null);
            produto3.setValorCompra(new BigDecimal("40.00"));
            produto3.setDataCompra(LocalDate.now());
            
            boolean cadastrado3 = produtoController.cadastrarProduto(produto3);
            verificar("Cadastrar terceiro produto", cadastrado3);
            
            // Teste 4: Verificar produtos disponíveis
            List<Produto> disponiveis = produtoController.listarProdutosDisponiveis();
            verificar("Produtos aparecem na lista de disponíveis", disponiveis.size() >= 3);
            System.out.println("  → " + disponiveis.size() + " produtos disponíveis");
            
            // Teste 5: Registrar venda do primeiro produto
            Venda venda1 = new Venda();
            venda1.setProdutoId(produto1.getId());
            venda1.setClienteId(cliente.getId());
            venda1.setDataVenda(LocalDate.now());
            venda1.setValorVenda(new BigDecimal("50.00"));
            
            boolean vendaRegistrada = vendaController.registrarVenda(venda1);
            verificar("Registrar venda do primeiro produto", vendaRegistrada);
            
            // Teste 6: Verificar remoção do estoque
            List<Produto> disponiveisAposVenda = produtoController.listarProdutosDisponiveis();
            verificar("Produto removido do estoque após venda", 
                disponiveisAposVenda.size() == disponiveis.size() - 1);
            System.out.println("  → " + disponiveisAposVenda.size() + " produtos disponíveis após venda");
            
            // Teste 7: Validar cálculos no painel de gerenciamento
            BigDecimal totalGasto = gerenciamentoController.calcularTotalGasto();
            BigDecimal totalRecebido = gerenciamentoController.calcularTotalRecebido();
            BigDecimal lucro = gerenciamentoController.calcularLucro();
            
            verificar("Cálculo de total gasto", totalGasto.compareTo(BigDecimal.ZERO) > 0);
            System.out.println("  → Total gasto: R$ " + totalGasto);
            
            verificar("Cálculo de total recebido", totalRecebido.compareTo(BigDecimal.ZERO) > 0);
            System.out.println("  → Total recebido: R$ " + totalRecebido);
            
            verificar("Cálculo de lucro/prejuízo", lucro != null);
            System.out.println("  → Lucro: R$ " + lucro);
            
            // Teste 8: Produtos em estoque
            List<Produto> estoque = gerenciamentoController.obterProdutosEmEstoque();
            verificar("Listar produtos em estoque", estoque.size() >= 2);
            System.out.println("  → " + estoque.size() + " produtos em estoque");
            
        } catch (Exception e) {
            System.err.println("✗ ERRO no teste 11.1: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println();
    }

    
    /**
     * Teste 11.2: Gerenciamento de clientes e pagamentos
     * - Cadastrar clientes
     * - Realizar múltiplas vendas para o mesmo cliente
     * - Verificar cálculo de saldo devedor
     * - Registrar pagamentos parciais e totais
     * - Validar atualização de saldo
     */
    private static void testar11_2_GerenciamentoClientesPagamentos() {
        System.out.println("=================================================");
        System.out.println("  TESTE 11.2: Gerenciamento de Clientes e Pagamentos");
        System.out.println("=================================================\n");
        
        try {
            ClienteController clienteController = new ClienteController();
            ProdutoController produtoController = new ProdutoController();
            VendaController vendaController = new VendaController();
            PagamentoController pagamentoController = new PagamentoController();
            
            // Teste 1: Cadastrar cliente
            Cliente cliente = new Cliente();
            cliente.setNome("João Silva");
            cliente.setTelefone("11987654321");
            cliente.setEmail("joao@email.com");
            cliente.setEndereco("Av. Principal, 456");
            
            boolean clienteCadastrado = clienteController.cadastrarCliente(cliente);
            verificar("Cadastrar cliente", clienteCadastrado);
            
            // Teste 2: Cadastrar produtos para venda
            Produto produto1 = new Produto();
            produto1.setDescricao("Camisa Vermelha");
            produto1.setTamanho("M");
            produto1.setValorCompra(new BigDecimal("30.00"));
            produto1.setDataCompra(LocalDate.now());
            produtoController.cadastrarProduto(produto1);
            
            Produto produto2 = new Produto();
            produto2.setDescricao("Camisa Verde");
            produto2.setTamanho("G");
            produto2.setValorCompra(new BigDecimal("35.00"));
            produto2.setDataCompra(LocalDate.now());
            produtoController.cadastrarProduto(produto2);
            
            // Teste 3: Realizar múltiplas vendas para o mesmo cliente
            Venda venda1 = new Venda();
            venda1.setProdutoId(produto1.getId());
            venda1.setClienteId(cliente.getId());
            venda1.setDataVenda(LocalDate.now());
            venda1.setValorVenda(new BigDecimal("60.00"));
            
            boolean venda1Registrada = vendaController.registrarVenda(venda1);
            verificar("Registrar primeira venda para cliente", venda1Registrada);
            
            Venda venda2 = new Venda();
            venda2.setProdutoId(produto2.getId());
            venda2.setClienteId(cliente.getId());
            venda2.setDataVenda(LocalDate.now());
            venda2.setValorVenda(new BigDecimal("70.00"));
            
            boolean venda2Registrada = vendaController.registrarVenda(venda2);
            verificar("Registrar segunda venda para cliente", venda2Registrada);
            
            // Teste 4: Verificar histórico de vendas do cliente
            List<Venda> vendasCliente = vendaController.obterVendasPorCliente(cliente.getId());
            verificar("Histórico de vendas do cliente", vendasCliente.size() >= 2);
            System.out.println("  → " + vendasCliente.size() + " vendas para o cliente");
            
            // Teste 5: Verificar cálculo de saldo devedor (antes de pagamentos)
            BigDecimal saldoDevedor = clienteController.calcularSaldoDevedor(cliente.getId());
            verificar("Cálculo de saldo devedor inicial", 
                saldoDevedor.compareTo(new BigDecimal("130.00")) == 0);
            System.out.println("  → Saldo devedor inicial: R$ " + saldoDevedor);
            
            // Teste 6: Registrar pagamento parcial
            Pagamento pagamento1 = new Pagamento();
            pagamento1.setClienteId(cliente.getId());
            pagamento1.setDataPagamento(LocalDate.now());
            pagamento1.setValorPago(new BigDecimal("50.00"));
            pagamento1.setObservacao("Pagamento parcial");
            
            boolean pagamento1Registrado = pagamentoController.registrarPagamento(pagamento1);
            verificar("Registrar pagamento parcial", pagamento1Registrado);
            
            // Teste 7: Verificar atualização de saldo após pagamento parcial
            BigDecimal saldoAposParcial = clienteController.calcularSaldoDevedor(cliente.getId());
            verificar("Saldo atualizado após pagamento parcial", 
                saldoAposParcial.compareTo(new BigDecimal("80.00")) == 0);
            System.out.println("  → Saldo após pagamento parcial: R$ " + saldoAposParcial);
            
            // Teste 8: Registrar pagamento total
            Pagamento pagamento2 = new Pagamento();
            pagamento2.setClienteId(cliente.getId());
            pagamento2.setDataPagamento(LocalDate.now());
            pagamento2.setValorPago(new BigDecimal("80.00"));
            pagamento2.setObservacao("Pagamento final");
            
            boolean pagamento2Registrado = pagamentoController.registrarPagamento(pagamento2);
            verificar("Registrar pagamento total", pagamento2Registrado);
            
            // Teste 9: Verificar saldo zerado
            BigDecimal saldoFinal = clienteController.calcularSaldoDevedor(cliente.getId());
            verificar("Saldo zerado após pagamento total", 
                saldoFinal.compareTo(BigDecimal.ZERO) == 0);
            System.out.println("  → Saldo final: R$ " + saldoFinal);
            
            // Teste 10: Listar pagamentos do cliente
            List<Pagamento> pagamentos = pagamentoController.listarPagamentosPorCliente(cliente.getId());
            verificar("Listar pagamentos do cliente", pagamentos.size() >= 2);
            System.out.println("  → " + pagamentos.size() + " pagamentos registrados");
            
        } catch (Exception e) {
            System.err.println("✗ ERRO no teste 11.2: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println();
    }

    
    /**
     * Teste 11.3: Persistência de dados
     * - Cadastrar dados diversos
     * - Fechar e reabrir aplicação (simular com nova conexão)
     * - Verificar que todos os dados foram mantidos
     * - Testar com imagens grandes
     */
    private static void testar11_3_PersistenciaDados() {
        System.out.println("=================================================");
        System.out.println("  TESTE 11.3: Persistência de Dados");
        System.out.println("=================================================\n");
        
        try {
            ProdutoDAO produtoDAO = new ProdutoDAO();
            ClienteDAO clienteDAO = new ClienteDAO();
            VendaDAO vendaDAO = new VendaDAO();
            PagamentoDAO pagamentoDAO = new PagamentoDAO();
            
            // Teste 1: Contar dados antes
            int produtosAntes = produtoDAO.buscarTodos().size();
            int clientesAntes = clienteDAO.buscarTodos().size();
            int vendasAntes = vendaDAO.buscarTodas().size();
            
            System.out.println("  Dados antes:");
            System.out.println("  → Produtos: " + produtosAntes);
            System.out.println("  → Clientes: " + clientesAntes);
            System.out.println("  → Vendas: " + vendasAntes);
            
            // Teste 2: Cadastrar novos dados
            Cliente novoCliente = new Cliente();
            novoCliente.setNome("Maria Santos");
            novoCliente.setTelefone("11955555555");
            novoCliente.setEmail("maria@email.com");
            novoCliente.setEndereco("Rua Nova, 789");
            clienteDAO.inserir(novoCliente);
            
            // Teste 3: Produto com imagem grande (simulada - 1MB)
            byte[] imagemGrande = new byte[1024 * 1024]; // 1MB
            for (int i = 0; i < imagemGrande.length; i++) {
                imagemGrande[i] = (byte) (i % 256);
            }
            
            Produto produtoImagemGrande = new Produto();
            produtoImagemGrande.setDescricao("Camisa com Imagem Grande");
            produtoImagemGrande.setTamanho("GG");
            produtoImagemGrande.setImagem(imagemGrande);
            produtoImagemGrande.setValorCompra(new BigDecimal("45.00"));
            produtoImagemGrande.setDataCompra(LocalDate.now());
            
            produtoDAO.inserir(produtoImagemGrande);
            verificar("Cadastrar produto com imagem grande (1MB)", 
                produtoImagemGrande.getId() != null);
            
            // Teste 4: Simular "fechar e reabrir" - criar novas instâncias de DAO
            ProdutoDAO novoProdutoDAO = new ProdutoDAO();
            ClienteDAO novoClienteDAO = new ClienteDAO();
            VendaDAO novaVendaDAO = new VendaDAO();
            
            // Teste 5: Verificar persistência dos dados
            int produtosDepois = novoProdutoDAO.buscarTodos().size();
            int clientesDepois = novoClienteDAO.buscarTodos().size();
            int vendasDepois = novaVendaDAO.buscarTodas().size();
            
            System.out.println("\n  Dados depois:");
            System.out.println("  → Produtos: " + produtosDepois);
            System.out.println("  → Clientes: " + clientesDepois);
            System.out.println("  → Vendas: " + vendasDepois);
            
            verificar("Produtos persistidos", produtosDepois > produtosAntes);
            verificar("Clientes persistidos", clientesDepois > clientesAntes);
            verificar("Vendas persistidas", vendasDepois == vendasAntes);
            
            // Teste 6: Recuperar produto com imagem grande
            Produto produtoRecuperado = novoProdutoDAO.buscarPorId(produtoImagemGrande.getId());
            verificar("Recuperar produto com imagem grande", produtoRecuperado != null);
            verificar("Imagem grande mantida", 
                produtoRecuperado.getImagem() != null && 
                produtoRecuperado.getImagem().length == imagemGrande.length);
            System.out.println("  → Imagem recuperada: " + produtoRecuperado.getImagem().length + " bytes");
            
            // Teste 7: Verificar integridade dos dados recuperados
            Cliente clienteRecuperado = novoClienteDAO.buscarPorId(novoCliente.getId());
            verificar("Dados do cliente mantidos", 
                clienteRecuperado != null && 
                clienteRecuperado.getNome().equals("Maria Santos"));
            
        } catch (Exception e) {
            System.err.println("✗ ERRO no teste 11.3: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println();
    }

    
    /**
     * Teste 11.4: Validações e tratamento de erros
     * - Tentar cadastrar produto sem campos obrigatórios
     * - Tentar registrar venda sem selecionar produto/cliente
     * - Verificar mensagens de erro
     * - Testar com valores inválidos
     */
    private static void testar11_4_ValidacoesErros() {
        System.out.println("=================================================");
        System.out.println("  TESTE 11.4: Validações e Tratamento de Erros");
        System.out.println("=================================================\n");
        
        try {
            ProdutoController produtoController = new ProdutoController();
            VendaController vendaController = new VendaController();
            ClienteController clienteController = new ClienteController();
            PagamentoController pagamentoController = new PagamentoController();
            
            // Teste 1: Tentar cadastrar produto sem descrição
            Produto produtoSemDescricao = new Produto();
            produtoSemDescricao.setDescricao(null);
            produtoSemDescricao.setTamanho("M");
            produtoSemDescricao.setValorCompra(new BigDecimal("25.00"));
            produtoSemDescricao.setDataCompra(LocalDate.now());
            
            boolean cadastroInvalido1 = produtoController.cadastrarProduto(produtoSemDescricao);
            verificar("Rejeitar produto sem descrição", !cadastroInvalido1);
            
            // Teste 2: Tentar cadastrar produto sem tamanho
            Produto produtoSemTamanho = new Produto();
            produtoSemTamanho.setDescricao("Camisa Teste");
            produtoSemTamanho.setTamanho(null);
            produtoSemTamanho.setValorCompra(new BigDecimal("25.00"));
            produtoSemTamanho.setDataCompra(LocalDate.now());
            
            boolean cadastroInvalido2 = produtoController.cadastrarProduto(produtoSemTamanho);
            verificar("Rejeitar produto sem tamanho", !cadastroInvalido2);
            
            // Teste 3: Tentar cadastrar produto sem valor
            Produto produtoSemValor = new Produto();
            produtoSemValor.setDescricao("Camisa Teste");
            produtoSemValor.setTamanho("M");
            produtoSemValor.setValorCompra(null);
            produtoSemValor.setDataCompra(LocalDate.now());
            
            boolean cadastroInvalido3 = produtoController.cadastrarProduto(produtoSemValor);
            verificar("Rejeitar produto sem valor", !cadastroInvalido3);
            
            // Teste 4: Tentar cadastrar produto sem data
            Produto produtoSemData = new Produto();
            produtoSemData.setDescricao("Camisa Teste");
            produtoSemData.setTamanho("M");
            produtoSemData.setValorCompra(new BigDecimal("25.00"));
            produtoSemData.setDataCompra(null);
            
            boolean cadastroInvalido4 = produtoController.cadastrarProduto(produtoSemData);
            verificar("Rejeitar produto sem data", !cadastroInvalido4);
            
            // Teste 5: Tentar cadastrar cliente sem nome
            Cliente clienteSemNome = new Cliente();
            clienteSemNome.setNome(null);
            clienteSemNome.setTelefone("11999999999");
            
            boolean clienteInvalido = clienteController.cadastrarCliente(clienteSemNome);
            verificar("Rejeitar cliente sem nome", !clienteInvalido);
            
            // Teste 6: Tentar registrar venda sem produto
            Venda vendaSemProduto = new Venda();
            vendaSemProduto.setProdutoId(null);
            vendaSemProduto.setClienteId(1L);
            vendaSemProduto.setDataVenda(LocalDate.now());
            vendaSemProduto.setValorVenda(new BigDecimal("50.00"));
            
            boolean vendaInvalida1 = vendaController.registrarVenda(vendaSemProduto);
            verificar("Rejeitar venda sem produto", !vendaInvalida1);
            
            // Teste 7: Tentar registrar venda sem cliente
            Venda vendaSemCliente = new Venda();
            vendaSemCliente.setProdutoId(1L);
            vendaSemCliente.setClienteId(null);
            vendaSemCliente.setDataVenda(LocalDate.now());
            vendaSemCliente.setValorVenda(new BigDecimal("50.00"));
            
            boolean vendaInvalida2 = vendaController.registrarVenda(vendaSemCliente);
            verificar("Rejeitar venda sem cliente", !vendaInvalida2);
            
            // Teste 8: Tentar registrar venda sem valor
            Venda vendaSemValor = new Venda();
            vendaSemValor.setProdutoId(1L);
            vendaSemValor.setClienteId(1L);
            vendaSemValor.setDataVenda(LocalDate.now());
            vendaSemValor.setValorVenda(null);
            
            boolean vendaInvalida3 = vendaController.registrarVenda(vendaSemValor);
            verificar("Rejeitar venda sem valor", !vendaInvalida3);
            
            // Teste 9: Tentar registrar venda com valor negativo
            Venda vendaValorNegativo = new Venda();
            vendaValorNegativo.setProdutoId(1L);
            vendaValorNegativo.setClienteId(1L);
            vendaValorNegativo.setDataVenda(LocalDate.now());
            vendaValorNegativo.setValorVenda(new BigDecimal("-50.00"));
            
            boolean vendaInvalida4 = vendaController.registrarVenda(vendaValorNegativo);
            verificar("Rejeitar venda com valor negativo", !vendaInvalida4);
            
            // Teste 10: Tentar registrar pagamento sem valor
            Pagamento pagamentoSemValor = new Pagamento();
            pagamentoSemValor.setClienteId(1L);
            pagamentoSemValor.setDataPagamento(LocalDate.now());
            pagamentoSemValor.setValorPago(null);
            
            boolean pagamentoInvalido1 = pagamentoController.registrarPagamento(pagamentoSemValor);
            verificar("Rejeitar pagamento sem valor", !pagamentoInvalido1);
            
            // Teste 11: Tentar registrar pagamento com valor negativo
            Pagamento pagamentoValorNegativo = new Pagamento();
            pagamentoValorNegativo.setClienteId(1L);
            pagamentoValorNegativo.setDataPagamento(LocalDate.now());
            pagamentoValorNegativo.setValorPago(new BigDecimal("-100.00"));
            
            boolean pagamentoInvalido2 = pagamentoController.registrarPagamento(pagamentoValorNegativo);
            verificar("Rejeitar pagamento com valor negativo", !pagamentoInvalido2);
            
            // Teste 12: Tentar registrar pagamento com valor zero
            Pagamento pagamentoValorZero = new Pagamento();
            pagamentoValorZero.setClienteId(1L);
            pagamentoValorZero.setDataPagamento(LocalDate.now());
            pagamentoValorZero.setValorPago(BigDecimal.ZERO);
            
            boolean pagamentoInvalido3 = pagamentoController.registrarPagamento(pagamentoValorZero);
            verificar("Rejeitar pagamento com valor zero", !pagamentoInvalido3);
            
        } catch (Exception e) {
            System.err.println("✗ ERRO no teste 11.4: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println();
    }
    
    /**
     * Método auxiliar para verificar resultado de teste
     */
    private static void verificar(String descricao, boolean resultado) {
        testesExecutados++;
        if (resultado) {
            testesPassaram++;
            System.out.println("✓ " + descricao);
        } else {
            testesFalharam++;
            System.out.println("✗ " + descricao + " - FALHOU");
        }
    }
}
