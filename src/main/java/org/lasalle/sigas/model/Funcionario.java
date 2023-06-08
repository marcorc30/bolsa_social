package org.lasalle.sigas.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Funcionario implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
//	@ManyToOne
//	@JoinColumn(name = "id_funcao")
//	private Funcao funcao;
//	
	private Integer ordem;
	
	private Boolean visivel;
	
	@Column(name = "assistente")
	private Boolean ehAssistente;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "funcionarios")
	private List<Unidade> unidades;

	@Column(name = "funcao_la_salle")
	private String funcaoLaSalle;
	
	private String cpf;
	
	private String celular;
	
	@Column(name = "cargo_funcao")
	private String cargoFuncao;
	
	@Column(name = "caminho_assinatura")
	private String caminhoAssinatura;
	
	@Column(name = "cress")
	private String cress;
	
	@Column(name = "responsavel_visto_direcao")
	private Boolean responsavelVistoDirecao;
	
	@Column(name = "usuario_id")
	private Integer usuarioId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

//	public Funcao getFuncao() {
//		return funcao;
//	}
//
//	public void setFuncao(Funcao funcao) {
//		this.funcao = funcao;
//	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public Boolean getVisivel() {
		return visivel;
	}

	public void setVisivel(Boolean visivel) {
		this.visivel = visivel;
	}


	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

	public Boolean getEhAssistente() {
		return ehAssistente;
	}

	public void setEhAssistente(Boolean ehAssistente) {
		this.ehAssistente = ehAssistente;
	}

	public String getFuncaoLaSalle() {
		return funcaoLaSalle;
	}

	public void setFuncaoLaSalle(String funcaoLaSalle) {
		this.funcaoLaSalle = funcaoLaSalle;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCargoFuncao() {
		return cargoFuncao;
	}

	public void setCargoFuncao(String cargoFuncao) {
		this.cargoFuncao = cargoFuncao;
	}

	public String getCaminhoAssinatura() {
		return caminhoAssinatura;
	}

	public void setCaminhoAssinatura(String caminhoAssinatura) {
		this.caminhoAssinatura = caminhoAssinatura;
	}

	public String getCress() {
		return cress;
	}

	public void setCress(String cress) {
		this.cress = cress;
	}

	public Boolean getResponsavelVistoDirecao() {
		return responsavelVistoDirecao;
	}

	public void setResponsavelVistoDirecao(Boolean responsavelVistoDirecao) {
		this.responsavelVistoDirecao = responsavelVistoDirecao;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
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
		Funcionario other = (Funcionario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
