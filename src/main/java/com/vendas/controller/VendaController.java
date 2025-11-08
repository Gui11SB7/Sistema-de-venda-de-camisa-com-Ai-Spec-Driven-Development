package com.vendas.controller;

import com.vendas.dao.ProdutoDAO;
import com.vendas.dao.VendaDAO;
import com.vendas.model.Produto;
import com.vendas.model.Venda;

import java.sql.SQLException;
import java.util.List;

/**
 * Controller para gerenciar a lógica de negócio relacionada a vendas.
 */
public class VendaController {
    
    private final VendaDAO vendaDAO;
    private final ProdutoDAO produtoDAO;
    
    /**
     * Construtor padrão que inicializa os DAOs.
     */
    public VendaController() {
        this.vendaDAO = new VendaDAO();
        this.produtoDAO = new ProdutoDAO();
    }
    
    /**
     * Registra uma nova venda com validações de campos obrigatórios.
     * Marca o produto como vendido após o registro da venda.
     * 
     * @param venda Venda a ser registrada
     * @return true se o registro foi bem-sucedido, false caso contrário
     * @throws IllegalArgumentException se algum campo obrigatório estiver inválido
     */
    public boolean registrarVenda(Venda venda) {
        try {
            // Validações de campos obrigatórios
            if (venda.getProdutoId() == null || venda.getProdutoId() <= 0) {
                throw new IllegalArgumentException("Selecione um produto válido");
            }
            
            if (venda.getClienteId() == null || venda.getClienteId() <= 0) {
                throw new IllegalArgumentException("Selecione um cliente válido");
            }
            
            if (venda.getDataVenda() == null) {
                throw new IllegalArgumentException("A data da venda é obrigatória");
            }
            
            if (venda.getValorVenda() == null || venda.getValorVenda().signum() <= 0) {
                throw new IllegalArgumentException("O valor da venda deve ser maior que zero");
            }
            
            // Verifica se o produto existe e está disponível
            Produto produto = produtoDAO.buscarPorId(venda.getProdutoId());
            if (produto == null) {
                throw new IllegalArgumentException("Produto não encontrado");
            }
            
            if (produto.isVendido()) {
                throw new IllegalArgumentException("Este produto já foi vendido");
            }
            
            // Registra a venda
            vendaDAO.inserir(venda);
            
            // Marca o produto como vendido
            produtoDAO.marcarComoVendido(venda.getProdutoId());
            
            return true;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar venda no banco de dados: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtém o histórico de vendas de um cliente específico.
     * 
     * @param clienteId ID do cliente
     * @return Lista de vendas do cliente
     * @throws IllegalArgumentException se o ID do cliente for inválido
     */
    public List<Venda> obterVendasPorCliente(Long clienteId) {
        try {
            if (clienteId == null || clienteId <= 0) {
                throw new IllegalArgumentException("ID do cliente inválido");
            }
            
            return vendaDAO.buscarPorCliente(clienteId);
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar vendas do cliente: " + e.getMessage(), e);
        }
    }
}
