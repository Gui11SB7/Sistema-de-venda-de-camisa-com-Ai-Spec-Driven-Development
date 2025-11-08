package com.vendas.view;

import com.vendas.controller.ClienteController;
import com.vendas.controller.ProdutoController;
import com.vendas.controller.VendaController;
import com.vendas.model.Cliente;
import com.vendas.model.Produto;
import com.vendas.model.Venda;
import com.vendas.util.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Painel para registro de vendas de produtos.
 */
public class RegistroVendaPanel extends JPanel {
    
    private final ProdutoController produtoController;
    private final ClienteController clienteController;
    private final VendaController vendaController;
    
    // Componentes do formulário
    private JComboBox<ProdutoComboItem> cmbProduto;
    private JPanel painelDetalhesProduto;
    private JLabel lblDescricaoProduto;
    private JLabel lblTamanhoProduto;
    private JLabel lblValorCompraProduto;
    private JLabel lblImagemProduto;
    private JComboBox<ClienteComboItem> cmbCliente;
    private JSpinner spnDataVenda;
    private JTextField txtValorVenda;
    private JButton btnRegistrarVenda;
    private JButton btnCancelar;
    
    public RegistroVendaPanel() {
        this.produtoController = new ProdutoController();
        this.clienteController = new ClienteController();
        this.vendaController = new VendaController();
        inicializarComponentes();
        configurarListeners();
        carregarDados();
    }
    
    /**
     * Inicializa os componentes da interface.
     */
    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Painel principal com formulário
        JPanel painelFormulario = criarPainelFormulario();
        add(painelFormulario, BorderLayout.CENTER);
    }
    
    /**
     * Cria o painel do formulário com todos os campos.
     */
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título
        JLabel lblTitulo = new JLabel("Registro de Vendas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(45, 52, 54));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(lblTitulo, gbc);
        
        // Resetar gridwidth
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Campo Produto
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblProduto = new JLabel("Produto:*");
        lblProduto.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(lblProduto, gbc);
        
        gbc.gridx = 1;
        cmbProduto = new JComboBox<>();
        cmbProduto.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbProduto.setPreferredSize(new Dimension(400, 25));
        painel.add(cmbProduto, gbc);
        
        // Painel de detalhes do produto
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        painelDetalhesProduto = criarPainelDetalhesProduto();
        painel.add(painelDetalhesProduto, gbc);
        
        // Resetar configurações
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Campo Cliente
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblCliente = new JLabel("Cliente:*");
        lblCliente.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(lblCliente, gbc);
        
        gbc.gridx = 1;
        cmbCliente = new JComboBox<>();
        cmbCliente.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbCliente.setPreferredSize(new Dimension(400, 25));
        painel.add(cmbCliente, gbc);
        
        // Campo Data da Venda
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblDataVenda = new JLabel("Data da Venda:*");
        lblDataVenda.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(lblDataVenda, gbc);
        
        gbc.gridx = 1;
        SpinnerDateModel dateModel = new SpinnerDateModel();
        spnDataVenda = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnDataVenda, "dd/MM/yyyy");
        spnDataVenda.setEditor(dateEditor);
        spnDataVenda.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(spnDataVenda, gbc);
        
        // Campo Valor de Venda
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel lblValorVenda = new JLabel("Valor de Venda:*");
        lblValorVenda.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(lblValorVenda, gbc);
        
        gbc.gridx = 1;
        txtValorVenda = new JTextField(15);
        txtValorVenda.setFont(new Font("Arial", Font.PLAIN, 14));
        txtValorVenda.setToolTipText("Ex: 150.00 ou 150,00");
        painel.add(txtValorVenda, gbc);
        
        // Painel de Botões
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel painelBotoes = criarPainelBotoes();
        painel.add(painelBotoes, gbc);
        
        return painel;
    }
    
    /**
     * Cria o painel de detalhes do produto selecionado.
     */
    private JPanel criarPainelDetalhesProduto() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(new Color(245, 245, 245));
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Detalhes do Produto"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        painel.setPreferredSize(new Dimension(500, 250));
        
        // Painel esquerdo com imagem
        JPanel painelImagem = new JPanel(new BorderLayout());
        painelImagem.setBackground(new Color(245, 245, 245));
        lblImagemProduto = new JLabel();
        lblImagemProduto.setPreferredSize(new Dimension(180, 180));
        lblImagemProduto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        lblImagemProduto.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagemProduto.setText("Sem imagem");
        painelImagem.add(lblImagemProduto, BorderLayout.CENTER);
        
        // Painel direito com informações
        JPanel painelInfo = new JPanel(new GridLayout(3, 1, 5, 10));
        painelInfo.setBackground(new Color(245, 245, 245));
        painelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        lblDescricaoProduto = new JLabel("Descrição: -");
        lblDescricaoProduto.setFont(new Font("Arial", Font.PLAIN, 14));
        
        lblTamanhoProduto = new JLabel("Tamanho: -");
        lblTamanhoProduto.setFont(new Font("Arial", Font.PLAIN, 14));
        
        lblValorCompraProduto = new JLabel("Valor de Compra: -");
        lblValorCompraProduto.setFont(new Font("Arial", Font.PLAIN, 14));
        
        painelInfo.add(lblDescricaoProduto);
        painelInfo.add(lblTamanhoProduto);
        painelInfo.add(lblValorCompraProduto);
        
        painel.add(painelImagem, BorderLayout.WEST);
        painel.add(painelInfo, BorderLayout.CENTER);
        
        return painel;
    }
    
    /**
     * Cria o painel com os botões de ação.
     */
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        painel.setBackground(Color.WHITE);
        
        // Botão Registrar Venda
        btnRegistrarVenda = new JButton("Registrar Venda");
        btnRegistrarVenda.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrarVenda.setPreferredSize(new Dimension(160, 40));
        btnRegistrarVenda.setBackground(new Color(46, 204, 113));
        btnRegistrarVenda.setForeground(Color.WHITE);
        btnRegistrarVenda.setFocusPainted(false);
        btnRegistrarVenda.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painel.add(btnRegistrarVenda);
        
        // Botão Cancelar
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setPreferredSize(new Dimension(120, 40));
        btnCancelar.setBackground(new Color(149, 165, 166));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painel.add(btnCancelar);
        
        // Adicionar listener para cancelar
        btnCancelar.addActionListener(e -> limparFormulario());
        
        return painel;
    }
    
    /**
     * Configura os listeners dos componentes.
     */
    private void configurarListeners() {
        // Listener para atualizar detalhes quando produto é selecionado
        cmbProduto.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                atualizarDetalhesProduto();
            }
        });
        
        // Listener para registrar venda
        btnRegistrarVenda.addActionListener(e -> registrarVenda());
    }
    
    /**
     * Carrega os dados iniciais (produtos e clientes).
     */
    private void carregarDados() {
        carregarProdutosDisponiveis();
        carregarClientes();
    }
    
    /**
     * Carrega a lista de produtos disponíveis no ComboBox.
     */
    public void carregarProdutosDisponiveis() {
        try {
            // Limpar combo antes de carregar
            cmbProduto.removeAllItems();
            
            // Buscar produtos disponíveis
            List<Produto> produtos = produtoController.listarProdutosDisponiveis();
            
            // Adicionar produtos ao combo
            for (Produto produto : produtos) {
                cmbProduto.addItem(new ProdutoComboItem(produto));
            }
            
            // Limpar seleção inicial
            if (cmbProduto.getItemCount() > 0) {
                cmbProduto.setSelectedIndex(-1);
            }
            
        } catch (Exception ex) {
            UIUtils.mostrarMensagemErro(this, "Erro ao carregar produtos: " + ex.getMessage());
        }
    }
    
    /**
     * Carrega a lista de clientes no ComboBox.
     */
    private void carregarClientes() {
        try {
            // Limpar combo antes de carregar
            cmbCliente.removeAllItems();
            
            // Buscar clientes
            List<Cliente> clientes = clienteController.listarClientes();
            
            // Adicionar clientes ao combo
            for (Cliente cliente : clientes) {
                cmbCliente.addItem(new ClienteComboItem(cliente));
            }
            
            // Limpar seleção inicial
            if (cmbCliente.getItemCount() > 0) {
                cmbCliente.setSelectedIndex(-1);
            }
            
        } catch (Exception ex) {
            UIUtils.mostrarMensagemErro(this, "Erro ao carregar clientes: " + ex.getMessage());
        }
    }
    
    /**
     * Atualiza os detalhes do produto selecionado.
     */
    private void atualizarDetalhesProduto() {
        ProdutoComboItem itemSelecionado = (ProdutoComboItem) cmbProduto.getSelectedItem();
        
        if (itemSelecionado == null) {
            limparDetalhesProduto();
            return;
        }
        
        Produto produto = itemSelecionado.getProduto();
        
        // Atualizar labels com informações do produto
        lblDescricaoProduto.setText("Descrição: " + produto.getDescricao());
        lblTamanhoProduto.setText("Tamanho: " + produto.getTamanho());
        lblValorCompraProduto.setText("Valor de Compra: " + 
            UIUtils.formatarValorMonetario(produto.getValorCompra()));
        
        // Exibir imagem se disponível
        if (produto.getImagem() != null && produto.getImagem().length > 0) {
            exibirImagemProduto(produto.getImagem());
        } else {
            lblImagemProduto.setIcon(null);
            lblImagemProduto.setText("Sem imagem");
        }
    }
    
    /**
     * Exibe a imagem do produto no label.
     */
    private void exibirImagemProduto(byte[] imagemBytes) {
        try {
            // Criar ImageIcon a partir dos bytes
            ImageIcon iconOriginal = new ImageIcon(imagemBytes);
            Image imagemOriginal = iconOriginal.getImage();
            
            // Redimensionar imagem para caber no label (180x180)
            int larguraLabel = 180;
            int alturaLabel = 180;
            
            // Calcular dimensões mantendo proporção
            int larguraOriginal = imagemOriginal.getWidth(null);
            int alturaOriginal = imagemOriginal.getHeight(null);
            
            double proporcao = Math.min(
                (double) larguraLabel / larguraOriginal,
                (double) alturaLabel / alturaOriginal
            );
            
            int novaLargura = (int) (larguraOriginal * proporcao);
            int novaAltura = (int) (alturaOriginal * proporcao);
            
            // Redimensionar imagem
            Image imagemRedimensionada = imagemOriginal.getScaledInstance(
                novaLargura, novaAltura, Image.SCALE_SMOOTH
            );
            
            // Definir o ícone redimensionado
            lblImagemProduto.setIcon(new ImageIcon(imagemRedimensionada));
            lblImagemProduto.setText("");
            
        } catch (Exception ex) {
            lblImagemProduto.setIcon(null);
            lblImagemProduto.setText("Erro ao carregar imagem");
        }
    }
    
    /**
     * Registra uma nova venda.
     */
    private void registrarVenda() {
        try {
            // Validar campos obrigatórios
            if (!validarCampos()) {
                return;
            }
            
            // Obter produto selecionado
            ProdutoComboItem produtoItem = (ProdutoComboItem) cmbProduto.getSelectedItem();
            Produto produto = produtoItem.getProduto();
            
            // Obter cliente selecionado
            ClienteComboItem clienteItem = (ClienteComboItem) cmbCliente.getSelectedItem();
            Cliente cliente = clienteItem.getCliente();
            
            // Converter valor de venda
            BigDecimal valorVenda = UIUtils.converterParaBigDecimal(txtValorVenda.getText());
            
            // Converter data de venda
            Date dataSelecionada = (Date) spnDataVenda.getValue();
            LocalDate dataVenda = dataSelecionada.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
            
            // Criar objeto Venda
            Venda venda = new Venda();
            venda.setProdutoId(produto.getId());
            venda.setClienteId(cliente.getId());
            venda.setDataVenda(dataVenda);
            venda.setValorVenda(valorVenda);
            
            // Chamar controller para registrar venda
            boolean sucesso = vendaController.registrarVenda(venda);
            
            if (sucesso) {
                UIUtils.mostrarMensagemSucesso(this, "Venda registrada com sucesso!");
                
                // Atualizar lista de produtos disponíveis
                carregarProdutosDisponiveis();
                
                // Limpar formulário
                limparFormulario();
            }
            
        } catch (IllegalArgumentException ex) {
            UIUtils.mostrarMensagemErro(this, ex.getMessage());
        } catch (RuntimeException ex) {
            UIUtils.mostrarMensagemErro(this, "Erro ao registrar venda: " + ex.getMessage());
        } catch (Exception ex) {
            UIUtils.mostrarMensagemErro(this, "Erro inesperado: " + ex.getMessage());
        }
    }
    
    /**
     * Valida todos os campos obrigatórios do formulário.
     */
    private boolean validarCampos() {
        // Validar seleção de produto
        if (cmbProduto.getSelectedIndex() == -1 || cmbProduto.getSelectedItem() == null) {
            UIUtils.mostrarMensagemErro(this, "Por favor, selecione um produto.");
            cmbProduto.requestFocus();
            return false;
        }
        
        // Validar seleção de cliente
        if (cmbCliente.getSelectedIndex() == -1 || cmbCliente.getSelectedItem() == null) {
            UIUtils.mostrarMensagemErro(this, "Por favor, selecione um cliente.");
            cmbCliente.requestFocus();
            return false;
        }
        
        // Validar data de venda
        if (spnDataVenda.getValue() == null) {
            UIUtils.mostrarMensagemErro(this, "Por favor, selecione a data da venda.");
            spnDataVenda.requestFocus();
            return false;
        }
        
        // Validar valor de venda
        if (UIUtils.campoVazio(txtValorVenda)) {
            UIUtils.mostrarMensagemErro(this, "Por favor, preencha o valor de venda.");
            txtValorVenda.requestFocus();
            return false;
        }
        
        BigDecimal valorVenda = UIUtils.converterParaBigDecimal(txtValorVenda.getText());
        if (valorVenda == null) {
            UIUtils.mostrarMensagemErro(this, "Valor de venda inválido. Use apenas números (ex: 150.00 ou 150,00).");
            txtValorVenda.requestFocus();
            return false;
        }
        
        if (valorVenda.signum() <= 0) {
            UIUtils.mostrarMensagemErro(this, "O valor de venda deve ser maior que zero.");
            txtValorVenda.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Limpa todos os campos do formulário.
     */
    private void limparFormulario() {
        cmbProduto.setSelectedIndex(-1);
        cmbCliente.setSelectedIndex(-1);
        txtValorVenda.setText("");
        spnDataVenda.setValue(new Date());
        limparDetalhesProduto();
    }
    
    /**
     * Limpa os detalhes do produto exibidos.
     */
    private void limparDetalhesProduto() {
        lblDescricaoProduto.setText("Descrição: -");
        lblTamanhoProduto.setText("Tamanho: -");
        lblValorCompraProduto.setText("Valor de Compra: -");
        lblImagemProduto.setIcon(null);
        lblImagemProduto.setText("Sem imagem");
    }
    
    /**
     * Classe auxiliar para representar um produto no ComboBox.
     */
    private static class ProdutoComboItem {
        private final Produto produto;
        
        public ProdutoComboItem(Produto produto) {
            this.produto = produto;
        }
        
        public Produto getProduto() {
            return produto;
        }
        
        @Override
        public String toString() {
            return produto.getDescricao() + " - " + produto.getTamanho() + 
                   " (ID: " + produto.getId() + ")";
        }
    }
    
    /**
     * Classe auxiliar para representar um cliente no ComboBox.
     */
    private static class ClienteComboItem {
        private final Cliente cliente;
        
        public ClienteComboItem(Cliente cliente) {
            this.cliente = cliente;
        }
        
        public Cliente getCliente() {
            return cliente;
        }
        
        @Override
        public String toString() {
            return cliente.getNome() + " (ID: " + cliente.getId() + ")";
        }
    }
}
