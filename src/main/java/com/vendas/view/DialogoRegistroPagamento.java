package com.vendas.view;

import com.vendas.controller.PagamentoController;
import com.vendas.model.Pagamento;
import com.vendas.util.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Diálogo para registro de pagamentos de clientes.
 */
public class DialogoRegistroPagamento extends JDialog {
    
    private final PagamentoController pagamentoController;
    private final Long clienteId;
    private boolean confirmado = false;
    
    // Componentes da interface
    private JSpinner spnDataPagamento;
    private JTextField txtValor;
    private JTextArea txtObservacao;
    private JButton btnSalvar;
    private JButton btnCancelar;
    
    /**
     * Construtor para registro de pagamento.
     * 
     * @param parent Frame pai
     * @param clienteId ID do cliente que está realizando o pagamento
     */
    public DialogoRegistroPagamento(Frame parent, Long clienteId) {
        super(parent, "Registrar Pagamento", true);
        this.pagamentoController = new PagamentoController();
        this.clienteId = clienteId;
        
        inicializarComponentes();
        configurarDialogo();
    }
    
    /**
     * Inicializa os componentes da interface.
     */
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        
        // Painel central com formulário
        JPanel painelFormulario = criarPainelFormulario();
        add(painelFormulario, BorderLayout.CENTER);
        
        // Painel inferior com botões
        JPanel painelBotoes = criarPainelBotoes();
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    /**
     * Cria o painel com o formulário de registro.
     */
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Data do pagamento (obrigatório)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblData = new JLabel("Data: *");
        lblData.setFont(new Font("Arial", Font.BOLD, 12));
        painel.add(lblData, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1;
        SpinnerDateModel dateModel = new SpinnerDateModel();
        spnDataPagamento = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnDataPagamento, "dd/MM/yyyy");
        spnDataPagamento.setEditor(dateEditor);
        spnDataPagamento.setValue(new Date()); // Data atual como padrão
        spnDataPagamento.setPreferredSize(new Dimension(250, 25));
        painel.add(spnDataPagamento, gbc);
        
        // Valor do pagamento (obrigatório)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblValor = new JLabel("Valor: *");
        lblValor.setFont(new Font("Arial", Font.BOLD, 12));
        painel.add(lblValor, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1;
        txtValor = new JTextField(20);
        txtValor.setToolTipText("Digite o valor do pagamento (ex: 150.00 ou 150,00)");
        painel.add(txtValor, gbc);
        
        // Observação (opcional)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel lblObservacao = new JLabel("Observação:");
        lblObservacao.setFont(new Font("Arial", Font.PLAIN, 12));
        painel.add(lblObservacao, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        txtObservacao = new JTextArea(4, 20);
        txtObservacao.setLineWrap(true);
        txtObservacao.setWrapStyleWord(true);
        txtObservacao.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtObservacao.setToolTipText("Informações adicionais sobre o pagamento (opcional)");
        JScrollPane scrollObservacao = new JScrollPane(txtObservacao);
        painel.add(scrollObservacao, gbc);
        
        // Nota sobre campos obrigatórios
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel lblNota = new JLabel("* Campos obrigatórios");
        lblNota.setFont(new Font("Arial", Font.ITALIC, 10));
        lblNota.setForeground(Color.GRAY);
        painel.add(lblNota, gbc);
        
        return painel;
    }
    
    /**
     * Cria o painel com os botões de ação.
     */
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 35));
        btnCancelar.setBackground(new Color(149, 165, 166));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(e -> cancelar());
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.setPreferredSize(new Dimension(100, 35));
        btnSalvar.setBackground(new Color(46, 204, 113));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorderPainted(false);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalvar.addActionListener(e -> salvar());
        
        painel.add(btnCancelar);
        painel.add(btnSalvar);
        
        return painel;
    }
    
    /**
     * Configura as propriedades do diálogo.
     */
    private void configurarDialogo() {
        setSize(450, 350);
        setResizable(false);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    /**
     * Valida os campos do formulário.
     * 
     * @return true se todos os campos obrigatórios estão preenchidos e válidos
     */
    private boolean validarCampos() {
        // Validar data
        if (spnDataPagamento.getValue() == null) {
            UIUtils.mostrarMensagemErro(this, "A data do pagamento é obrigatória");
            spnDataPagamento.requestFocus();
            return false;
        }
        
        // Validar valor
        if (UIUtils.campoVazio(txtValor)) {
            UIUtils.mostrarMensagemErro(this, "O valor do pagamento é obrigatório");
            txtValor.requestFocus();
            return false;
        }
        
        // Validar se o valor é um número válido
        BigDecimal valor = UIUtils.converterParaBigDecimal(txtValor.getText());
        if (valor == null) {
            UIUtils.mostrarMensagemErro(this, "Digite um valor válido (ex: 150.00 ou 150,00)");
            txtValor.requestFocus();
            return false;
        }
        
        // Validar se o valor é positivo
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            UIUtils.mostrarMensagemErro(this, "O valor do pagamento deve ser maior que zero");
            txtValor.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Salva o pagamento.
     */
    private void salvar() {
        if (!validarCampos()) {
            return;
        }
        
        try {
            // Criar objeto Pagamento
            Pagamento pagamento = new Pagamento();
            pagamento.setClienteId(clienteId);
            
            // Converter Date para LocalDate
            Date dataSelecionada = (Date) spnDataPagamento.getValue();
            LocalDate dataLocal = dataSelecionada.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
            pagamento.setDataPagamento(dataLocal);
            
            // Converter valor
            BigDecimal valor = UIUtils.converterParaBigDecimal(txtValor.getText());
            pagamento.setValorPago(valor);
            
            // Observação (opcional)
            String observacao = txtObservacao.getText().trim();
            if (!observacao.isEmpty()) {
                pagamento.setObservacao(observacao);
            }
            
            // Registrar pagamento usando o controller
            pagamentoController.registrarPagamento(pagamento);
            
            UIUtils.mostrarMensagemSucesso(this, "Pagamento registrado com sucesso!");
            
            confirmado = true;
            dispose();
            
        } catch (IllegalArgumentException e) {
            UIUtils.mostrarMensagemErro(this, e.getMessage());
        } catch (Exception e) {
            UIUtils.mostrarMensagemErro(this, "Erro ao registrar pagamento: " + e.getMessage());
        }
    }
    
    /**
     * Cancela a operação e fecha o diálogo.
     */
    private void cancelar() {
        confirmado = false;
        dispose();
    }
    
    /**
     * Verifica se o diálogo foi confirmado (salvou).
     * 
     * @return true se o usuário salvou, false se cancelou
     */
    public boolean isConfirmado() {
        return confirmado;
    }
}
