package com.vendas.controller;

import com.vendas.dao.ProdutoDAO;
import com.vendas.dao.VendaDAO;
import com.vendas.model.Produto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller para gerenciar o painel financeiro e de estoque.
 */
public class GerenciamentoController {
    
    private final ProdutoDAO produtoDAO;
    private final VendaDAO vendaDAO;
    
    /**
     * Construtor padrão que inicializa os DAOs.
     */
    public GerenciamentoController() {
        this.produtoDAO = new ProdutoDAO();
        this.vendaDAO = new VendaDAO();
    }
    
    /**
     * Obtém a lista de produtos em estoque (não vendidos).
     * 
     * @return Lista de produtos disponíveis no estoque
     */
    public List<Produto> obterProdutosEmEstoque() {
        try {
            return produtoDAO.buscarDisponiveis();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produtos em estoque: " + e.getMessage(), e);
        }
    }
    
    /**
     * Calcula o valor total gasto na compra de todos os produtos.
     * 
     * @return Valor total gasto
     */
    public BigDecimal calcularTotalGasto() {
        try {
            return produtoDAO.calcularTotalGasto();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular total gasto: " + e.getMessage(), e);
        }
    }
    
    /**
     * Calcula o valor total recebido com todas as vendas.
     * 
     * @return Valor total recebido
     */
    public BigDecimal calcularTotalRecebido() {
        try {
            return vendaDAO.calcularTotalVendas();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular total recebido: " + e.getMessage(), e);
        }
    }
    
    /**
     * Calcula o lucro ou prejuízo (total recebido menos total gasto).
     * 
     * @return Lucro (positivo) ou prejuízo (negativo)
     */
    public BigDecimal calcularLucro() {
        BigDecimal totalRecebido = calcularTotalRecebido();
        BigDecimal totalGasto = calcularTotalGasto();
        return totalRecebido.subtract(totalGasto);
    }
}
