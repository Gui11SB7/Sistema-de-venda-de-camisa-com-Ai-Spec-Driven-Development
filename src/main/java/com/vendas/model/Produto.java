package com.vendas.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidade que representa um produto (camisa) no estoque.
 */
public class Produto {
    
    private Long id;
    private String descricao;
    private String tamanho;
    private byte[] imagem;
    private BigDecimal valorCompra;
    private LocalDate dataCompra;
    private boolean vendido;
    
    public Produto() {
    }
    
    public Produto(Long id, String descricao, String tamanho, byte[] imagem, 
                   BigDecimal valorCompra, LocalDate dataCompra, boolean vendido) {
        this.id = id;
        this.descricao = descricao;
        this.tamanho = tamanho;
        this.imagem = imagem;
        this.valorCompra = valorCompra;
        this.dataCompra = dataCompra;
        this.vendido = vendido;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getTamanho() {
        return tamanho;
    }
    
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
    
    public byte[] getImagem() {
        return imagem;
    }
    
    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
    
    public BigDecimal getValorCompra() {
        return valorCompra;
    }
    
    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }
    
    public LocalDate getDataCompra() {
        return dataCompra;
    }
    
    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }
    
    public boolean isVendido() {
        return vendido;
    }
    
    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }
    
    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", tamanho='" + tamanho + '\'' +
                ", valorCompra=" + valorCompra +
                ", dataCompra=" + dataCompra +
                ", vendido=" + vendido +
                '}';
    }
}
