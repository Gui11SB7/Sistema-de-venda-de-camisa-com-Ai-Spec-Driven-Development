package com.vendas.view;

import com.vendas.controller.GerenciamentoController;
import com.vendas.model.Produto;
import com.vendas.util.UIUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Painel de gerenciamento financeiro e de estoque.
 * Exibe produtos em estoque e resumo financeiro do negócio.
 */
public class PainelGerenciamentoPanel extends JPanel {
    
    private final GerenciamentoController gerenciamentoController;
    
    // Componentes da interface
    private JTable tabelaProdutos;
    private DefaultTableModel modeloTabela;
    private JLabel lblTotalGasto;
    private JLabel lblTotalRecebido;
    private JLabel lblLucro;
    private JButton btnAtualizar;
    
    public PainelGerenciamentoPanel() {
        this.gerenciamentoController = new GerenciamentoController();
        inicializarComponentes();
        carregarDados();
    }
    
    /**
     * Inicializa os componentes da interface.
     */
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Painel superior com título e botão atualizar
        JPanel painelSuperior = criarPainelSuperior();
        add(painelSuperior, BorderLayout.NORTH);
        
        // Painel central com tabela de produtos
        JPanel painelCentral = criarPainelTabela();
        add(painelCentral, BorderLayout.CENTER);
        
        // Painel inferior com resumo financeiro
        JPanel painelInferior = criarPainelResumoFinanceiro();
        add(painelInferior, BorderLayout.SOUTH);
    }
    
    /**
     * Cria o painel superior com título e botão atualizar.
     */
    private JPanel criarPainelSuperior() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Título
        JLabel lblTitulo = new JLabel("Painel de Gerenciamento Financeiro");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(45, 52, 54));
        painel.add(lblTitulo, BorderLayout.WEST);
        
        // Botão Atualizar
        btnAtualizar = new JButton("Atualizar Dados");
        btnAtualizar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAtualizar.setPreferredSize(new Dimension(150, 40));
        btnAtualizar.setBackground(new Color(52, 152, 219));
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAtualizar.addActionListener(e -> atualizarDados());
        painel.add(btnAtualizar, BorderLayout.EAST);
        
        return painel;
    }
    
    /**
     * Cria o painel com a tabela de produtos em estoque.
     */
    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            "Produtos em Estoque",
            0,
            0,
            new Font("Arial", Font.BOLD, 16),
            new Color(45, 52, 54)
        ));
        
        // Criar modelo da tabela
        String[] colunas = {"ID", "Descrição", "Tamanho", "Valor de Compra", "Data de Compra"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável
            }
        };
        
        // Criar tabela
        tabelaProdutos = new JTable(modeloTabela);
        tabelaProdutos.setFont(new Font("Arial", Font.PLAIN, 13));
        tabelaProdutos.setRowHeight(25);
        tabelaProdutos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabelaProdutos.getTableHeader().setBackground(new Color(52, 73, 94));
        tabelaProdutos.getTableHeader().setForeground(Color.WHITE);
        tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaProdutos.setGridColor(new Color(189, 195, 199));
        
        // Configurar largura das colunas
        tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(300); // Descrição
        tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(80);  // Tamanho
        tabelaProdutos.getColumnModel().getColumn(3).setPreferredWidth(120); // Valor
        tabelaProdutos.getColumnModel().getColumn(4).setPreferredWidth(120); // Data
        
        // Centralizar conteúdo das colunas ID e Tamanho
        DefaultTableCellRenderer centralizador = new DefaultTableCellRenderer();
        centralizador.setHorizontalAlignment(SwingConstants.CENTER);
        tabelaProdutos.getColumnModel().getColumn(0).setCellRenderer(centralizador);
        tabelaProdutos.getColumnModel().getColumn(2).setCellRenderer(centralizador);
        tabelaProdutos.getColumnModel().getColumn(4).setCellRenderer(centralizador);
        
        // Alinhar valor à direita
        DefaultTableCellRenderer alinhadorDireita = new DefaultTableCellRenderer();
        alinhadorDireita.setHorizontalAlignment(SwingConstants.RIGHT);
        tabelaProdutos.getColumnModel().getColumn(3).setCellRenderer(alinhadorDireita);
        
        // Adicionar scroll
        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        painel.add(scrollPane, BorderLayout.CENTER);
        
        return painel;
    }
    
    /**
     * Cria o painel com o resumo financeiro.
     */
    private JPanel criarPainelResumoFinanceiro() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 0, 0, 0),
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                "Resumo Financeiro",
                0,
                0,
                new Font("Arial", Font.BOLD, 16),
                new Color(45, 52, 54)
            )
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Total Gasto
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblTextoGasto = new JLabel("Total Gasto:");
        lblTextoGasto.setFont(new Font("Arial", Font.BOLD, 16));
        painel.add(lblTextoGasto, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        lblTotalGasto = new JLabel("R$ 0,00");
        lblTotalGasto.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotalGasto.setForeground(new Color(231, 76, 60)); // Vermelho
        painel.add(lblTotalGasto, gbc);
        
        // Total Recebido
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblTextoRecebido = new JLabel("Total Recebido:");
        lblTextoRecebido.setFont(new Font("Arial", Font.BOLD, 16));
        painel.add(lblTextoRecebido, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        lblTotalRecebido = new JLabel("R$ 0,00");
        lblTotalRecebido.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotalRecebido.setForeground(new Color(46, 204, 113)); // Verde
        painel.add(lblTotalRecebido, gbc);
        
        // Separador
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JSeparator separador = new JSeparator();
        separador.setForeground(new Color(189, 195, 199));
        painel.add(separador, gbc);
        
        // Lucro/Prejuízo
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lblTextoLucro = new JLabel("Lucro/Prejuízo:");
        lblTextoLucro.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(lblTextoLucro, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        lblLucro = new JLabel("R$ 0,00");
        lblLucro.setFont(new Font("Arial", Font.BOLD, 20));
        painel.add(lblLucro, gbc);
        
        return painel;
    }
    
    /**
     * Carrega os dados iniciais do painel.
     */
    private void carregarDados() {
        carregarProdutosEmEstoque();
        atualizarResumoFinanceiro();
    }
    
    /**
     * Carrega os produtos em estoque na tabela.
     */
    private void carregarProdutosEmEstoque() {
        try {
            // Limpar tabela
            modeloTabela.setRowCount(0);
            
            // Obter produtos em estoque
            List<Produto> produtos = gerenciamentoController.obterProdutosEmEstoque();
            
            // Adicionar produtos à tabela
            for (Produto produto : produtos) {
                Object[] linha = {
                    produto.getId(),
                    produto.getDescricao(),
                    produto.getTamanho(),
                    UIUtils.formatarValorMonetario(produto.getValorCompra()),
                    produto.getDataCompra().toString()
                };
                modeloTabela.addRow(linha);
            }
            
        } catch (Exception e) {
            UIUtils.mostrarMensagemErro(this, "Erro ao carregar produtos em estoque: " + e.getMessage());
        }
    }
    
    /**
     * Atualiza o resumo financeiro com os cálculos.
     */
    private void atualizarResumoFinanceiro() {
        try {
            // Obter totais do controller
            BigDecimal totalGasto = gerenciamentoController.calcularTotalGasto();
            BigDecimal totalRecebido = gerenciamentoController.calcularTotalRecebido();
            BigDecimal lucro = gerenciamentoController.calcularLucro();
            
            // Atualizar labels
            lblTotalGasto.setText(UIUtils.formatarValorMonetario(totalGasto));
            lblTotalRecebido.setText(UIUtils.formatarValorMonetario(totalRecebido));
            lblLucro.setText(UIUtils.formatarValorMonetario(lucro));
            
            // Aplicar cor ao lucro/prejuízo
            if (lucro.signum() >= 0) {
                // Lucro positivo ou zero - verde
                lblLucro.setForeground(new Color(46, 204, 113));
            } else {
                // Prejuízo - vermelho
                lblLucro.setForeground(new Color(231, 76, 60));
            }
            
        } catch (Exception e) {
            UIUtils.mostrarMensagemErro(this, "Erro ao calcular resumo financeiro: " + e.getMessage());
        }
    }
    
    /**
     * Atualiza todos os dados do painel.
     * Método público para permitir atualização externa.
     */
    public void atualizarDados() {
        carregarProdutosEmEstoque();
        atualizarResumoFinanceiro();
    }
    
    /**
     * Obtém a tabela de produtos.
     */
    public JTable getTabelaProdutos() {
        return tabelaProdutos;
    }
    
    /**
     * Obtém o modelo da tabela.
     */
    public DefaultTableModel getModeloTabela() {
        return modeloTabela;
    }
    
    /**
     * Obtém o label de total gasto.
     */
    public JLabel getLblTotalGasto() {
        return lblTotalGasto;
    }
    
    /**
     * Obtém o label de total recebido.
     */
    public JLabel getLblTotalRecebido() {
        return lblTotalRecebido;
    }
    
    /**
     * Obtém o label de lucro.
     */
    public JLabel getLblLucro() {
        return lblLucro;
    }
    
    /**
     * Obtém o botão atualizar.
     */
    public JButton getBtnAtualizar() {
        return btnAtualizar;
    }
    
    /**
     * Obtém o controller de gerenciamento.
     */
    public GerenciamentoController getGerenciamentoController() {
        return gerenciamentoController;
    }
}
