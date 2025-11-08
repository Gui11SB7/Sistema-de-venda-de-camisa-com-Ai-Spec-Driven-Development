package com.vendas.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gerenciador de conexões e inicialização do banco de dados SQLite.
 */
public class DatabaseManager {
    
    private static final String DB_URL = "jdbc:sqlite:vendas_camisas.db";
    private static Connection connection;
    
    /**
     * Retorna uma conexão com o banco de dados SQLite.
     * 
     * @return Connection objeto de conexão com o banco
     * @throws SQLException se houver erro ao conectar
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }
    
    /**
     * Inicializa o banco de dados criando o arquivo .db se não existir
     * e criando as tabelas necessárias.
     * 
     * @throws SQLException se houver erro na inicialização
     */
    public static void inicializarBanco() throws SQLException {
        getConnection();
        criarTabelas();
        criarIndices();
    }
    
    /**
     * Cria as tabelas do banco de dados se não existirem.
     * 
     * @throws SQLException se houver erro ao criar tabelas
     */
    public static void criarTabelas() throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        
        // Tabela de produtos
        String sqlProdutos = "CREATE TABLE IF NOT EXISTS produtos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "descricao TEXT NOT NULL, " +
                "tamanho TEXT NOT NULL, " +
                "imagem BLOB, " +
                "valor_compra DECIMAL(10,2) NOT NULL, " +
                "data_compra DATE NOT NULL, " +
                "vendido BOOLEAN DEFAULT 0" +
                ")";
        stmt.execute(sqlProdutos);
        
        // Tabela de clientes
        String sqlClientes = "CREATE TABLE IF NOT EXISTS clientes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "telefone TEXT, " +
                "email TEXT, " +
                "endereco TEXT" +
                ")";
        stmt.execute(sqlClientes);
        
        // Tabela de vendas
        String sqlVendas = "CREATE TABLE IF NOT EXISTS vendas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "produto_id INTEGER NOT NULL, " +
                "cliente_id INTEGER NOT NULL, " +
                "data_venda DATE NOT NULL, " +
                "valor_venda DECIMAL(10,2) NOT NULL, " +
                "FOREIGN KEY (produto_id) REFERENCES produtos(id), " +
                "FOREIGN KEY (cliente_id) REFERENCES clientes(id)" +
                ")";
        stmt.execute(sqlVendas);
        
        // Tabela de pagamentos
        String sqlPagamentos = "CREATE TABLE IF NOT EXISTS pagamentos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cliente_id INTEGER NOT NULL, " +
                "data_pagamento DATE NOT NULL, " +
                "valor_pago DECIMAL(10,2) NOT NULL, " +
                "observacao TEXT, " +
                "FOREIGN KEY (cliente_id) REFERENCES clientes(id)" +
                ")";
        stmt.execute(sqlPagamentos);
        
        stmt.close();
    }
    
    /**
     * Cria índices para melhorar a performance das consultas.
     * 
     * @throws SQLException se houver erro ao criar índices
     */
    public static void criarIndices() throws SQLException {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        
        // Índice para produtos vendidos
        String idxProdutosVendido = "CREATE INDEX IF NOT EXISTS idx_produtos_vendido " +
                "ON produtos(vendido)";
        stmt.execute(idxProdutosVendido);
        
        // Índice para vendas por cliente
        String idxVendasCliente = "CREATE INDEX IF NOT EXISTS idx_vendas_cliente " +
                "ON vendas(cliente_id)";
        stmt.execute(idxVendasCliente);
        
        // Índice para pagamentos por cliente
        String idxPagamentosCliente = "CREATE INDEX IF NOT EXISTS idx_pagamentos_cliente " +
                "ON pagamentos(cliente_id)";
        stmt.execute(idxPagamentosCliente);
        
        stmt.close();
    }
    
    /**
     * Fecha a conexão com o banco de dados.
     */
    public static void fecharConexao() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexão com banco de dados fechada com sucesso");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão com banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Verifica se a conexão está ativa.
     * 
     * @return true se a conexão está ativa, false caso contrário
     */
    public static boolean isConexaoAtiva() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            System.err.println("Erro ao verificar status da conexão: " + e.getMessage());
            return false;
        }
    }
}
