package com.vendas.util;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilitária para tratamento global de exceções.
 * Fornece métodos para logging e exibição de erros ao usuário.
 */
public class ExceptionHandler {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Trata uma exceção de banco de dados.
     * Registra o erro e exibe mensagem amigável ao usuário.
     * 
     * @param e Exceção SQL
     * @param contexto Contexto onde o erro ocorreu
     * @param parent Componente pai para exibir o diálogo
     */
    public static void tratarErroBancoDados(SQLException e, String contexto, java.awt.Component parent) {
        String mensagem = "Erro ao acessar o banco de dados";
        if (contexto != null && !contexto.isEmpty()) {
            mensagem += " (" + contexto + ")";
        }
        
        // Registrar erro no console
        logErro(mensagem, e);
        
        // Exibir mensagem ao usuário
        UIUtils.mostrarMensagemErro(parent, 
            mensagem + ".\n\nDetalhes: " + e.getMessage() + 
            "\n\nVerifique se o banco de dados está acessível.");
    }
    
    /**
     * Trata uma exceção de I/O (entrada/saída).
     * Registra o erro e exibe mensagem amigável ao usuário.
     * 
     * @param e Exceção de I/O
     * @param contexto Contexto onde o erro ocorreu
     * @param parent Componente pai para exibir o diálogo
     */
    public static void tratarErroIO(Exception e, String contexto, java.awt.Component parent) {
        String mensagem = "Erro ao processar arquivo";
        if (contexto != null && !contexto.isEmpty()) {
            mensagem += " (" + contexto + ")";
        }
        
        // Registrar erro no console
        logErro(mensagem, e);
        
        // Exibir mensagem ao usuário
        UIUtils.mostrarMensagemErro(parent, 
            mensagem + ".\n\nDetalhes: " + e.getMessage() + 
            "\n\nVerifique se o arquivo existe e está acessível.");
    }
    
    /**
     * Trata uma exceção genérica.
     * Registra o erro e exibe mensagem amigável ao usuário.
     * 
     * @param e Exceção
     * @param contexto Contexto onde o erro ocorreu
     * @param parent Componente pai para exibir o diálogo
     */
    public static void tratarErroGenerico(Exception e, String contexto, java.awt.Component parent) {
        String mensagem = "Erro inesperado";
        if (contexto != null && !contexto.isEmpty()) {
            mensagem += " (" + contexto + ")";
        }
        
        // Registrar erro no console
        logErro(mensagem, e);
        
        // Exibir mensagem ao usuário
        UIUtils.mostrarMensagemErro(parent, 
            mensagem + ".\n\nDetalhes: " + e.getMessage() + 
            "\n\nSe o problema persistir, entre em contato com o suporte.");
    }
    
    /**
     * Registra um erro no console com timestamp e stack trace.
     * 
     * @param mensagem Mensagem descritiva do erro
     * @param e Exceção ocorrida
     */
    public static void logErro(String mensagem, Exception e) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        System.err.println("=".repeat(80));
        System.err.println("[ERRO] " + timestamp + " - " + mensagem);
        System.err.println("Tipo: " + e.getClass().getName());
        System.err.println("Mensagem: " + e.getMessage());
        System.err.println("Stack Trace:");
        e.printStackTrace(System.err);
        System.err.println("=".repeat(80));
    }
    
    /**
     * Registra uma mensagem de aviso no console.
     * 
     * @param mensagem Mensagem de aviso
     */
    public static void logAviso(String mensagem) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        System.out.println("[AVISO] " + timestamp + " - " + mensagem);
    }
    
    /**
     * Registra uma mensagem informativa no console.
     * 
     * @param mensagem Mensagem informativa
     */
    public static void logInfo(String mensagem) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        System.out.println("[INFO] " + timestamp + " - " + mensagem);
    }
    
    /**
     * Converte o stack trace de uma exceção em String.
     * 
     * @param e Exceção
     * @return Stack trace como String
     */
    public static String getStackTraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
    
    /**
     * Configura um handler global para exceções não capturadas.
     * Deve ser chamado na inicialização da aplicação.
     */
    public static void configurarHandlerGlobal() {
        // Handler para exceções não capturadas em threads
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            logErro("Exceção não capturada na thread: " + thread.getName(), 
                   new Exception(throwable));
            
            // Exibir mensagem ao usuário
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(
                    null,
                    "Ocorreu um erro inesperado na aplicação.\n\n" +
                    "Detalhes: " + throwable.getMessage() + "\n\n" +
                    "A aplicação pode estar em estado inconsistente.\n" +
                    "Recomenda-se reiniciar a aplicação.",
                    "Erro Crítico",
                    JOptionPane.ERROR_MESSAGE
                );
            });
        });
        
        logInfo("Handler global de exceções configurado");
    }
}
