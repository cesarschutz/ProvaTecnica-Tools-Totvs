package com.leitor.entidades;

public class Vendedor {
	private Long cpf;
	private String nome;
	private Double salario;

	public Long getCPF() {
		return cpf;
	}

	public void setCPF(Long cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

}
