package org.lasalle.sigas.model;

public enum StatusSolicitacao {

	EM_PREENCHIMENTO("Em Preenchimento"),
	PREENCHIDO("Preenchido"),
	PENDENCIA_DOCUMENTOS("Pendencia Documentos"),
	DOC_ENVIADO("Documento enviado"),
	ANALISADO("Analisado"),
	FINALIZADO("Finalizado"),
	CANCELADO("Cancelado");
	
	public String descricao;

	private StatusSolicitacao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	

}
