package com.vendas.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Janela principal da aplicação de vendas de camisas.
 * Gerencia a navegação entre os diferentes painéis do sistema.
 */
public class MainFrame extends JFrame {
    
    private JPanel painelCentral;
    private CardLayout cardLayout;
    
    // Referências aos painéis para permitir atualização
    private CadastroProdutoPanel cadastroProdutoPanel;
    private RegistroVendaPanel registroVendaPanel;
    private GerenciamentoClientePanel gerenciamentoClientePanel;
    private PainelGerenciamentoPanel painelGerenciamentoPanel;
    
    // Constantes para identificar os painéis
    public static final String PAINEL_CADASTRO_PRODUTO = "cadastroProduto";
    public static final String PAINEL_REGISTRO_VENDA = "registroVenda";
    public static final String PAINEL_GERENCIAMENTO_CLIENTE = "gerenciamentoCliente";
    public static final String PAINEL_GERENCIAMENTO_FINANCEIRO = "gerenciamentoFinanceiro";
    
    public MainFrame() {
        inicializarComponentes();
        configurarJanela();
    }
    
    /**
     * Inicializa os componentes da interface.
     */
    private void inicializarComponentes() {
        // Configurar layout principal
        setLayout(new BorderLayout());
        
        // Criar menu lateral
        JPanel menuLateral = criarMenuLateral();
        add(menuLateral, BorderLayout.WEST);
        
        // Criar área central com CardLayout
        cardLayout = new CardLayout();
        painelCentral = new JPanel(cardLayout);
        painelCentral.setBackground(Color.WHITE);
        add(painelCentral, BorderLayout.CENTER);
        
        // Adicionar painéis placeholder (serão substituídos pelos painéis reais)
        adicionarPaineisPlaceholder();
    }
    
    /**
     * Cria o menu lateral de navegação.
     */
    private JPanel criarMenuLateral() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(new Color(45, 52, 54));
        menu.setPreferredSize(new Dimension(200, 0));
        menu.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        // Título do sistema
        JLabel titulo = new JLabel("Sistema de Vendas");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        menu.add(titulo);
        
        menu.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Botões de navegação
        JButton btnCadastroProduto = criarBotaoMenu("Cadastro de Produtos");
        btnCadastroProduto.addActionListener(e -> mostrarPainel(PAINEL_CADASTRO_PRODUTO));
        menu.add(btnCadastroProduto);
        menu.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JButton btnRegistroVenda = criarBotaoMenu("Registro de Vendas");
        btnRegistroVenda.addActionListener(e -> mostrarPainel(PAINEL_REGISTRO_VENDA));
        menu.add(btnRegistroVenda);
        menu.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JButton btnGerenciamentoCliente = criarBotaoMenu("Gerenciamento de Clientes");
        btnGerenciamentoCliente.addActionListener(e -> mostrarPainel(PAINEL_GERENCIAMENTO_CLIENTE));
        menu.add(btnGerenciamentoCliente);
        menu.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JButton btnGerenciamentoFinanceiro = criarBotaoMenu("Painel Financeiro");
        btnGerenciamentoFinanceiro.addActionListener(e -> mostrarPainel(PAINEL_GERENCIAMENTO_FINANCEIRO));
        menu.add(btnGerenciamentoFinanceiro);
        
        // Espaço flexível para empurrar os botões para o topo
        menu.add(Box.createVerticalGlue());
        
        return menu;
    }
    
    /**
     * Cria um botão estilizado para o menu.
     */
    private JButton criarBotaoMenu(String texto) {
        JButton botao = new JButton(texto);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setMaximumSize(new Dimension(180, 40));
        botao.setBackground(new Color(99, 110, 114));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setFont(new Font("Arial", Font.PLAIN, 12));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efeito hover
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(116, 125, 140));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(99, 110, 114));
            }
        });
        
        return botao;
    }
    
    /**
     * Adiciona todos os painéis ao container central.
     * Mantém referências aos painéis para permitir atualização quando necessário.
     */
    private void adicionarPaineisPlaceholder() {
        // Instanciar todos os painéis
        cadastroProdutoPanel = new CadastroProdutoPanel();
        registroVendaPanel = new RegistroVendaPanel();
        gerenciamentoClientePanel = new GerenciamentoClientePanel();
        painelGerenciamentoPanel = new PainelGerenciamentoPanel();
        
        // Adicionar painéis ao container central
        painelCentral.add(cadastroProdutoPanel, PAINEL_CADASTRO_PRODUTO);
        painelCentral.add(registroVendaPanel, PAINEL_REGISTRO_VENDA);
        painelCentral.add(gerenciamentoClientePanel, PAINEL_GERENCIAMENTO_CLIENTE);
        painelCentral.add(painelGerenciamentoPanel, PAINEL_GERENCIAMENTO_FINANCEIRO);
    }
    

    /**
     * Configura as propriedades da janela.
     */
    private void configurarJanela() {
        setTitle("Sistema de Vendas de Camisas");
        setSize(1024, 768);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // Configurar fechamento da aplicação com liberação de recursos
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fecharAplicacao();
            }
        });
    }
    
    /**
     * Mostra o painel especificado e atualiza seus dados se necessário.
     * 
     * @param nomePainel Nome do painel a ser exibido
     */
    public void mostrarPainel(String nomePainel) {
        // Atualizar dados do painel antes de exibir
        atualizarPainel(nomePainel);
        
        // Exibir o painel
        cardLayout.show(painelCentral, nomePainel);
    }
    
    /**
     * Atualiza os dados do painel especificado.
     * 
     * @param nomePainel Nome do painel a ser atualizado
     */
    private void atualizarPainel(String nomePainel) {
        switch (nomePainel) {
            case PAINEL_REGISTRO_VENDA:
                // Atualizar lista de produtos disponíveis ao abrir tela de vendas
                if (registroVendaPanel != null) {
                    registroVendaPanel.carregarProdutosDisponiveis();
                }
                break;
            case PAINEL_GERENCIAMENTO_FINANCEIRO:
                // Atualizar dados financeiros ao abrir painel
                if (painelGerenciamentoPanel != null) {
                    painelGerenciamentoPanel.atualizarDados();
                }
                break;
            // Outros painéis não precisam de atualização automática
        }
    }
    
    /**
     * Adiciona um painel ao container central.
     * 
     * @param painel Painel a ser adicionado
     * @param nome Nome identificador do painel
     */
    public void adicionarPainel(JPanel painel, String nome) {
        painelCentral.add(painel, nome);
    }
    
    /**
     * Remove um painel do container central.
     * 
     * @param nome Nome identificador do painel
     */
    public void removerPainel(String nome) {
        Component[] componentes = painelCentral.getComponents();
        for (Component comp : componentes) {
            if (comp.getName() != null && comp.getName().equals(nome)) {
                painelCentral.remove(comp);
                break;
            }
        }
    }
    
    /**
     * Fecha a aplicação liberando recursos.
     */
    private void fecharAplicacao() {
        int opcao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente sair da aplicação?",
            "Confirmar Saída",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (opcao == JOptionPane.YES_OPTION) {
            try {
                // Liberar recursos do banco de dados
                com.vendas.util.ExceptionHandler.logInfo("Fechando conexão com banco de dados...");
                com.vendas.util.DatabaseManager.fecharConexao();
                com.vendas.util.ExceptionHandler.logInfo("Aplicação encerrada com sucesso");
                
            } catch (Exception e) {
                com.vendas.util.ExceptionHandler.logErro("Erro ao fechar recursos da aplicação", e);
            } finally {
                // Encerrar aplicação mesmo se houver erro ao fechar recursos
                dispose();
                System.exit(0);
            }
        }
    }
}
