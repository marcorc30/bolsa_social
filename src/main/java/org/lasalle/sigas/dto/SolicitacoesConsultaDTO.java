package org.lasalle.sigas.dto;

import java.math.BigDecimal;

import org.lasalle.sigas.model.StatusSolicitacao;

public class SolicitacoesConsultaDTO {
	
	public SolicitacoesConsultaDTO(Long id, String protocolo, String nome,  String status, BigDecimal rendaBruta,
			BigDecimal despesa, Integer totalComponentes, BigDecimal rendaPerCapta, Integer qtdIrmaos, Boolean cadunico, Boolean moraperto, Integer ic ) {
		this.id = id;
		this.protocolo = protocolo;
		this.nome = nome;
//		this.curso = curso;
		this.status = status;
		this.rendaBruta = rendaBruta;
		this.despesa = despesa;
		this.totalComponentes = totalComponentes;
		this.rendaPerCapta = rendaPerCapta;
		
		this.qtdIrmaos = qtdIrmaos;
		this.cadunico = cadunico;
		this.moraperto = moraperto;
		this.ic = ic;
		
	}

	private Long id;
	
	private String protocolo;
	
	private String nome;
	
//	private String curso;
		
	private BigDecimal rendaBruta;
	
	private BigDecimal despesa;
	
	private Integer totalComponentes;
	
	private BigDecimal rendaPerCapta;
	
	private String status;
	
	private Integer qtdIrmaos;
	
	private Boolean cadunico;
	
	private Boolean moraperto;
	
	private Integer ic;

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

//	public String getCurso() {
//		return curso;
//	}
//
//	public void setCurso(String curso) {
//		this.curso = curso;
//	}

	public BigDecimal getRendaBruta() {
		return rendaBruta;
	}

	public void setRendaBruta(BigDecimal rendaBruta) {
		this.rendaBruta = rendaBruta;
	}

	public BigDecimal getDespesa() {
		return despesa;
	}

	public void setDespesa(BigDecimal despesa) {
		this.despesa = despesa;
	}

	public Integer getTotalComponentes() {
		return totalComponentes;
	}

	public void setTotalComponentes(Integer totalComponentes) {
		this.totalComponentes = totalComponentes;
	}

	public BigDecimal getRendaPerCapta() {
		return rendaPerCapta;
	}

	public void setRendaPerCapta(BigDecimal rendaPerCapta) {
		this.rendaPerCapta = rendaPerCapta;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getQtdIrmaos() {
		return qtdIrmaos;
	}

	public void setQtdIrmaos(Integer qtdIrmaos) {
		this.qtdIrmaos = qtdIrmaos;
	}

	public Boolean getCadunico() {
		return cadunico;
	}

	public void setCadunico(Boolean cadunico) {
		this.cadunico = cadunico;
	}

	public Boolean getMoraperto() {
		return moraperto;
	}

	public void setMoraperto(Boolean moraperto) {
		this.moraperto = moraperto;
	}

	public Integer getIc() {
		return ic;
	}

	public void setIc(Integer ic) {
		this.ic = ic;
	}
	

}
