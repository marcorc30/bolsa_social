package org.lasalle.sigas.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import org.lasalle.sigas.validation.AtributoConfirmacao;


@AtributoConfirmacao(atributo="senha", atributoConfirmacao="confirmacaoSenha", message = "Confirmação da senha não confere.")
@Entity
@Table(name = "usuario")
@DynamicUpdate
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "CPF é obrigatório")
	@CPF(message = "CPF é inválido")
	private String cpf;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@NotBlank(message="Email é obrigatório")
	@Email(message = "Informe um email válido")
	private String email;
	
	
//	@Min(message = "Senha deve ter no mínimo 6 caracteres", value = 6)
	private String senha;
	
	@Transient
	private String confirmacaoSenha;
	
	private Boolean ativo;
	
	@Transient
	private Integer idUnidade;
	
	@Size(message = "Selecione pelo menos um grupo", min = 1)
	@ManyToMany
	@JoinTable(name="usuario_grupo", 
				joinColumns = @JoinColumn(name="id_usuario"), 
				inverseJoinColumns = @JoinColumn(name="id_grupo"))
	private List<Grupo> grupos;
	
//	@Fetch(FetchMode.JOIN)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="usuario_unidade",
				joinColumns = @JoinColumn(name="id_usuario"),
				inverseJoinColumns = @JoinColumn(name="id_unidade"))
	private Set<Unidade> unidades;
	
	@NotBlank(message = "Celular é obrigatório")
	private String celular;
	
//	@OneToMany(mappedBy = "usuario")
//	private List<Candidato> candidatos;
	
	private String hash;
	
	@Column(name = "data_criacao")
	private LocalDate dataCriacao;
	
	@Column(name = "data_alteracao")
	private LocalDate dataAlteracao;

	@PreUpdate
	private void preUpdate() {
		this.confirmacaoSenha = senha;
		this.hash = UUID.randomUUID().toString();
//		this.dataAlteracao = LocalDate.now();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	
	
	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
	
//	public List<Candidato> getCandidatos() {
//		return candidatos;
//	}
//
//	public void setCandidatos(List<Candidato> candidatos) {
//		this.candidatos = candidatos;
//	}

	public boolean isNovo() {
		return id == null;
	}
	

	public Set<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(Set<Unidade> unidades) {
		this.unidades = unidades;
	}

	public Integer getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}
	
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
	

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDate getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(LocalDate dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	@PrePersist
	public void gerarHashUsuario() {
		this.hash = UUID.randomUUID().toString();
//		this.dataCriacao = LocalDate.now();
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
