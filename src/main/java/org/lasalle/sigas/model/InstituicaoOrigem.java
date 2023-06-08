package org.lasalle.sigas.model;

public enum InstituicaoOrigem {

	ESCOLA_PUPLICA("Escola Pública"),
	ESCOLA_PARTICULAR("Escola Particular");
	
	private String descricao;
	
	private InstituicaoOrigem(String descricao) {
		this.descricao = descricao;
	} 
	
	public String getDescricao() {
		return this.descricao;
	}
}
