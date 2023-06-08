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

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "documento")
public class Documento implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="componente_familiar_id")
	@NotNull(message = "Componente Familiar é obrigatório")
	private ComposicaoFamiliar componenteFamiliar;
	
	@Column(name = "grupo_documento")
	@NotBlank(message = "Grupo de Documento é obrigatório")
	private String grupoDocumento;
	
	@NotBlank(message = "Descrição do documento é obrigatório")
	private String descricao;
	
	@Column(name = "nome_arquivo")
	@NotBlank(message = "Arquivo é obrigatório")
	private String nomeArquivo;
	
	@ManyToOne
	@JoinColumn(name = "solicitacao_id")
	private DadosIniciais dadosIniciais;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ComposicaoFamiliar getComponenteFamiliar() {
		return componenteFamiliar;
	}

	public void setComponenteFamiliar(ComposicaoFamiliar componenteFamiliar) {
		this.componenteFamiliar = componenteFamiliar;
	}

	public String getGrupoDocumento() {
		return grupoDocumento;
	}

	public void setGrupoDocumento(String grupoDocumento) {
		this.grupoDocumento = grupoDocumento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public DadosIniciais getDadosIniciais() {
		return dadosIniciais;
	}

	public void setDadosIniciais(DadosIniciais dadosIniciais) {
		this.dadosIniciais = dadosIniciais;
	}

	public boolean isNovo() {
		return id == null;
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
		Documento other = (Documento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
