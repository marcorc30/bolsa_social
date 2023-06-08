package org.lasalle.sigas.dto;

public class UnidadeTotal {
	
	private String nome;
	
	private Integer total;


	public UnidadeTotal() {
		
	}
	
	
	public UnidadeTotal(String nome, Integer total) {
		this.nome = nome;
		this.total = total;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
	
	

}
