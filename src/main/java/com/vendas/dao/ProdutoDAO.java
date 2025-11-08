package com.vendas.dao;

import com.vendas.model.Produto;
import com.vendas.util.DatabaseManager;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operações de banco de dados relacionadas a produtos.
 */
public class ProdutoDAO {
    
    /**
     * Insere um novo produto no banco de dados.
     * 
     * @param produto Produto a ser inserido
     * @throws SQLException se houver erro na inserção
     */
    public void inserir(Produto produto) throws SQLException {
        String sql = "INSERT INTO produtos (descricao, tamanho, imagem, valor_compra, data_compra, vendido) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, produto.getDescricao());
            pstmt.setString(2, produto.getTamanho());
            pstmt.setBytes(3, produto.getImagem());
            pstmt.setBigDecimal(4, produto.getValorCompra());
            pstmt.setString(5, produto.getDataCompra().toString());
            pstmt.setBoolean(6, produto.isVendido());
            
            pstmt.executeUpdate();
            
            // Recupera o ID gerado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    produto.setId(rs.getLong(1));
                }
            }
        }
    }
    
    /**
     * Busca todos os produtos cadastrados.
     * 
     * @return Lista de todos os produtos
     * @throws SQLException se houver erro na consulta
     */
    public List<Produto> buscarTodos() throws SQLException {
        String sql = "SELECT id, descricao, tamanho, imagem, valor_compra, data_compra, vendido " +
                     "FROM produtos";
        
        List<Produto> produtos = new ArrayList<>();
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                produtos.add(mapResultSetToProduto(rs));
            }
        }
        
        return produtos;
    }
    
    /**
     * Busca produtos disponíveis (não vendidos).
     * 
     * @return Lista de produtos não vendidos
     * @throws SQLException se houver erro na consulta
     */
    public List<Produto> buscarDisponiveis() throws SQLException {
        String sql = "SELECT id, descricao, tamanho, imagem, valor_compra, data_compra, vendido " +
                     "FROM produtos WHERE vendido = 0";
        
        List<Produto> produtos = new ArrayList<>();
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                produtos.add(mapResultSetToProduto(rs));
            }
        }
        
        return produtos;
    }
    
    /**
     * Busca um produto por ID.
     * 
     * @param id ID do produto
     * @return Produto encontrado ou null se não existir
     * @throws SQLException se houver erro na consulta
     */
    public Produto buscarPorId(Long id) throws SQLException {
        String sql = "SELECT id, descricao, tamanho, imagem, valor_compra, data_compra, vendido " +
                     "FROM produtos WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduto(rs);
                }
            }
        }
        
        return null;
    }
    
    /**
     * Marca um produto como vendido.
     * 
     * @param id ID do produto a ser marcado como vendido
     * @throws SQLException se houver erro na atualização
     */
    public void marcarComoVendido(Long id) throws SQLException {
        String sql = "UPDATE produtos SET vendido = 1 WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }
    
    /**
     * Calcula o valor total gasto na compra de todos os produtos.
     * 
     * @return Valor total gasto
     * @throws SQLException se houver erro na consulta
     */
    public BigDecimal calcularTotalGasto() throws SQLException {
        String sql = "SELECT SUM(valor_compra) as total FROM produtos";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("total");
                return total != null ? total : BigDecimal.ZERO;
            }
        }
        
        return BigDecimal.ZERO;
    }
    
    /**
     * Mapeia um ResultSet para um objeto Produto.
     * 
     * @param rs ResultSet contendo dados do produto
     * @return Objeto Produto
     * @throws SQLException se houver erro ao ler dados
     */
    private Produto mapResultSetToProduto(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setId(rs.getLong("id"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setTamanho(rs.getString("tamanho"));
        produto.setImagem(rs.getBytes("imagem"));
        produto.setValorCompra(rs.getBigDecimal("valor_compra"));
        produto.setDataCompra(LocalDate.parse(rs.getString("data_compra")));
        produto.setVendido(rs.getBoolean("vendido"));
        return produto;
    }
}
