package com.vendas.dao;

import com.vendas.model.Cliente;
import com.vendas.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operações de banco de dados relacionadas a clientes.
 */
public class ClienteDAO {
    
    /**
     * Insere um novo cliente no banco de dados.
     * 
     * @param cliente Cliente a ser inserido
     * @throws SQLException se houver erro na inserção
     */
    public void inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nome, telefone, email, endereco) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getTelefone());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, cliente.getEndereco());
            
            pstmt.executeUpdate();
            
            // Recupera o ID gerado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getLong(1));
                }
            }
        }
    }
    
    /**
     * Atualiza os dados de um cliente existente.
     * 
     * @param cliente Cliente com dados atualizados
     * @throws SQLException se houver erro na atualização
     */
    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nome = ?, telefone = ?, email = ?, endereco = ? WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getTelefone());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, cliente.getEndereco());
            pstmt.setLong(5, cliente.getId());
            
            pstmt.executeUpdate();
        }
    }
    
    /**
     * Busca todos os clientes cadastrados.
     * 
     * @return Lista de todos os clientes
     * @throws SQLException se houver erro na consulta
     */
    public List<Cliente> buscarTodos() throws SQLException {
        String sql = "SELECT id, nome, telefone, email, endereco FROM clientes";
        
        List<Cliente> clientes = new ArrayList<>();
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                clientes.add(mapResultSetToCliente(rs));
            }
        }
        
        return clientes;
    }
    
    /**
     * Busca um cliente por ID.
     * 
     * @param id ID do cliente
     * @return Cliente encontrado ou null se não existir
     * @throws SQLException se houver erro na consulta
     */
    public Cliente buscarPorId(Long id) throws SQLException {
        String sql = "SELECT id, nome, telefone, email, endereco FROM clientes WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCliente(rs);
                }
            }
        }
        
        return null;
    }
    
    /**
     * Exclui um cliente do banco de dados.
     * 
     * @param id ID do cliente a ser excluído
     * @throws SQLException se houver erro na exclusão
     */
    public void excluir(Long id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }
    
    /**
     * Mapeia um ResultSet para um objeto Cliente.
     * 
     * @param rs ResultSet contendo dados do cliente
     * @return Objeto Cliente
     * @throws SQLException se houver erro ao ler dados
     */
    private Cliente mapResultSetToCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("id"));
        cliente.setNome(rs.getString("nome"));
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setEmail(rs.getString("email"));
        cliente.setEndereco(rs.getString("endereco"));
        return cliente;
    }
}
