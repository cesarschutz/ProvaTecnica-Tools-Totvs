package com.leitor.entidades;

public class Venda {
	private Long id;
	private Long idItem;
	private Double qtdItem;
	private Double precoItem;
	private Vendedor vendedor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public Double getQtdItem() {
		return qtdItem;
	}

	public void setQtdItem(Double qtdItem) {
		this.qtdItem = qtdItem;
	}

	public Double getPrecoItem() {
		return precoItem;
	}

	public void setPrecoItem(Double precoItem) {
		this.precoItem = precoItem;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}
	
	public Double getValorTotalVenda() {
		return qtdItem * precoItem;
	}
}
