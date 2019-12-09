package com.leitor.entidades;

public class Cliente {
	private Long cnpj;
	private String nome;
	private String ramoDeAtividade;

	public Long getCNPJ() {
		return cnpj;
	}

	public void setCNPJ(Long cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRamoDeAtividade() {
		return ramoDeAtividade;
	}

	public void setRamoDeAtividade(String ramoDeAtividade) {
		this.ramoDeAtividade = ramoDeAtividade;
	}
}
