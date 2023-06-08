package org.lasalle.sigas.dto;

public class SolicitacoesConsultaDTOCIBS {
	
	public SolicitacoesConsultaDTOCIBS(Long id, String protocolo, Integer qtdIrmaos, Boolean moraPerto, Integer ic, String status){
		this.id = id;
		this.protocolo = protocolo;
		this.qtdIrmaos = qtdIrmaos;
		this.moraPerto = moraPerto;
		this.ic = ic;
		this.status = status;
		
	}

	private Long id;
	
	private String protocolo;
	
	private Integer qtdIrmaos;
	
	private Boolean moraPerto;
	
	private Integer ic;
	
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public Integer getQtdIrmaos() {
		return qtdIrmaos;
	}

	public void setQtdIrmaos(Integer qtdIrmaos) {
		this.qtdIrmaos = qtdIrmaos;
	}

	public Boolean getMoraPerto() {
		return moraPerto;
	}

	public void setMoraPerto(Boolean moraPerto) {
		this.moraPerto = moraPerto;
	}

	public Integer getIc() {
		return ic;
	}

	public void setIc(Integer ic) {
		this.ic = ic;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	
	

}
