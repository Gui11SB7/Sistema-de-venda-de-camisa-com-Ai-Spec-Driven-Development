package com.vendas.dao;

import com.vendas.model.Venda;
import com.vendas.util.DatabaseManager;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operações de banco de dados relacionadas a vendas.
 */
public class VendaDAO {
    
    /**
     * Insere uma nova venda no banco de dados.
     * 
     * @param venda Venda a ser inserida
     * @throws SQLException se houver erro na inserção
     */
    public void inserir(Venda venda) throws SQLException {
        String sql = "INSERT INTO vendas (produto_id, cliente_id, data_venda, valor_venda) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setLong(1, venda.getProdutoId());
            pstmt.setLong(2, venda.getClienteId());
            pstmt.setString(3, venda.getDataVenda().toString());
            pstmt.setBigDecimal(4, venda.getValorVenda());
            
            pstmt.executeUpdate();
            
            // Recupera o ID gerado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    venda.setId(rs.getLong(1));
                }
            }
        }
    }
    
    /**
     * Busca todas as vendas cadastradas.
     * 
     * @return Lista de todas as vendas
     * @throws SQLException se houver erro na consulta
     */
    public List<Venda> buscarTodas() throws SQLException {
        String sql = "SELECT id, produto_id, cliente_id, data_venda, valor_venda FROM vendas";
        
        List<Venda> vendas = new ArrayList<>();
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                vendas.add(mapResultSetToVenda(rs));
            }
        }
        
        return vendas;
    }
    
    /**
     * Busca vendas de um cliente específico.
     * 
     * @param clienteId ID do cliente
     * @return Lista de vendas do cliente
     * @throws SQLException se houver erro na consulta
     */
    public List<Venda> buscarPorCliente(Long clienteId) throws SQLException {
        String sql = "SELECT id, produto_id, cliente_id, data_venda, valor_venda " +
                     "FROM vendas WHERE cliente_id = ?";
        
        List<Venda> vendas = new ArrayList<>();
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, clienteId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    vendas.add(mapResultSetToVenda(rs));
                }
            }
        }
        
        return vendas;
    }
    
    /**
     * Calcula o valor total de todas as vendas.
     * 
     * @return Valor total das vendas
     * @throws SQLException se houver erro na consulta
     */
    public BigDecimal calcularTotalVendas() throws SQLException {
        String sql = "SELECT SUM(valor_venda) as total FROM vendas";
        
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
     * Calcula o valor total de vendas para um cliente específico.
     * 
     * @param clienteId ID do cliente
     * @return Valor total das vendas do cliente
     * @throws SQLException se houver erro na consulta
     */
    public BigDecimal calcularTotalVendasPorCliente(Long clienteId) throws SQLException {
        String sql = "SELECT SUM(valor_venda) as total FROM vendas WHERE cliente_id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, clienteId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal total = rs.getBigDecimal("total");
                    return total != null ? total : BigDecimal.ZERO;
                }
            }
        }
        
        return BigDecimal.ZERO;
    }
    
    /**
     * Mapeia um ResultSet para um objeto Venda.
     * 
     * @param rs ResultSet contendo dados da venda
     * @return Objeto Venda
     * @throws SQLException se houver erro ao ler dados
     */
    private Venda mapResultSetToVenda(ResultSet rs) throws SQLException {
        Venda venda = new Venda();
        venda.setId(rs.getLong("id"));
        venda.setProdutoId(rs.getLong("produto_id"));
        venda.setClienteId(rs.getLong("cliente_id"));
        venda.setDataVenda(LocalDate.parse(rs.getString("data_venda")));
        venda.setValorVenda(rs.getBigDecimal("valor_venda"));
        return venda;
    }
}
