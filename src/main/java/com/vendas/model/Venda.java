package com.vendas.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidade que representa uma venda de produto para um cliente.
 */
public class Venda {
    
    private Long id;
    private Long produtoId;
    private Long clienteId;
    private LocalDate dataVenda;
    private BigDecimal valorVenda;
    
    public Venda() {
    }
    
    public Venda(Long id, Long produtoId, Long clienteId, LocalDate dataVenda, BigDecimal valorVenda) {
        this.id = id;
        this.produtoId = produtoId;
        this.clienteId = clienteId;
        this.dataVenda = dataVenda;
        this.valorVenda = valorVenda;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getProdutoId() {
        return produtoId;
    }
    
    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }
    
    public Long getClienteId() {
        return clienteId;
    }
    
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    
    public LocalDate getDataVenda() {
        return dataVenda;
    }
    
    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }
    
    public BigDecimal getValorVenda() {
        return valorVenda;
    }
    
    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }
    
    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", produtoId=" + produtoId +
                ", clienteId=" + clienteId +
                ", dataVenda=" + dataVenda +
                ", valorVenda=" + valorVenda +
                '}';
    }
}
