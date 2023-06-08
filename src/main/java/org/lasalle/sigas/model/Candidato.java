package org.lasalle.sigas.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="candidato")
public class Candidato implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@NotBlank(message = "UF da naturalidade é obrigatório")
	@Column(name = "uf_naturalidade")
	private String ufNaturalidade;
	
	@NotBlank(message = "Naturalidade é obrigatório")
	private String naturalidade;
	
	@NotNull(message = "Nacionalidade é obrigatória")
	@ManyToOne
	@JoinColumn(name = "nacionalidade_id")
	private Nacionalidade nacionalidade;
	
	
	@NotNull(message = "Data de nascimento é obrigatória")
	@Column(name = "data_nascimento")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;

	@Email
	private String email;
	
	private String telefone;
	
	private String celular;
		
	@CPF
	@NotBlank(message = "CPF é obrigatório")
	private String cpf;
	
	private String rg;
	
	@Column(name = "rg_orgao")
	private String rgOrgao;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "rg_data_emissao")
	private LocalDate rgDataEmissao;
	
	@NotNull(message="Sexo é obrigatório")
	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	
	@Valid
	@Embedded
	private Endereco endereco;
	
	@Transient
	@ManyToOne
	@JoinColumn(name="turno_id")
	private Turno turno;
	
	@NotNull(message="Instituição de origem é obrigatório")
	@ManyToOne
	@JoinColumn(name = "tipo_instituicao_id")
	private TipoInstituicao tipoInstituicao;
		
	@NotNull(message="Tipo de transporte é obrigatório")
	@ManyToOne
	@JoinColumn(name = "tipo_transporte_id")
	private TipoTransporte tipoTransporte;
	
	@Column(name="mora_perto_escola")
	private Boolean moraPertoEscola;
	
	@Column(name="possui_irmaos_colegio")
	private Boolean possuiIrmaosColegio;
	
	@Column(name="possui_deficiencia")
	private Boolean possuiDeficiencia;
	
	@Column(name="possui_desconto_escola_origem")
	private Boolean possuiDescontoEscolaOrigem;
	
	@ManyToOne
	@JoinColumn(name = "solicitacao_id")
//	@Column(name = "solicitacao_id")
	private DadosIniciais dadosIniciais;
	
	@Column(name = "arquivo_laudo")
	private String arquivoLaudo;

	private String matricula;
	
	@ManyToOne
	@JoinColumn(name = "bolsa_anterior_resposta_id")
	private BolsaAnteriorReposta bolsaAnteriorResposta;
	
	@Column(name = "descricao_deficiencia")
	private String descricaoDeficiencia;
	
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

	public String getUfNaturalidade() {
		return ufNaturalidade;
	}

	public void setUfNaturalidade(String ufNaturalidade) {
		this.ufNaturalidade = ufNaturalidade;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public Nacionalidade getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(Nacionalidade nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public TipoInstituicao getTipoInstituicao() {
		return tipoInstituicao;
	}

	public void setTipoInstituicao(TipoInstituicao tipoInstituicao) {
		this.tipoInstituicao = tipoInstituicao;
	}

	
	public TipoTransporte getTipoTransporte() {
		return tipoTransporte;
	}

	public void setTipoTransporte(TipoTransporte tipoTransporte) {
		this.tipoTransporte = tipoTransporte;
	}

	public Boolean getMoraPertoEscola() {
		return moraPertoEscola;
	}

	public void setMoraPertoEscola(Boolean moraPertoEscola) {
		this.moraPertoEscola = moraPertoEscola;
	}

	public Boolean getPossuiIrmaosColegio() {
		return possuiIrmaosColegio;
	}

	public void setPossuiIrmaosColegio(Boolean possuiIrmaosColegio) {
		this.possuiIrmaosColegio = possuiIrmaosColegio;
	}

	public Boolean getPossuiDeficiencia() {
		return possuiDeficiencia;
	}

	public void setPossuiDeficiencia(Boolean possuiDeficiencia) {
		this.possuiDeficiencia = possuiDeficiencia;
	}

	public Boolean getPossuiDescontoEscolaOrigem() {
		return possuiDescontoEscolaOrigem;
	}

	public void setPossuiDescontoEscolaOrigem(Boolean possuiDescontoEscolaOrigem) {
		this.possuiDescontoEscolaOrigem = possuiDescontoEscolaOrigem;
	}
	

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public DadosIniciais getDadosIniciais() {
		return dadosIniciais;
	}

	public void setDadosIniciais(DadosIniciais dadosIniciais) {
		this.dadosIniciais = dadosIniciais;
	}

	public String getArquivoLaudo() {
		return arquivoLaudo;
	}

	public void setArquivoLaudo(String arquivoLaudo) {
		this.arquivoLaudo = arquivoLaudo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public BolsaAnteriorReposta getBolsaAnteriorResposta() {
		return bolsaAnteriorResposta;
	}

	public void setBolsaAnteriorResposta(BolsaAnteriorReposta bolsaAnteriorResposta) {
		this.bolsaAnteriorResposta = bolsaAnteriorResposta;
	}

	public String getDescricaoDeficiencia() {
		return descricaoDeficiencia;
	}

	public void setDescricaoDeficiencia(String descricaoDeficiencia) {
		this.descricaoDeficiencia = descricaoDeficiencia;
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
		Candidato other = (Candidato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
		
	

	

	

	
	
}
