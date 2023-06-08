package org.lasalle.sigas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "analise_tecnica_parecer")
public class AnaliseTecnicaParecer {
	
	@Id
	private Long id;
	
	
	private String observacao;
	
	@Column(name = "solicitacao_id")
	private Integer dadosIniciais;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		AnaliseTecnicaParecer other = (AnaliseTecnicaParecer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
