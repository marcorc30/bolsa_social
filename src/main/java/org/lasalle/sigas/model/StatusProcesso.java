package org.lasalle.sigas.model;

public enum StatusProcesso {
	
	INICIADO("Iniciado"),
	NAO_INICIADO("Não Iniciado");
	
	private String descricao;
	
	private StatusProcesso(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

}
