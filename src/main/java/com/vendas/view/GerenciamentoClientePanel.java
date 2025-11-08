package com.vendas.view;

import com.vendas.controller.ClienteController;
import com.vendas.model.Cliente;
import com.vendas.util.UIUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Painel para gerenciamento de clientes.
 * Permite listar, cadastrar, editar clientes e visualizar seus detalhes.
 */
public class GerenciamentoClientePanel extends JPanel {
    
    private final ClienteController clienteController;
    
    // Componentes da interface
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;
    private JButton btnNovoCliente;
    private JButton btnEditarCliente;
    private JButton btnVerDetalhes;
    private JPanel painelDetalhes;
    private ClienteDetalhesPanel clienteDetalhesPanel;
    
    public GerenciamentoClientePanel() {
        this.clienteController = new ClienteController();
        inicializarComponentes();
        carregarClientes();
    }
    
    /**
     * Inicializa os componentes da interface.
     */
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Painel superior com título e botões
        JPanel painelSuperior = criarPainelSuperior();
        add(painelSuperior, BorderLayout.NORTH);
        
        // Criar JSplitPane para dividir lista e detalhes
        JSplitPane splitPane = criarSplitPane();
        add(splitPane, BorderLayout.CENTER);
    }
    
    /**
     * Cria o painel superior com título e botões de ação.
     */
    private JPanel criarPainelSuperior() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(Color.WHITE);
        
        // Título
        JLabel titulo = new JLabel("Gerenciamento de Clientes");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(45, 52, 54));
        painel.add(titulo, BorderLayout.WEST);
        
        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelBotoes.setBackground(Color.WHITE);
        
        btnNovoCliente = criarBotao("Novo Cliente");
        btnNovoCliente.addActionListener(e -> abrirDialogoCadastro(null));
        
        btnEditarCliente = criarBotao("Editar");
        btnEditarCliente.addActionListener(e -> editarClienteSelecionado());
        btnEditarCliente.setEnabled(false);
        
        btnVerDetalhes = criarBotao("Ver Detalhes");
        btnVerDetalhes.addActionListener(e -> exibirDetalhesCliente());
        btnVerDetalhes.setEnabled(false);
        
        painelBotoes.add(btnNovoCliente);
        painelBotoes.add(btnEditarCliente);
        painelBotoes.add(btnVerDetalhes);
        
        painel.add(painelBotoes, BorderLayout.EAST);
        
        return painel;
    }
    
    /**
     * Cria o JSplitPane com a lista de clientes e o painel de detalhes.
     */
    private JSplitPane criarSplitPane() {
        // Painel esquerdo: lista de clientes
        JPanel painelLista = criarPainelListaClientes();
        
        // Painel direito: detalhes do cliente (inicialmente vazio)
        painelDetalhes = new JPanel(new BorderLayout());
        painelDetalhes.setBackground(Color.WHITE);
        painelDetalhes.setBorder(BorderFactory.createTitledBorder("Detalhes do Cliente"));
        
        JLabel labelVazio = new JLabel("Selecione um cliente para ver os detalhes", SwingConstants.CENTER);
        labelVazio.setForeground(Color.GRAY);
        painelDetalhes.add(labelVazio, BorderLayout.CENTER);
        
        // Criar split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelLista, painelDetalhes);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.4);
        
        return splitPane;
    }
    
    /**
     * Cria o painel com a lista de clientes.
     */
    private JPanel criarPainelListaClientes() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createTitledBorder("Lista de Clientes"));
        
        // Criar tabela de clientes
        String[] colunas = {"ID", "Nome", "Telefone", "Email"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaClientes.getTableHeader().setReorderingAllowed(false);
        
        // Configurar larguras das colunas
        tabelaClientes.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaClientes.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabelaClientes.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabelaClientes.getColumnModel().getColumn(3).setPreferredWidth(150);
        
        // Listener para seleção de linha
        tabelaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean temSelecao = tabelaClientes.getSelectedRow() != -1;
                btnEditarCliente.setEnabled(temSelecao);
                btnVerDetalhes.setEnabled(temSelecao);
            }
        });
        
        // Adicionar scroll pane
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        painel.add(scrollPane, BorderLayout.CENTER);
        
        return painel;
    }
    
    /**
     * Cria um botão estilizado.
     */
    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(new Color(52, 152, 219));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setFont(new Font("Arial", Font.PLAIN, 12));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(120, 35));
        
        return botao;
    }
    
    /**
     * Carrega a lista de clientes na tabela.
     */
    private void carregarClientes() {
        try {
            // Limpar tabela
            modeloTabela.setRowCount(0);
            
            // Buscar clientes
            List<Cliente> clientes = clienteController.listarClientes();
            
            // Adicionar clientes na tabela
            for (Cliente cliente : clientes) {
                Object[] linha = {
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getTelefone() != null ? cliente.getTelefone() : "",
                    cliente.getEmail() != null ? cliente.getEmail() : ""
                };
                modeloTabela.addRow(linha);
            }
            
        } catch (Exception e) {
            UIUtils.mostrarMensagemErro(this, "Erro ao carregar clientes: " + e.getMessage());
        }
    }
    
    /**
     * Abre o diálogo de cadastro/edição de cliente.
     * 
     * @param cliente Cliente a ser editado, ou null para novo cadastro
     */
    private void abrirDialogoCadastro(Cliente cliente) {
        DialogoCadastroCliente dialogo = new DialogoCadastroCliente(
            (Frame) SwingUtilities.getWindowAncestor(this),
            cliente
        );
        
        dialogo.setVisible(true);
        
        // Se o diálogo foi confirmado, recarregar a lista
        if (dialogo.isConfirmado()) {
            carregarClientes();
        }
    }
    
    /**
     * Edita o cliente selecionado na tabela.
     */
    private void editarClienteSelecionado() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada == -1) {
            UIUtils.mostrarMensagemAviso(this, "Selecione um cliente para editar");
            return;
        }
        
        try {
            // Obter ID do cliente selecionado
            Long clienteId = (Long) modeloTabela.getValueAt(linhaSelecionada, 0);
            
            // Buscar cliente completo
            List<Cliente> clientes = clienteController.listarClientes();
            Cliente clienteSelecionado = clientes.stream()
                .filter(c -> c.getId().equals(clienteId))
                .findFirst()
                .orElse(null);
            
            if (clienteSelecionado != null) {
                abrirDialogoCadastro(clienteSelecionado);
            }
            
        } catch (Exception e) {
            UIUtils.mostrarMensagemErro(this, "Erro ao editar cliente: " + e.getMessage());
        }
    }
    
    /**
     * Exibe os detalhes do cliente selecionado.
     */
    private void exibirDetalhesCliente() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada == -1) {
            UIUtils.mostrarMensagemAviso(this, "Selecione um cliente para ver os detalhes");
            return;
        }
        
        try {
            // Obter ID do cliente selecionado
            Long clienteId = (Long) modeloTabela.getValueAt(linhaSelecionada, 0);
            
            // Buscar cliente completo
            List<Cliente> clientes = clienteController.listarClientes();
            Cliente clienteSelecionado = clientes.stream()
                .filter(c -> c.getId().equals(clienteId))
                .findFirst()
                .orElse(null);
            
            if (clienteSelecionado != null) {
                // Remover conteúdo anterior
                painelDetalhes.removeAll();
                
                // Criar e adicionar painel de detalhes
                clienteDetalhesPanel = new ClienteDetalhesPanel(clienteSelecionado);
                painelDetalhes.add(clienteDetalhesPanel, BorderLayout.CENTER);
                
                // Atualizar interface
                painelDetalhes.revalidate();
                painelDetalhes.repaint();
            }
            
        } catch (Exception e) {
            UIUtils.mostrarMensagemErro(this, "Erro ao exibir detalhes do cliente: " + e.getMessage());
        }
    }
}
