package org.lasalle.sigas.dto;

import java.math.BigDecimal;

import org.lasalle.sigas.model.PercentualParecer;

public class AnaliseTecnicaFinanceiraDTO {
	
	private String id;
	
	private BigDecimal rendaBruta;
	
	private Integer numeroFamiliares;
	
	private BigDecimal rendaPercapita;
	
	private Integer percentualId;
	
	private String observacao;
	
	private Integer dadosIniciais;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getRendaBruta() {
		return rendaBruta;
	}

	public void setRendaBruta(BigDecimal rendaBruta) {
		this.rendaBruta = rendaBruta;
	}

	public Integer getNumeroFamiliares() {
		return numeroFamiliares;
	}

	public void setNumeroFamiliares(Integer numeroFamiliares) {
		this.numeroFamiliares = numeroFamiliares;
	}

	public BigDecimal getRendaPercapita() {
		return rendaPercapita;
	}

	public void setRendaPercapita(BigDecimal rendaPercapita) {
		this.rendaPercapita = rendaPercapita;
	}



	public Integer getPercentualId() {
		return percentualId;
	}

	public void setPercentualId(Integer percentualId) {
		this.percentualId = percentualId;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getDadosIniciais() {
		return dadosIniciais;
	}

	public void setDadosIniciais(Integer dadosIniciais) {
		this.dadosIniciais = dadosIniciais;
	}

	@Override
	public String toString() {
		return this.id + " " + this.observacao + " " + this.percentualId;
	}
	

}
