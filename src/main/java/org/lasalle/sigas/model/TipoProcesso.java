package org.lasalle.sigas.model;

public enum TipoProcesso {
	
	NOVO_PROCESSO("Novo Processo"),
	RENOVACAO("Renovacao");
	
	private String descricao;
	
	private TipoProcesso(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

}
