package com.vendas.controller;

import com.vendas.dao.ClienteDAO;
import com.vendas.dao.PagamentoDAO;
import com.vendas.dao.VendaDAO;
import com.vendas.model.Cliente;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller para gerenciar a lógica de negócio relacionada a clientes.
 */
public class ClienteController {
    
    private final ClienteDAO clienteDAO;
    private final VendaDAO vendaDAO;
    private final PagamentoDAO pagamentoDAO;
    
    /**
     * Construtor padrão que inicializa os DAOs.
     */
    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
        this.vendaDAO = new VendaDAO();
        this.pagamentoDAO = new PagamentoDAO();
    }
    
    /**
     * Cadastra um novo cliente com validações de campos obrigatórios.
     * 
     * @param cliente Cliente a ser cadastrado
     * @return true se o cadastro foi bem-sucedido, false caso contrário
     * @throws IllegalArgumentException se algum campo obrigatório estiver inválido
     */
    public boolean cadastrarCliente(Cliente cliente) {
        try {
            // Validação de campo obrigatório
            if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("O nome do cliente é obrigatório");
            }
            
            // Insere o cliente no banco de dados
            clienteDAO.inserir(cliente);
            return true;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar cliente no banco de dados: " + e.getMessage(), e);
        }
    }
    
    /**
     * Atualiza os dados de um cliente existente.
     * 
     * @param cliente Cliente com dados atualizados
     * @return true se a atualização foi bem-sucedida, false caso contrário
     * @throws IllegalArgumentException se algum campo obrigatório estiver inválido
     */
    public boolean atualizarCliente(Cliente cliente) {
        try {
            // Validações
            if (cliente.getId() == null || cliente.getId() <= 0) {
                throw new IllegalArgumentException("ID do cliente inválido");
            }
            
            if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("O nome do cliente é obrigatório");
            }
            
            // Verifica se o cliente existe
            Cliente clienteExistente = clienteDAO.buscarPorId(cliente.getId());
            if (clienteExistente == null) {
                throw new IllegalArgumentException("Cliente não encontrado com ID: " + cliente.getId());
            }
            
            // Atualiza o cliente
            clienteDAO.atualizar(cliente);
            return true;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cliente no banco de dados: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista todos os clientes cadastrados.
     * 
     * @return Lista de todos os clientes
     */
    public List<Cliente> listarClientes() {
        try {
            return clienteDAO.buscarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar clientes: " + e.getMessage(), e);
        }
    }
    
    /**
     * Calcula o saldo devedor de um cliente (total de vendas menos pagamentos).
     * 
     * @param clienteId ID do cliente
     * @return Saldo devedor do cliente
     * @throws IllegalArgumentException se o ID do cliente for inválido
     */
    public BigDecimal calcularSaldoDevedor(Long clienteId) {
        try {
            if (clienteId == null || clienteId <= 0) {
                throw new IllegalArgumentException("ID do cliente inválido");
            }
            
            // Calcula total de vendas
            BigDecimal totalVendas = vendaDAO.calcularTotalVendasPorCliente(clienteId);
            
            // Calcula total de pagamentos
            BigDecimal totalPagamentos = pagamentoDAO.calcularTotalPagoPorCliente(clienteId);
            
            // Retorna o saldo devedor (vendas - pagamentos)
            return totalVendas.subtract(totalPagamentos);
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular saldo devedor: " + e.getMessage(), e);
        }
    }
}
