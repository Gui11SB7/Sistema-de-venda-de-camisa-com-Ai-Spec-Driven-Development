package com.vendas.util;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Classe de utilidades para componentes de interface do usuário.
 * Fornece métodos auxiliares para formatação, validação e exibição de mensagens.
 */
public class UIUtils {
    
    private static final NumberFormat FORMATO_MOEDA = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    
    /**
     * Formata um valor BigDecimal para string monetária no formato brasileiro.
     * 
     * @param valor Valor a ser formatado
     * @return String formatada como moeda (ex: "R$ 150,00")
     */
    public static String formatarValorMonetario(BigDecimal valor) {
        if (valor == null) {
            return "R$ 0,00";
        }
        return FORMATO_MOEDA.format(valor);
    }
    
    /**
     * Formata um valor double para string monetária no formato brasileiro.
     * 
     * @param valor Valor a ser formatado
     * @return String formatada como moeda (ex: "R$ 150,00")
     */
    public static String formatarValorMonetario(double valor) {
        return FORMATO_MOEDA.format(valor);
    }
    
    /**
     * Exibe uma mensagem de sucesso ao usuário.
     * 
     * @param componente Componente pai para centralizar o diálogo
     * @param mensagem Mensagem a ser exibida
     */
    public static void mostrarMensagemSucesso(Component componente, String mensagem) {
        JOptionPane.showMessageDialog(
            componente,
            mensagem,
            "Sucesso",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Exibe uma mensagem de sucesso ao usuário com título customizado.
     * 
     * @param componente Componente pai para centralizar o diálogo
     * @param mensagem Mensagem a ser exibida
     * @param titulo Título da janela de diálogo
     */
    public static void mostrarMensagemSucesso(Component componente, String mensagem, String titulo) {
        JOptionPane.showMessageDialog(
            componente,
            mensagem,
            titulo,
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Exibe uma mensagem de erro ao usuário.
     * 
     * @param componente Componente pai para centralizar o diálogo
     * @param mensagem Mensagem de erro a ser exibida
     */
    public static void mostrarMensagemErro(Component componente, String mensagem) {
        JOptionPane.showMessageDialog(
            componente,
            mensagem,
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Exibe uma mensagem de erro ao usuário com título customizado.
     * 
     * @param componente Componente pai para centralizar o diálogo
     * @param mensagem Mensagem de erro a ser exibida
     * @param titulo Título da janela de diálogo
     */
    public static void mostrarMensagemErro(Component componente, String mensagem, String titulo) {
        JOptionPane.showMessageDialog(
            componente,
            mensagem,
            titulo,
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Exibe uma mensagem de aviso ao usuário.
     * 
     * @param componente Componente pai para centralizar o diálogo
     * @param mensagem Mensagem de aviso a ser exibida
     */
    public static void mostrarMensagemAviso(Component componente, String mensagem) {
        JOptionPane.showMessageDialog(
            componente,
            mensagem,
            "Aviso",
            JOptionPane.WARNING_MESSAGE
        );
    }
    
    /**
     * Valida se um campo de texto está vazio ou contém apenas espaços.
     * 
     * @param campo Campo de texto a ser validado
     * @return true se o campo estiver vazio, false caso contrário
     */
    public static boolean campoVazio(JTextField campo) {
        return campo == null || campo.getText() == null || campo.getText().trim().isEmpty();
    }
    
    /**
     * Valida se uma string está vazia ou contém apenas espaços.
     * 
     * @param texto String a ser validada
     * @return true se a string estiver vazia, false caso contrário
     */
    public static boolean textoVazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
    
    /**
     * Valida se um JComboBox tem um item selecionado.
     * 
     * @param comboBox ComboBox a ser validado
     * @return true se nenhum item estiver selecionado, false caso contrário
     */
    public static boolean comboBoxVazio(JComboBox<?> comboBox) {
        return comboBox == null || comboBox.getSelectedItem() == null;
    }
    
    /**
     * Valida múltiplos campos de texto de uma vez.
     * 
     * @param campos Array de campos a serem validados
     * @return true se algum campo estiver vazio, false se todos estiverem preenchidos
     */
    public static boolean algumCampoVazio(JTextField... campos) {
        for (JTextField campo : campos) {
            if (campoVazio(campo)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Limpa o conteúdo de múltiplos campos de texto.
     * 
     * @param campos Array de campos a serem limpos
     */
    public static void limparCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            if (campo != null) {
                campo.setText("");
            }
        }
    }
    
    /**
     * Exibe uma mensagem de confirmação ao usuário.
     * 
     * @param componente Componente pai para centralizar o diálogo
     * @param mensagem Mensagem de confirmação
     * @return true se o usuário confirmar (Sim), false caso contrário
     */
    public static boolean confirmar(Component componente, String mensagem) {
        int opcao = JOptionPane.showConfirmDialog(
            componente,
            mensagem,
            "Confirmação",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        return opcao == JOptionPane.YES_OPTION;
    }
    
    /**
     * Exibe uma mensagem de confirmação ao usuário com título customizado.
     * 
     * @param componente Componente pai para centralizar o diálogo
     * @param mensagem Mensagem de confirmação
     * @param titulo Título da janela de diálogo
     * @return true se o usuário confirmar (Sim), false caso contrário
     */
    public static boolean confirmar(Component componente, String mensagem, String titulo) {
        int opcao = JOptionPane.showConfirmDialog(
            componente,
            mensagem,
            titulo,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        return opcao == JOptionPane.YES_OPTION;
    }
    
    /**
     * Tenta converter uma string para BigDecimal.
     * 
     * @param texto String a ser convertida
     * @return BigDecimal ou null se a conversão falhar
     */
    public static BigDecimal converterParaBigDecimal(String texto) {
        if (textoVazio(texto)) {
            return null;
        }
        try {
            // Remove caracteres não numéricos exceto vírgula e ponto
            String textoLimpo = texto.replaceAll("[^0-9,.]", "");
            // Substitui vírgula por ponto para conversão
            textoLimpo = textoLimpo.replace(",", ".");
            return new BigDecimal(textoLimpo);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * Valida se uma string representa um número válido.
     * 
     * @param texto String a ser validada
     * @return true se for um número válido, false caso contrário
     */
    public static boolean isNumeroValido(String texto) {
        return converterParaBigDecimal(texto) != null;
    }
    
    /**
     * Centraliza uma janela na tela.
     * 
     * @param janela Janela a ser centralizada
     */
    public static void centralizarJanela(Window janela) {
        janela.setLocationRelativeTo(null);
    }
    
    /**
     * Define o look and feel do sistema para a aplicação.
     */
    public static void configurarLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Erro ao configurar look and feel: " + e.getMessage());
        }
    }
}
