package com.vendas.controller;

import com.vendas.dao.PagamentoDAO;
import com.vendas.model.Pagamento;

import java.sql.SQLException;
import java.util.List;

/**
 * Controller para gerenciar a lógica de negócio relacionada a pagamentos.
 */
public class PagamentoController {
    
    private final PagamentoDAO pagamentoDAO;
    
    /**
     * Construtor padrão que inicializa o DAO.
     */
    public PagamentoController() {
        this.pagamentoDAO = new PagamentoDAO();
    }
    
    /**
     * Registra um novo pagamento com validações de campos obrigatórios.
     * 
     * @param pagamento Pagamento a ser registrado
     * @return true se o registro foi bem-sucedido, false caso contrário
     * @throws IllegalArgumentException se algum campo obrigatório estiver inválido
     */
    public boolean registrarPagamento(Pagamento pagamento) {
        try {
            // Validações de campos obrigatórios
            if (pagamento.getClienteId() == null || pagamento.getClienteId() <= 0) {
                throw new IllegalArgumentException("Selecione um cliente válido");
            }
            
            if (pagamento.getDataPagamento() == null) {
                throw new IllegalArgumentException("A data do pagamento é obrigatória");
            }
            
            if (pagamento.getValorPago() == null || pagamento.getValorPago().signum() <= 0) {
                throw new IllegalArgumentException("O valor do pagamento deve ser maior que zero");
            }
            
            // Insere o pagamento no banco de dados
            pagamentoDAO.inserir(pagamento);
            return true;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar pagamento no banco de dados: " + e.getMessage(), e);
        }
    }
    
    /**
     * Lista todos os pagamentos de um cliente específico.
     * 
     * @param clienteId ID do cliente
     * @return Lista de pagamentos do cliente
     * @throws IllegalArgumentException se o ID do cliente for inválido
     */
    public List<Pagamento> listarPagamentosPorCliente(Long clienteId) {
        try {
            if (clienteId == null || clienteId <= 0) {
                throw new IllegalArgumentException("ID do cliente inválido");
            }
            
            return pagamentoDAO.buscarPorCliente(clienteId);
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pagamentos do cliente: " + e.getMessage(), e);
        }
    }
}
