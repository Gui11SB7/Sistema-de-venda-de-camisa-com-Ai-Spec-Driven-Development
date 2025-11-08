package com.vendas.view;

import com.vendas.controller.ClienteController;
import com.vendas.controller.PagamentoController;
import com.vendas.controller.VendaController;
import com.vendas.dao.ProdutoDAO;
import com.vendas.model.Cliente;
import com.vendas.model.Pagamento;
import com.vendas.model.Produto;
import com.vendas.model.Venda;
import com.vendas.util.UIUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Painel para exibir detalhes completos de um cliente.
 * Mostra informações do cliente, histórico de vendas, pagamentos e saldo devedor.
 */
public class ClienteDetalhesPanel extends JPanel {
    
    private final Cliente cliente;
    private final ClienteController clienteController;
    private final VendaController vendaController;
    private final PagamentoController pagamentoController;
    private final ProdutoDAO produtoDAO;
    
    // Componentes da interface
    private JTable tabelaVendas;
    private DefaultTableModel modeloTabelaVendas;
    private JTable tabelaPagamentos;
    private DefaultTableModel modeloTabelaPagamentos;
    private JLabel lblTotalVendas;
    private JLabel lblTotalPago;
    private JLabel lblSaldoDevedor;
    private JButton btnRegistrarPagamento;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public ClienteDetalhesPanel(Cliente cliente) {
        this.cliente = cliente;
        this.clienteController = new ClienteController();
        this.vendaController = new VendaController();
        this.pagamentoController = new PagamentoController();
        this.produtoDAO = new ProdutoDAO();
        
        inicializarComponentes();
        carregarDados();
    }
    
    /**
     * Inicializa os componentes da interface.
     */
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Painel superior com informações do cliente
        JPanel painelInfo = criarPainelInformacoes();
        add(painelInfo, BorderLayout.NORTH);
        
        // Painel central com vendas e pagamentos
        JPanel painelCentral = criarPainelCentral();
        add(painelCentral, BorderLayout.CENTER);
        
        // Painel inferior com resumo financeiro
        JPanel painelResumo = criarPainelResumo();
        add(painelResumo, BorderLayout.SOUTH);
    }
    
    /**
     * Cria o painel com informações básicas do cliente.
     */
    private JPanel criarPainelInformacoes() {
        JPanel painel = new JPanel(new GridLayout(0, 2, 10, 5));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createTitledBorder("Informações do Cliente"));
        
        adicionarCampoInfo(painel, "Nome:", cliente.getNome());
        adicionarCampoInfo(painel, "Telefone:", cliente.getTelefone() != null ? cliente.getTelefone() : "N/A");
        adicionarCampoInfo(painel, "Email:", cliente.getEmail() != null ? cliente.getEmail() : "N/A");
        adicionarCampoInfo(painel, "Endereço:", cliente.getEndereco() != null ? cliente.getEndereco() : "N/A");
        
        return painel;
    }
    
    /**
     * Adiciona um campo de informação ao painel.
     */
    private void adicionarCampoInfo(JPanel painel, String label, String valor) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.PLAIN, 12));
        
        painel.add(lblLabel);
        painel.add(lblValor);
    }
    
    /**
     * Cria o painel central com tabelas de vendas e pagamentos.
     */
    private JPanel criarPainelCentral() {
        JPanel painel = new JPanel(new GridLayout(2, 1, 10, 10));
        painel.setBackground(Color.WHITE);
        
        // Painel de vendas
        JPanel painelVendas = criarPainelVendas();
        painel.add(painelVendas);
        
        // Painel de pagamentos
        JPanel painelPagamentos = criarPainelPagamentos();
        painel.add(painelPagamentos);
        
        return painel;
    }
    
    /**
     * Cria o painel com a tabela de vendas.
     */
    private JPanel criarPainelVendas() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createTitledBorder("Histórico de Vendas"));
        
        // Criar tabela de vendas
        String[] colunas = {"ID", "Produto", "Data", "Valor"};
        modeloTabelaVendas = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaVendas = new JTable(modeloTabelaVendas);
        tabelaVendas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaVendas.getTableHeader().setReorderingAllowed(false);
        
        // Configurar larguras das colunas
        tabelaVendas.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaVendas.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelaVendas.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabelaVendas.getColumnModel().getColumn(3).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(tabelaVendas);
        scrollPane.setPreferredSize(new Dimension(0, 150));
        painel.add(scrollPane, BorderLayout.CENTER);
        
        return painel;
    }
    
    /**
     * Cria o painel com a tabela de pagamentos.
     */
    private JPanel criarPainelPagamentos() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createTitledBorder("Histórico de Pagamentos"));
        
        // Criar tabela de pagamentos
        String[] colunas = {"ID", "Data", "Valor", "Observação"};
        modeloTabelaPagamentos = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaPagamentos = new JTable(modeloTabelaPagamentos);
        tabelaPagamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaPagamentos.getTableHeader().setReorderingAllowed(false);
        
        // Configurar larguras das colunas
        tabelaPagamentos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaPagamentos.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabelaPagamentos.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabelaPagamentos.getColumnModel().getColumn(3).setPreferredWidth(200);
        
        JScrollPane scrollPane = new JScrollPane(tabelaPagamentos);
        scrollPane.setPreferredSize(new Dimension(0, 150));
        painel.add(scrollPane, BorderLayout.CENTER);
        
        return painel;
    }
    
    /**
     * Cria o painel de resumo financeiro.
     */
    private JPanel criarPainelResumo() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createTitledBorder("Resumo Financeiro"));
        
        // Painel com os valores
        JPanel painelValores = new JPanel(new GridLayout(3, 2, 10, 10));
        painelValores.setBackground(Color.WHITE);
        painelValores.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Total de vendas
        JLabel lblTituloVendas = new JLabel("Total de Vendas:");
        lblTituloVendas.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalVendas = new JLabel("R$ 0,00");
        lblTotalVendas.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTotalVendas.setForeground(new Color(46, 125, 50));
        
        // Total pago
        JLabel lblTituloPago = new JLabel("Total Pago:");
        lblTituloPago.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalPago = new JLabel("R$ 0,00");
        lblTotalPago.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTotalPago.setForeground(new Color(52, 152, 219));
        
        // Saldo devedor
        JLabel lblTituloSaldo = new JLabel("Saldo Devedor:");
        lblTituloSaldo.setFont(new Font("Arial", Font.BOLD, 16));
        lblSaldoDevedor = new JLabel("R$ 0,00");
        lblSaldoDevedor.setFont(new Font("Arial", Font.BOLD, 16));
        
        painelValores.add(lblTituloVendas);
        painelValores.add(lblTotalVendas);
        painelValores.add(lblTituloPago);
        painelValores.add(lblTotalPago);
        painelValores.add(lblTituloSaldo);
        painelValores.add(lblSaldoDevedor);
        
        painel.add(painelValores, BorderLayout.CENTER);
        
        // Botão de registrar pagamento
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotao.setBackground(Color.WHITE);
        
        btnRegistrarPagamento = new JButton("Registrar Pagamento");
        btnRegistrarPagamento.setBackground(new Color(46, 125, 50));
        btnRegistrarPagamento.setForeground(Color.WHITE);
        btnRegistrarPagamento.setFocusPainted(false);
        btnRegistrarPagamento.setBorderPainted(false);
        btnRegistrarPagamento.setFont(new Font("Arial", Font.BOLD, 12));
        btnRegistrarPagamento.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrarPagamento.setPreferredSize(new Dimension(180, 35));
        btnRegistrarPagamento.addActionListener(e -> abrirDialogoRegistroPagamento());
        
        painelBotao.add(btnRegistrarPagamento);
        painel.add(painelBotao, BorderLayout.SOUTH);
        
        return painel;
    }
    
    /**
     * Carrega os dados do cliente (vendas, pagamentos e cálculos).
     */
    private void carregarDados() {
        carregarVendas();
        carregarPagamentos();
        calcularResumoFinanceiro();
    }
    
    /**
     * Carrega as vendas do cliente na tabela.
     */
    private void carregarVendas() {
        try {
            // Limpar tabela
            modeloTabelaVendas.setRowCount(0);
            
            // Buscar vendas do cliente
            List<Venda> vendas = vendaController.obterVendasPorCliente(cliente.getId());
            
            // Adicionar vendas na tabela
            for (Venda venda : vendas) {
                // Buscar informações do produto
                Produto produto = produtoDAO.buscarPorId(venda.getProdutoId());
                String descricaoProduto = produto != null ? 
                    produto.getDescricao() + " (" + produto.getTamanho() + ")" : 
                    "Produto ID: " + venda.getProdutoId();
                
                Object[] linha = {
                    venda.getId(),
                    descricaoProduto,
                    venda.getDataVenda().format(DATE_FORMATTER),
                    UIUtils.formatarValorMonetario(venda.getValorVenda())
                };
                modeloTabelaVendas.addRow(linha);
            }
            
        } catch (Exception e) {
            UIUtils.mostrarMensagemErro(this, "Erro ao carregar vendas: " + e.getMessage());
        }
    }
    
    /**
     * Carrega os pagamentos do cliente na tabela.
     */
    private void carregarPagamentos() {
        try {
            // Limpar tabela
            modeloTabelaPagamentos.setRowCount(0);
            
            // Buscar pagamentos do cliente
            List<Pagamento> pagamentos = pagamentoController.listarPagamentosPorCliente(cliente.getId());
            
            // Adicionar pagamentos na tabela
            for (Pagamento pagamento : pagamentos) {
                Object[] linha = {
                    pagamento.getId(),
                    pagamento.getDataPagamento().format(DATE_FORMATTER),
                    UIUtils.formatarValorMonetario(pagamento.getValorPago()),
                    pagamento.getObservacao() != null ? pagamento.getObservacao() : ""
                };
                modeloTabelaPagamentos.addRow(linha);
            }
            
        } catch (Exception e) {
            UIUtils.mostrarMensagemErro(this, "Erro ao carregar pagamentos: " + e.getMessage());
        }
    }
    
    /**
     * Calcula e exibe o resumo financeiro do cliente.
     */
    private void calcularResumoFinanceiro() {
        try {
            // Calcular saldo devedor usando o controller
            BigDecimal saldoDevedor = clienteController.calcularSaldoDevedor(cliente.getId());
            
            // Buscar vendas e pagamentos para exibir totais
            List<Venda> vendas = vendaController.obterVendasPorCliente(cliente.getId());
            List<Pagamento> pagamentos = pagamentoController.listarPagamentosPorCliente(cliente.getId());
            
            // Calcular total de vendas
            BigDecimal totalVendas = vendas.stream()
                .map(Venda::getValorVenda)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            // Calcular total pago
            BigDecimal totalPago = pagamentos.stream()
                .map(Pagamento::getValorPago)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            // Atualizar labels
            lblTotalVendas.setText(UIUtils.formatarValorMonetario(totalVendas));
            lblTotalPago.setText(UIUtils.formatarValorMonetario(totalPago));
            lblSaldoDevedor.setText(UIUtils.formatarValorMonetario(saldoDevedor));
            
            // Aplicar destaque visual ao saldo devedor
            if (saldoDevedor.compareTo(BigDecimal.ZERO) > 0) {
                // Saldo positivo (cliente deve) - vermelho
                lblSaldoDevedor.setForeground(new Color(211, 47, 47));
            } else if (saldoDevedor.compareTo(BigDecimal.ZERO) < 0) {
                // Saldo negativo (cliente pagou a mais) - azul
                lblSaldoDevedor.setForeground(new Color(52, 152, 219));
            } else {
                // Saldo zero (quitado) - verde
                lblSaldoDevedor.setForeground(new Color(46, 125, 50));
            }
            
        } catch (Exception e) {
            UIUtils.mostrarMensagemErro(this, "Erro ao calcular resumo financeiro: " + e.getMessage());
        }
    }
    
    /**
     * Abre o diálogo para registrar um novo pagamento.
     */
    private void abrirDialogoRegistroPagamento() {
        // Obter o Frame pai
        Window window = SwingUtilities.getWindowAncestor(this);
        Frame frame = null;
        if (window instanceof Frame) {
            frame = (Frame) window;
        }
        
        // Criar e exibir o diálogo
        DialogoRegistroPagamento dialogo = new DialogoRegistroPagamento(frame, cliente.getId());
        dialogo.setVisible(true);
        
        // Se o pagamento foi registrado com sucesso, atualizar os dados
        if (dialogo.isConfirmado()) {
            atualizarDados();
        }
    }
    
    /**
     * Atualiza os dados exibidos no painel.
     * Útil para ser chamado após registrar um pagamento.
     */
    public void atualizarDados() {
        carregarDados();
    }
}
