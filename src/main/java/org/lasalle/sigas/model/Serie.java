package org.lasalle.sigas.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Serie implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String descricao;

//	@JsonIgnore
	
	@JsonManagedReference
//	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "nivel_id")
	private Nivel nivel;
	
//	@OneToOne(mappedBy = "serie")
//	private DetalhesProcessoSeletivo detalhe;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "series")
	private List<Unidade> unidades;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}
	
	

//	public DetalhesProcessoSeletivo getDetalhe() {
//		return detalhe;
//	}
//
//	public void setDetalhe(DetalhesProcessoSeletivo detalhe) {
//		this.detalhe = detalhe;
//	}

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
		Serie other = (Serie) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
