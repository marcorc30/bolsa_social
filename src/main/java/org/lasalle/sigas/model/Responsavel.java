package org.lasalle.sigas.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="responsavel")
public class Responsavel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CPF
	@NotBlank(message = "CPF é obrigatório")
	private String cpf;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@NotNull(message = "Data de nascimento é obrigatória")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;
	
		
	@NotBlank(message="Celular é obrigatório")
	private String celular;
	
	@NotBlank(message = "RG é obrigatório")
	private String rg;
	
	
	@NotBlank(message = "Órgão expedidor é obrigatório")
	@Column(name = "orgao_expedidor")
	private String rgOrgao;
	
	@NotNull(message = "Data de emissão do rg é obrigatório")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_emissao")
	private LocalDate rgDataEmissao;

	@NotNull(message = "Grau de parentesco é obrigatório")
	@ManyToOne
	@JoinColumn(name = "parentesco_id")
	private Parentesco parentesco;
	
	@NotNull(message = "Situacao é obrigatória")
	@ManyToOne
	@JoinColumn(name = "situacao_responsavel_id")
	private SituacaoReponsavel situacaoResponsavel;
	
	@ManyToOne
	@JoinColumn(name = "solicitacao_id")
	private DadosIniciais dadosIniciais;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getRgOrgao() {
		return rgOrgao;
	}

	public void setRgOrgao(String rgOrgao) {
		this.rgOrgao = rgOrgao;
	}

	public LocalDate getRgDataEmissao() {
		return rgDataEmissao;
	}

	public void setRgDataEmissao(LocalDate rgDataEmissao) {
		this.rgDataEmissao = rgDataEmissao;
	}

	public Parentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}

	public SituacaoReponsavel getSituacaoResponsavel() {
		return situacaoResponsavel;
	}

	public void setSituacaoResponsavel(SituacaoReponsavel situacaoResponsavel) {
		this.situacaoResponsavel = situacaoResponsavel;
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
		Responsavel other = (Responsavel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}
