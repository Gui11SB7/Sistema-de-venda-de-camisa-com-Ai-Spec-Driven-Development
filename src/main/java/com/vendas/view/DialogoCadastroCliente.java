package com.vendas.view;

import com.vendas.controller.ClienteController;
import com.vendas.model.Cliente;
import com.vendas.util.UIUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Diálogo para cadastro e edição de clientes.
 */
public class DialogoCadastroCliente extends JDialog {
    
    private final ClienteController clienteController;
    private final Cliente clienteEditando;
    private boolean confirmado = false;
    
    // Componentes da interface
    private JTextField txtNome;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JTextArea txtEndereco;
    private JButton btnSalvar;
    private JButton btnCancelar;
    
    /**
     * Construtor para novo cliente.
     * 
     * @param parent Frame pai
     */
    public DialogoCadastroCliente(Frame parent) {
        this(parent, null);
    }
    
    /**
     * Construtor para edição de cliente existente.
     * 
     * @param parent Frame pai
     * @param cliente Cliente a ser editado, ou null para novo cadastro
     */
    public DialogoCadastroCliente(Frame parent, Cliente cliente) {
        super(parent, cliente == null ? "Novo Cliente" : "Editar Cliente", true);
        this.clienteController = new ClienteController();
        this.clienteEditando = cliente;
        
        inicializarComponentes();
        configurarDialogo();
        
        if (cliente != null) {
            preencherCampos(cliente);
        }
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
     * Cria o painel com o formulário de cadastro.
     */
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Nome (obrigatório)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblNome = new JLabel("Nome: *");
        lblNome.setFont(new Font("Arial", Font.BOLD, 12));
        painel.add(lblNome, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1;
        txtNome = new JTextField(30);
        painel.add(txtNome, gbc);
        
        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setFont(new Font("Arial", Font.PLAIN, 12));
        painel.add(lblTelefone, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1;
        txtTelefone = new JTextField(30);
        painel.add(txtTelefone, gbc);
        
        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        painel.add(lblEmail, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1;
        txtEmail = new JTextField(30);
        painel.add(txtEmail, gbc);
        
        // Endereço
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel lblEndereco = new JLabel("Endereço:");
        lblEndereco.setFont(new Font("Arial", Font.PLAIN, 12));
        painel.add(lblEndereco, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        txtEndereco = new JTextArea(4, 30);
        txtEndereco.setLineWrap(true);
        txtEndereco.setWrapStyleWord(true);
        txtEndereco.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollEndereco = new JScrollPane(txtEndereco);
        painel.add(scrollEndereco, gbc);
        
        // Nota sobre campos obrigatórios
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
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
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    /**
     * Preenche os campos com os dados do cliente.
     * 
     * @param cliente Cliente cujos dados serão exibidos
     */
    private void preencherCampos(Cliente cliente) {
        txtNome.setText(cliente.getNome());
        txtTelefone.setText(cliente.getTelefone() != null ? cliente.getTelefone() : "");
        txtEmail.setText(cliente.getEmail() != null ? cliente.getEmail() : "");
        txtEndereco.setText(cliente.getEndereco() != null ? cliente.getEndereco() : "");
    }
    
    /**
     * Valida os campos do formulário.
     * 
     * @return true se todos os campos obrigatórios estão preenchidos
     */
    private boolean validarCampos() {
        if (UIUtils.campoVazio(txtNome)) {
            UIUtils.mostrarMensagemErro(this, "O nome do cliente é obrigatório");
            txtNome.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Salva o cliente (novo ou editado).
     */
    private void salvar() {
        if (!validarCampos()) {
            return;
        }
        
        try {
            Cliente cliente;
            
            if (clienteEditando == null) {
                // Novo cliente
                cliente = new Cliente();
            } else {
                // Edição de cliente existente
                cliente = clienteEditando;
            }
            
            // Preencher dados do cliente
            cliente.setNome(txtNome.getText().trim());
            cliente.setTelefone(txtTelefone.getText().trim());
            cliente.setEmail(txtEmail.getText().trim());
            cliente.setEndereco(txtEndereco.getText().trim());
            
            // Salvar no banco
            if (clienteEditando == null) {
                clienteController.cadastrarCliente(cliente);
                UIUtils.mostrarMensagemSucesso(this, "Cliente cadastrado com sucesso!");
            } else {
                clienteController.atualizarCliente(cliente);
                UIUtils.mostrarMensagemSucesso(this, "Cliente atualizado com sucesso!");
            }
            
            confirmado = true;
            dispose();
            
        } catch (IllegalArgumentException e) {
            UIUtils.mostrarMensagemErro(this, e.getMessage());
        } catch (Exception e) {
            UIUtils.mostrarMensagemErro(this, "Erro ao salvar cliente: " + e.getMessage());
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
