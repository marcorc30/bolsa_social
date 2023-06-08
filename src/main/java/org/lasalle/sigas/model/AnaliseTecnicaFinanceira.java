package org.lasalle.sigas.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name = "analise_tecnica_financeira")
public class AnaliseTecnicaFinanceira implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	
	@NotNull(message = "Renda bruta é obrigatória")
	@Column(name = "renda_bruta")
	 @JsonFormat(shape=JsonFormat.Shape.STRING)
	private BigDecimal rendaBruta;
	
	@NotNull(message = "Numero de Familiares é obrigatório")
	@Column(name = "numero_familiares")
	private Integer numeroFamiliares;
	
	@NotNull(message = "Renda percapita é obrigatório")
	 @JsonFormat(shape=JsonFormat.Shape.STRING)
	 @Column(name = "renda_percapita")
	private BigDecimal rendaPercapita;

	@JsonIgnore
	@NotNull(message = "Percentual é obrigatório")
	@OneToOne
	@JoinColumn(name = "percentual_id")
	private PercentualParecer percentual;
	
	
	@NotEmpty(message = "Observação é obrigatória")
	private String observacao;
	
	@Column(name = "solicitacao_id")
	private Integer dadosIniciais;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Integer getNumeroFamiliares() {
		return numeroFamiliares;
	}

	public void setNumeroFamiliares(Integer numeroFamiliares) {
		this.numeroFamiliares = numeroFamiliares;
	}

	
	

	public BigDecimal getRendaBruta() {
		return rendaBruta;
	}

	public void setRendaBruta(BigDecimal rendaBruta) {
		this.rendaBruta = rendaBruta;
	}

	public BigDecimal getRendaPercapita() {
		return rendaPercapita;
	}

	public void setRendaPercapita(BigDecimal rendaPercapita) {
		this.rendaPercapita = rendaPercapita;
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

	public PercentualParecer getPercentual() {
		return percentual;
	}

	public void setPercentual(PercentualParecer percentual) {
		this.percentual = percentual;
	}
	
	public Boolean isTemPercentual() {
		return percentual != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnaliseTecnicaFinanceira other = (AnaliseTecnicaFinanceira) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
	

}
