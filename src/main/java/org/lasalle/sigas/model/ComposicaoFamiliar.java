package org.lasalle.sigas.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "solicitacao_composicao_familiar")
public class ComposicaoFamiliar implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@NotNull(message = "Grau de parentesco é obrigatório")
	@ManyToOne
	@JoinColumn(name = "parentesco_id")
	private Parentesco parentesco;
	
	@NotNull(message = "Data de nascimento é obrigatória")
	@DateTimeFormat(pattern =  "dd/MM/yyyy")
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;
	
	private Integer idade;
	
	@NotNull(message = "Estado civil é obrigatório")
	@ManyToOne
	@JoinColumn(name = "estado_civil_id")
	private EstadoCivil estadoCivil;
	
	@NotNull(message = "Escolaridade é obrigatória")
	@ManyToOne
	@JoinColumn(name = "escolaridade_id")
	private Escolaridade escolaridade;
	
	@NotBlank(message = "Ocupação é obrigatória")
	private String ocupacao;
	
	@ManyToOne
	@JoinColumn(name = "solicitacao_id")
	private DadosIniciais dadosIniciais;
	
//	@NotNull(message = "Salário é obrigatório")
	private BigDecimal salario;
	
	private Boolean conferido;

	@Transient
	private String cpf;
	
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

	public Parentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Escolaridade getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(Escolaridade escolaridade) {
		this.escolaridade = escolaridade;
	}

	public String getOcupacao() {
		return ocupacao;
	}

	public void setOcupacao(String ocupacao) {
		this.ocupacao = ocupacao;
	}

	public DadosIniciais getDadosIniciais() {
		return dadosIniciais;
	}

	public void setDadosIniciais(DadosIniciais dadosIniciais) {
		this.dadosIniciais = dadosIniciais;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public boolean isNovo() {
		return id == null;
	}
			
	public Boolean getConferido() {
		return conferido;
	}

	public void setConferido(Boolean conferido) {
		this.conferido = conferido;
	}

	@PrePersist @PreUpdate
	public void gravarRendaEIdade() {
		if (salario == null) {
			salario = new BigDecimal(0);
		}
		
		this.idade = calcularIdade(this.dataNascimento);
//		LocalDate aniversario = dataNascimento;
//	    LocalDate dataAtual = LocalDate.now();
//	    Period periodo = Period.between(aniversario, dataAtual);
//	    idade = periodo.getYears();
	}

	public Integer calcularIdade(LocalDate aniversario) {
		aniversario = this.dataNascimento;
	    LocalDate dataAtual = LocalDate.now();
	    Period periodo = Period.between(aniversario, dataAtual);
	    return periodo.getYears();
		
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
		ComposicaoFamiliar other = (ComposicaoFamiliar) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
