package com.vendas.controller;

import com.vendas.dao.ProdutoDAO;
import com.vendas.model.Produto;

import java.sql.SQLException;
import java.util.List;

/**
 * Controller para gerenciar a lógica de negócio relacionada a produtos.
 */
public class ProdutoController {
    
    private final ProdutoDAO produtoDAO;
    
    /**
     * Construtor padrão que inicializa o DAO.
     */
    public ProdutoController() {
        this.produtoDAO = new ProdutoDAO();
    }
    
    /**
     * Cadastra um novo produto com validações de campos obrigatórios.
     * 
     * @param produto Produto a ser cadastrado
     * @return true se o cadastro foi bem-sucedido, false caso contrário
     * @throws IllegalArgumentException se algum campo obrigatório estiver vazio
     */
    public boolean cadastrarProduto(Produto produto) {
        try {
            // Validações de campos obrigatórios
            if (produto.getDescricao() == null || produto.getDescricao().trim().isEmpty()) {
                throw new IllegalArgumentException("A descrição do produto é obrigatória");
            }
            
            if (produto.getTamanho() == null || produto.getTamanho().trim().isEmpty()) {
                throw new IllegalArgumentException("O tamanho do produto é obrigatório");
            }
            
            if (produto.getValorCompra() == null || produto.getValorCompra().signum() <= 0) {
                throw new IllegalArgumentException("O valor de compra deve ser maior que zero");
            }
            
            if (produto.getDataCompra() == null) {
                throw new IllegalArgumentException("A data de compra é obrigatória");
            }
            
            // Insere o produto no banco de dados
            produtoDAO.inserir(produto);
            return true;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto no banco de dados: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista todos os produtos disponíveis (não vendidos).
     * 
     * @return Lista de produtos disponíveis
     */
    public List<Produto> listarProdutosDisponiveis() {
        try {
            return produtoDAO.buscarDisponiveis();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produtos disponíveis: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtém um produto específico por ID.
     * 
     * @param id ID do produto
     * @return Produto encontrado
     * @throws IllegalArgumentException se o produto não for encontrado
     */
    public Produto obterProduto(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("ID do produto inválido");
            }
            
            Produto produto = produtoDAO.buscarPorId(id);
            
            if (produto == null) {
                throw new IllegalArgumentException("Produto não encontrado com ID: " + id);
            }
            
            return produto;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto: " + e.getMessage(), e);
        }
    }
}
