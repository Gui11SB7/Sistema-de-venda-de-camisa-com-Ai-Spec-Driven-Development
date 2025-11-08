package com.vendas;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Classe principal da aplicação Sistema de Vendas de Camisas.
 * Responsável por inicializar o banco de dados e a interface gráfica.
 */
public class Main {
    
    public static void main(String[] args) {
        // Configurar handler global de exceções
        com.vendas.util.ExceptionHandler.configurarHandlerGlobal();
        
        // Configurar look and feel do sistema para melhor aparência
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            com.vendas.util.ExceptionHandler.logInfo("Look and feel configurado: " + 
                UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            com.vendas.util.ExceptionHandler.logAviso("Erro ao configurar look and feel: " + 
                e.getMessage() + ". Usando look and feel padrão.");
            // Continuar mesmo se falhar - usar look and feel padrão
        }
        
        // Inicializar a aplicação na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            try {
                com.vendas.util.ExceptionHandler.logInfo("Sistema de Vendas de Camisas - Iniciando...");
                
                // Inicializar DatabaseManager e criar tabelas
                com.vendas.util.ExceptionHandler.logInfo("Inicializando banco de dados...");
                com.vendas.util.DatabaseManager.inicializarBanco();
                com.vendas.util.ExceptionHandler.logInfo("Banco de dados inicializado com sucesso!");
                
                // Instanciar e exibir MainFrame
                com.vendas.util.ExceptionHandler.logInfo("Carregando interface gráfica...");
                com.vendas.view.MainFrame mainFrame = new com.vendas.view.MainFrame();
                mainFrame.setVisible(true);
                
                com.vendas.util.ExceptionHandler.logInfo("Aplicação iniciada com sucesso!");
                
            } catch (java.sql.SQLException e) {
                com.vendas.util.ExceptionHandler.tratarErroBancoDados(e, "inicialização da aplicação", null);
                System.exit(1);
            } catch (Exception e) {
                com.vendas.util.ExceptionHandler.logErro("Erro crítico ao inicializar aplicação", e);
                
                // Exibir mensagem de erro ao usuário
                JOptionPane.showMessageDialog(
                    null,
                    "Erro ao inicializar a aplicação:\n" + e.getMessage() +
                    "\n\nVerifique os logs para mais detalhes.",
                    "Erro de Inicialização",
                    JOptionPane.ERROR_MESSAGE
                );
                
                // Encerrar aplicação em caso de erro crítico
                System.exit(1);
            }
        });
    }
}
