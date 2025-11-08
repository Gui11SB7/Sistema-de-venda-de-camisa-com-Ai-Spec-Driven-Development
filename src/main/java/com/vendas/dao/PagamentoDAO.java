package com.vendas.dao;

import com.vendas.model.Pagamento;
import com.vendas.util.DatabaseManager;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operações de banco de dados relacionadas a pagamentos.
 */
public class PagamentoDAO {
    
    /**
     * Insere um novo pagamento no banco de dados.
     * 
     * @param pagamento Pagamento a ser inserido
     * @throws SQLException se houver erro na inserção
     */
    public void inserir(Pagamento pagamento) throws SQLException {
        String sql = "INSERT INTO pagamentos (cliente_id, data_pagamento, valor_pago, observacao) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setLong(1, pagamento.getClienteId());
            pstmt.setString(2, pagamento.getDataPagamento().toString());
            pstmt.setBigDecimal(3, pagamento.getValorPago());
            pstmt.setString(4, pagamento.getObservacao());
            
            pstmt.executeUpdate();
            
            // Recupera o ID gerado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pagamento.setId(rs.getLong(1));
                }
            }
        }
    }
    
    /**
     * Busca pagamentos de um cliente específico.
     * 
     * @param clienteId ID do cliente
     * @return Lista de pagamentos do cliente
     * @throws SQLException se houver erro na consulta
     */
    public List<Pagamento> buscarPorCliente(Long clienteId) throws SQLException {
        String sql = "SELECT id, cliente_id, data_pagamento, valor_pago, observacao " +
                     "FROM pagamentos WHERE cliente_id = ?";
        
        List<Pagamento> pagamentos = new ArrayList<>();
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, clienteId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    pagamentos.add(mapResultSetToPagamento(rs));
                }
            }
        }
        
        return pagamentos;
    }
    
    /**
     * Calcula o valor total pago por um cliente específico.
     * 
     * @param clienteId ID do cliente
     * @return Valor total pago pelo cliente
     * @throws SQLException se houver erro na consulta
     */
    public BigDecimal calcularTotalPagoPorCliente(Long clienteId) throws SQLException {
        String sql = "SELECT SUM(valor_pago) as total FROM pagamentos WHERE cliente_id = ?";
        
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
     * Mapeia um ResultSet para um objeto Pagamento.
     * 
     * @param rs ResultSet contendo dados do pagamento
     * @return Objeto Pagamento
     * @throws SQLException se houver erro ao ler dados
     */
    private Pagamento mapResultSetToPagamento(ResultSet rs) throws SQLException {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(rs.getLong("id"));
        pagamento.setClienteId(rs.getLong("cliente_id"));
        pagamento.setDataPagamento(LocalDate.parse(rs.getString("data_pagamento")));
        pagamento.setValorPago(rs.getBigDecimal("valor_pago"));
        pagamento.setObservacao(rs.getString("observacao"));
        return pagamento;
    }
}
