package org.lasalle.sigas.repository.filter;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class RelatorioDeferidoIndeferidoFilter {
	
	@NotNull(message = "Favor informar o processo seletivo")
	private Integer idProcessoSeletivo;
	
	@NotEmpty(message = "Favor informar o tipo do processo")
	private String concessao;

	public Integer getIdProcessoSeletivo() {
		return idProcessoSeletivo;
	}

	public void setIdProcessoSeletivo(Integer idProcessoSeletivo) {
		this.idProcessoSeletivo = idProcessoSeletivo;
	}

	public String getConcessao() {
		return concessao;
	}

	public void setConcessao(String concessao) {
		this.concessao = concessao;
	}

	
	

}
