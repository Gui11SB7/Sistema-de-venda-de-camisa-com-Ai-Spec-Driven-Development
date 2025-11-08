package com.vendas.view;

import com.vendas.controller.ProdutoController;
import com.vendas.model.Produto;
import com.vendas.util.UIUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Painel para cadastro de produtos (camisas) no estoque.
 */
public class CadastroProdutoPanel extends JPanel {
    
    private final ProdutoController produtoController;
    
    // Componentes do formulário
    private JTextField txtDescricao;
    private JComboBox<String> cmbTamanho;
    private JButton btnSelecionarImagem;
    private JLabel lblPreviewImagem;
    private JTextField txtValorCompra;
    private JSpinner spnDataCompra;
    private JButton btnSalvar;
    private JButton btnLimpar;
    
    // Dados da imagem
    private byte[] imagemBytes;
    private ImageIcon imagemPreview;
    
    public CadastroProdutoPanel() {
        this.produtoController = new ProdutoController();
        inicializarComponentes();
        configurarListeners();
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
        JLabel lblTitulo = new JLabel("Cadastro de Produtos");
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
        
        // Campo Descrição
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblDescricao = new JLabel("Descrição:*");
        lblDescricao.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(lblDescricao, gbc);
        
        gbc.gridx = 1;
        txtDescricao = new JTextField(30);
        txtDescricao.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(txtDescricao, gbc);
        
        // Campo Tamanho
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblTamanho = new JLabel("Tamanho:*");
        lblTamanho.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(lblTamanho, gbc);
        
        gbc.gridx = 1;
        String[] tamanhos = {"PP", "P", "M", "G", "2G", "3G"};
        cmbTamanho = new JComboBox<>(tamanhos);
        cmbTamanho.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(cmbTamanho, gbc);
        
        // Campo Valor de Compra
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblValorCompra = new JLabel("Valor de Compra:*");
        lblValorCompra.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(lblValorCompra, gbc);
        
        gbc.gridx = 1;
        txtValorCompra = new JTextField(15);
        txtValorCompra.setFont(new Font("Arial", Font.PLAIN, 14));
        txtValorCompra.setToolTipText("Ex: 50.00 ou 50,00");
        painel.add(txtValorCompra, gbc);
        
        // Campo Data de Compra
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel lblDataCompra = new JLabel("Data de Compra:*");
        lblDataCompra.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(lblDataCompra, gbc);
        
        gbc.gridx = 1;
        SpinnerDateModel dateModel = new SpinnerDateModel();
        spnDataCompra = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnDataCompra, "dd/MM/yyyy");
        spnDataCompra.setEditor(dateEditor);
        spnDataCompra.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(spnDataCompra, gbc);
        
        // Campo Imagem
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel lblImagem = new JLabel("Imagem:");
        lblImagem.setFont(new Font("Arial", Font.PLAIN, 14));
        painel.add(lblImagem, gbc);
        
        gbc.gridx = 1;
        btnSelecionarImagem = new JButton("Selecionar Imagem");
        btnSelecionarImagem.setFont(new Font("Arial", Font.PLAIN, 14));
        btnSelecionarImagem.setBackground(new Color(52, 152, 219));
        btnSelecionarImagem.setForeground(Color.WHITE);
        btnSelecionarImagem.setFocusPainted(false);
        btnSelecionarImagem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painel.add(btnSelecionarImagem, gbc);
        
        // Preview da Imagem
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        lblPreviewImagem = new JLabel();
        lblPreviewImagem.setPreferredSize(new Dimension(200, 200));
        lblPreviewImagem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        lblPreviewImagem.setHorizontalAlignment(SwingConstants.CENTER);
        lblPreviewImagem.setText("Nenhuma imagem selecionada");
        painel.add(lblPreviewImagem, gbc);
        
        // Painel de Botões
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel painelBotoes = criarPainelBotoes();
        painel.add(painelBotoes, gbc);
        
        return painel;
    }
    
    /**
     * Cria o painel com os botões de ação.
     */
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        painel.setBackground(Color.WHITE);
        
        // Botão Salvar
        btnSalvar = new JButton("Salvar");
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalvar.setPreferredSize(new Dimension(120, 40));
        btnSalvar.setBackground(new Color(46, 204, 113));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painel.add(btnSalvar);
        
        // Botão Limpar
        btnLimpar = new JButton("Limpar");
        btnLimpar.setFont(new Font("Arial", Font.BOLD, 14));
        btnLimpar.setPreferredSize(new Dimension(120, 40));
        btnLimpar.setBackground(new Color(149, 165, 166));
        btnLimpar.setForeground(Color.WHITE);
        btnLimpar.setFocusPainted(false);
        btnLimpar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painel.add(btnLimpar);
        
        // Adicionar listeners
        btnLimpar.addActionListener(e -> limparFormulario());
        
        return painel;
    }
    
    /**
     * Configura os listeners dos componentes.
     */
    private void configurarListeners() {
        // Listener para seleção de imagem
        btnSelecionarImagem.addActionListener(e -> selecionarImagem());
        
        // Listener para salvar produto
        btnSalvar.addActionListener(e -> salvarProduto());
    }
    
    /**
     * Abre diálogo para seleção de imagem e exibe preview.
     */
    private void selecionarImagem() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecionar Imagem do Produto");
        
        // Filtro para aceitar apenas JPG e PNG
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(
            "Imagens (*.jpg, *.jpeg, *.png)", "jpg", "jpeg", "png"
        );
        fileChooser.setFileFilter(filtro);
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        int resultado = fileChooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivoSelecionado = fileChooser.getSelectedFile();
            
            // Validar formato de imagem
            String nomeArquivo = arquivoSelecionado.getName().toLowerCase();
            if (!nomeArquivo.endsWith(".jpg") && !nomeArquivo.endsWith(".jpeg") && !nomeArquivo.endsWith(".png")) {
                UIUtils.mostrarMensagemErro(this, "Formato de imagem não suportado. Use JPG ou PNG.");
                return;
            }
            
            // Validar tamanho do arquivo (máximo 5MB)
            long tamanhoArquivo = arquivoSelecionado.length();
            long tamanhoMaximo = 5 * 1024 * 1024; // 5MB em bytes
            if (tamanhoArquivo > tamanhoMaximo) {
                UIUtils.mostrarMensagemErro(this, "A imagem é muito grande. O tamanho máximo é 5MB.");
                return;
            }
            
            try {
                // Converter imagem para byte array
                imagemBytes = Files.readAllBytes(arquivoSelecionado.toPath());
                
                // Exibir preview da imagem
                exibirPreviewImagem(arquivoSelecionado);
                
            } catch (IOException ex) {
                UIUtils.mostrarMensagemErro(this, "Erro ao carregar imagem: " + ex.getMessage());
                imagemBytes = null;
            }
        }
    }
    
    /**
     * Exibe o preview da imagem selecionada.
     */
    private void exibirPreviewImagem(File arquivo) {
        try {
            // Carregar imagem original
            ImageIcon iconOriginal = new ImageIcon(arquivo.getAbsolutePath());
            Image imagemOriginal = iconOriginal.getImage();
            
            // Redimensionar imagem para caber no preview (200x200)
            int larguraPreview = 200;
            int alturaPreview = 200;
            
            // Calcular dimensões mantendo proporção
            int larguraOriginal = imagemOriginal.getWidth(null);
            int alturaOriginal = imagemOriginal.getHeight(null);
            
            double proporcao = Math.min(
                (double) larguraPreview / larguraOriginal,
                (double) alturaPreview / alturaOriginal
            );
            
            int novaLargura = (int) (larguraOriginal * proporcao);
            int novaAltura = (int) (alturaOriginal * proporcao);
            
            // Redimensionar imagem
            Image imagemRedimensionada = imagemOriginal.getScaledInstance(
                novaLargura, novaAltura, Image.SCALE_SMOOTH
            );
            
            // Criar e definir o ícone redimensionado
            imagemPreview = new ImageIcon(imagemRedimensionada);
            lblPreviewImagem.setIcon(imagemPreview);
            lblPreviewImagem.setText("");
            
        } catch (Exception ex) {
            UIUtils.mostrarMensagemErro(this, "Erro ao exibir preview da imagem: " + ex.getMessage());
        }
    }
    
    /**
     * Salva o produto no banco de dados.
     */
    private void salvarProduto() {
        try {
            // Validar campos obrigatórios
            if (!validarCampos()) {
                return;
            }
            
            // Criar objeto Produto com dados do formulário
            Produto produto = new Produto();
            produto.setDescricao(txtDescricao.getText().trim());
            produto.setTamanho((String) cmbTamanho.getSelectedItem());
            produto.setImagem(imagemBytes); // Pode ser null
            
            // Converter valor de compra
            BigDecimal valorCompra = UIUtils.converterParaBigDecimal(txtValorCompra.getText());
            produto.setValorCompra(valorCompra);
            
            // Converter data de compra
            Date dataSelecionada = (Date) spnDataCompra.getValue();
            LocalDate dataCompra = dataSelecionada.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
            produto.setDataCompra(dataCompra);
            
            produto.setVendido(false);
            
            // Chamar controller para cadastrar produto
            boolean sucesso = produtoController.cadastrarProduto(produto);
            
            if (sucesso) {
                UIUtils.mostrarMensagemSucesso(this, "Produto cadastrado com sucesso!");
                limparFormulario();
            }
            
        } catch (IllegalArgumentException ex) {
            UIUtils.mostrarMensagemErro(this, ex.getMessage());
        } catch (RuntimeException ex) {
            UIUtils.mostrarMensagemErro(this, "Erro ao salvar produto: " + ex.getMessage());
        } catch (Exception ex) {
            UIUtils.mostrarMensagemErro(this, "Erro inesperado: " + ex.getMessage());
        }
    }
    
    /**
     * Valida todos os campos obrigatórios do formulário.
     */
    private boolean validarCampos() {
        // Validar descrição
        if (UIUtils.campoVazio(txtDescricao)) {
            UIUtils.mostrarMensagemErro(this, "Por favor, preencha a descrição do produto.");
            txtDescricao.requestFocus();
            return false;
        }
        
        // Validar tamanho
        if (UIUtils.comboBoxVazio(cmbTamanho)) {
            UIUtils.mostrarMensagemErro(this, "Por favor, selecione o tamanho do produto.");
            cmbTamanho.requestFocus();
            return false;
        }
        
        // Validar valor de compra
        if (UIUtils.campoVazio(txtValorCompra)) {
            UIUtils.mostrarMensagemErro(this, "Por favor, preencha o valor de compra.");
            txtValorCompra.requestFocus();
            return false;
        }
        
        BigDecimal valorCompra = UIUtils.converterParaBigDecimal(txtValorCompra.getText());
        if (valorCompra == null) {
            UIUtils.mostrarMensagemErro(this, "Valor de compra inválido. Use apenas números (ex: 50.00 ou 50,00).");
            txtValorCompra.requestFocus();
            return false;
        }
        
        if (valorCompra.signum() <= 0) {
            UIUtils.mostrarMensagemErro(this, "O valor de compra deve ser maior que zero.");
            txtValorCompra.requestFocus();
            return false;
        }
        
        // Validar data de compra
        if (spnDataCompra.getValue() == null) {
            UIUtils.mostrarMensagemErro(this, "Por favor, selecione a data de compra.");
            spnDataCompra.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Limpa todos os campos do formulário.
     */
    private void limparFormulario() {
        txtDescricao.setText("");
        cmbTamanho.setSelectedIndex(0);
        txtValorCompra.setText("");
        spnDataCompra.setValue(new Date());
        imagemBytes = null;
        imagemPreview = null;
        lblPreviewImagem.setIcon(null);
        lblPreviewImagem.setText("Nenhuma imagem selecionada");
    }
    
    /**
     * Obtém o botão de selecionar imagem para adicionar listener externamente.
     */
    public JButton getBtnSelecionarImagem() {
        return btnSelecionarImagem;
    }
    
    /**
     * Obtém o botão de salvar para adicionar listener externamente.
     */
    public JButton getBtnSalvar() {
        return btnSalvar;
    }
    
    /**
     * Obtém o label de preview da imagem.
     */
    public JLabel getLblPreviewImagem() {
        return lblPreviewImagem;
    }
    
    /**
     * Define os bytes da imagem.
     */
    public void setImagemBytes(byte[] imagemBytes) {
        this.imagemBytes = imagemBytes;
    }
    
    /**
     * Obtém os bytes da imagem.
     */
    public byte[] getImagemBytes() {
        return imagemBytes;
    }
    
    /**
     * Obtém o campo de descrição.
     */
    public JTextField getTxtDescricao() {
        return txtDescricao;
    }
    
    /**
     * Obtém o combo de tamanho.
     */
    public JComboBox<String> getCmbTamanho() {
        return cmbTamanho;
    }
    
    /**
     * Obtém o campo de valor de compra.
     */
    public JTextField getTxtValorCompra() {
        return txtValorCompra;
    }
    
    /**
     * Obtém o spinner de data de compra.
     */
    public JSpinner getSpnDataCompra() {
        return spnDataCompra;
    }
    
    /**
     * Obtém o controller de produtos.
     */
    public ProdutoController getProdutoController() {
        return produtoController;
    }
}
