package com.vendas.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidade que representa um pagamento realizado por um cliente.
 */
public class Pagamento {
    
    private Long id;
    private Long clienteId;
    private LocalDate dataPagamento;
    private BigDecimal valorPago;
    private String observacao;
    
    public Pagamento() {
    }
    
    public Pagamento(Long id, Long clienteId, LocalDate dataPagamento, 
                     BigDecimal valorPago, String observacao) {
        this.id = id;
        this.clienteId = clienteId;
        this.dataPagamento = dataPagamento;
        this.valorPago = valorPago;
        this.observacao = observacao;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getClienteId() {
        return clienteId;
    }
    
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    
    public LocalDate getDataPagamento() {
        return dataPagamento;
    }
    
    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
    
    public BigDecimal getValorPago() {
        return valorPago;
    }
    
    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }
    
    public String getObservacao() {
        return observacao;
    }
    
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    @Override
    public String toString() {
        return "Pagamento{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", dataPagamento=" + dataPagamento +
                ", valorPago=" + valorPago +
                ", observacao='" + observacao + '\'' +
                '}';
    }
}
