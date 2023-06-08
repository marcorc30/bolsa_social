package org.lasalle.sigas.model;

public enum Perfil {
	
	SAS("SAS"),
	CBIS("CBIS"),
	AUXILIAR("Auxilar");
	
	private String descricao;
	
	private Perfil(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
