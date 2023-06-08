package org.lasalle.sigas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "situacao_habitacional")
public class SituacaoHabitacional implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Area da moradia é obrigatória")
	@ManyToOne
	@JoinColumn(name = "moradia_area_id")
	private MoradiaArea moradiaArea;
	
	@NotNull(message = "Imóvel da moradia é obrigatória")
	@ManyToOne
	@JoinColumn(name = "moradia_imovel_id")
	private MoradiaImovel moradiaImovel;
	
	@NotNull(message = "Condição da moradia é obrigatória")
	@ManyToOne
	@JoinColumn(name = "moradia_condicao_id")
	private MoradiaCondicao moradiaCondicao;
	
	@NotNull(message = "Tipo da moradia é obrigatória")
	@ManyToOne
	@JoinColumn(name = "moradia_tipo_id")
	private MoradiaTipo moradiaTipo;
	
	@NotNull(message = "Tem outros moradores na moradia é obrigatória")
	@ManyToOne
	@JoinColumn(name = "moradia_outros_moradores_id")
	private MoradiaResposta moradiaOutrosMoradores;
	
	@ManyToOne
	@JoinColumn(name = "moraria_tem_vinculo_familiar_id")
	private MoradiaResposta moradiaVinculoFamiliar;

	@NotNull(message = "Quantidade de cômodos é obrigatória")
	@Column(name = "quantidade_comodos")
	private Integer quantidadeComodos;
	
	@ManyToOne
	@JoinColumn(name = "solicitacao_id")
	private DadosIniciais dadosIniciais;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MoradiaArea getMoradiaArea() {
		return moradiaArea;
	}

	public void setMoradiaArea(MoradiaArea moradiaArea) {
		this.moradiaArea = moradiaArea;
	}

	public MoradiaImovel getMoradiaImovel() {
		return moradiaImovel;
	}

	public void setMoradiaImovel(MoradiaImovel moradiaImovel) {
		this.moradiaImovel = moradiaImovel;
	}

	public MoradiaCondicao getMoradiaCondicao() {
		return moradiaCondicao;
	}

	public void setMoradiaCondicao(MoradiaCondicao moradiaCondicao) {
		this.moradiaCondicao = moradiaCondicao;
	}

	public MoradiaTipo getMoradiaTipo() {
		return moradiaTipo;
	}

	public void setMoradiaTipo(MoradiaTipo moradiaTipo) {
		this.moradiaTipo = moradiaTipo;
	}

	public MoradiaResposta getMoradiaOutrosMoradores() {
		return moradiaOutrosMoradores;
	}

	public void setMoradiaOutrosMoradores(MoradiaResposta moradiaOutrosMoradores) {
		this.moradiaOutrosMoradores = moradiaOutrosMoradores;
	}

	public MoradiaResposta getMoradiaVinculoFamiliar() {
		return moradiaVinculoFamiliar;
	}

	public void setMoradiaVinculoFamiliar(MoradiaResposta moradiaVinculoFamiliar) {
		this.moradiaVinculoFamiliar = moradiaVinculoFamiliar;
	}

	public Integer getQuantidadeComodos() {
		return quantidadeComodos;
	}

	public void setQuantidadeComodos(Integer quantidadeComodos) {
		this.quantidadeComodos = quantidadeComodos;
	}

	public DadosIniciais getDadosIniciais() {
		return dadosIniciais;
	}

	public void setDadosIniciais(DadosIniciais dadosIniciais) {
		this.dadosIniciais = dadosIniciais;
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
		SituacaoHabitacional other = (SituacaoHabitacional) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
