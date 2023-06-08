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
@Table(name = "declaracao_final")
public class DeclaracaoFinal implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Informar se declara imposto de renda é obrigatório")
	@ManyToOne
	@JoinColumn(name = "irresposta_id")
	private IrReposta irResposta;
	

	@NotNull(message = "Informar se é inscrito no cadÚnico é obrigatória")
	@Column(name = "inscrito_cad_unico")
	private Boolean inscritoCadUnico;
	
	@ManyToOne
	@JoinColumn(name = "solicitacao_id")
	private DadosIniciais dadosIniciais;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public IrReposta getIrResposta() {
		return irResposta;
	}

	public void setIrResposta(IrReposta irResposta) {
		this.irResposta = irResposta;
	}

	public Boolean getInscritoCadUnico() {
		return inscritoCadUnico;
	}

	public void setInscritoCadUnico(Boolean inscritoCadUnico) {
		this.inscritoCadUnico = inscritoCadUnico;
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
		DeclaracaoFinal other = (DeclaracaoFinal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
