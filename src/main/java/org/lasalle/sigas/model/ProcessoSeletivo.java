package org.lasalle.sigas.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "processo_seletivo")
public class ProcessoSeletivo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	 
	@NotNull(message="Ano é obrigatório")
	private Long ano;
	
	@NotBlank(message="Descrição do processo seletivo é obrigatório")
	private String descricao;
	
	@NotNull(message="Unidade é obrigatória")
	@ManyToOne
	@JoinColumn(name = "unidade_id")
	private Unidade unidade;
	
//	@Enumerated(EnumType.STRING)
	@NotNull(message="Tipo do processo é obrigatório")
	@JoinColumn(name = "tipo_processo")
	@OneToOne
	private TipoProcessoSeletivo tipoProcesso;
	
	@NotNull(message="Data da divulgação é obrigatório")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "divulgacao_edital")
	private LocalDate divulgacaoEdital;
	
	@NotNull(message="Data início do preenchimento é obrigatório")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "preenchimento_cadastro_inicio")
	private LocalDate preenchimentoCadastroInicio;
	
	@NotNull(message="Data fim do preenchimento é obrigatório")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name="preenchimento_cadastro_fim")
	private LocalDate preenchimentoCadastroFim;
	
	@NotNull(message="Data início da análise é obrigatório")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name="analise_perfil_inicio")
	private LocalDate analisePerfilInicio;
	
	@NotNull(message="Data fim da análise é obrigatório")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name="analise_perfil_fim")
	private LocalDate analisePerfilFim;
	
	@NotNull(message="Data início da validação é obrigatório")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name="validacao_comissao_interna_inicio")
	private LocalDate validacaoComissaoInternaInicio;

	@NotNull(message="Data fim da validação é obrigatório")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name="validacao_comissao_interna_fim")
	private LocalDate validacaoComissaoInternaFim;
	
	@NotNull(message="Data início do resultado é obrigatório")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name="resultado_inicio")
	private LocalDate resultadoInicio;
	
	@NotNull(message="Data fim do resultado é obrigatório")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name="resultado_fim")
	private LocalDate resultadoFim;
	
	@NotNull(message="Data início da efetivação é obrigatório")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name="efetivacao_concessao_bolsa_inicio")
	private LocalDate efetivacaoConcessaoBolsaInicio;
	
	@NotNull(message="Data fim da efetivação é obrigatório")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name="efetivacao_concessao_bolsa_fim")
	private LocalDate efetivacaoConcessaoBolsaFim;
	
	@Column(name = "nome_arquivo")
	private String nomeArquivo;
		
//	@JsonIgnore
	@JsonBackReference
	@Fetch(FetchMode.JOIN)
	@OneToMany(mappedBy = "processoSeletivo")
	private List<DetalhesProcessoSeletivo> detalhes;// = new ArrayList<DetalhesProcessoSeletivo>();

	@OneToOne
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;
	
	@OneToOne
	@JoinColumn(name = "salario_minimo_id")
	private SalarioMinimo salarioMinimo;
	
	@Transient
	private String estado;
	
	@NotNull(message = "Tipo de Edital é obrigatório")
	@OneToOne
	@JoinColumn(name = "tipo_edital_id")
	private TipoEdital tipoEdital;

//	@NotNull(message = "Numero do Edital é obrigatório")
	@OneToOne
	@JoinColumn(name = "numero_edital_id")
	private NumeroEdital numeroEdital;
	
	@Column(name = "numero_edital_principal")
	private Long numeroEditalPrincipal;
	
	@Column(name = "data_criacao")
	private LocalDate dataCriacao;
	
	@Column(name = "data_alteracao")
	private LocalDate dataAlteracao;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAno() {
		return ano;
	}

	public void setAno(Long ano) {
		this.ano = ano;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public TipoProcessoSeletivo getTipoProcesso() {
		return tipoProcesso;
	}

	public void setTipoProcesso(TipoProcessoSeletivo tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}

	public List<DetalhesProcessoSeletivo> getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(List<DetalhesProcessoSeletivo> detalhes) {
		this.detalhes = detalhes;
	}

	public LocalDate getDivulgacaoEdital() {
		return divulgacaoEdital;
	}

	public void setDivulgacaoEdital(LocalDate divulgacaoEdital) {
		this.divulgacaoEdital = divulgacaoEdital;
	}

	public LocalDate getPreenchimentoCadastroInicio() {
		return preenchimentoCadastroInicio;
	}

	public void setPreenchimentoCadastroInicio(LocalDate preenchimentoCadastroInicio) {
		this.preenchimentoCadastroInicio = preenchimentoCadastroInicio;
	}

	public LocalDate getPreenchimentoCadastroFim() {
		return preenchimentoCadastroFim;
	}

	public void setPreenchimentoCadastroFim(LocalDate preenchimentoCadastroFim) {
		this.preenchimentoCadastroFim = preenchimentoCadastroFim;
	}

	public LocalDate getAnalisePerfilInicio() {
		return analisePerfilInicio;
	}

	public void setAnalisePerfilInicio(LocalDate analisePerfilInicio) {
		this.analisePerfilInicio = analisePerfilInicio;
	}

	public LocalDate getAnalisePerfilFim() {
		return analisePerfilFim;
	}

	public void setAnalisePerfilFim(LocalDate analisePerfilFim) {
		this.analisePerfilFim = analisePerfilFim;
	}

	public LocalDate getValidacaoComissaoInternaInicio() {
		return validacaoComissaoInternaInicio;
	}

	public void setValidacaoComissaoInternaInicio(LocalDate validacaoComissaoInternaInicio) {
		this.validacaoComissaoInternaInicio = validacaoComissaoInternaInicio;
	}

	public LocalDate getValidacaoComissaoInternaFim() {
		return validacaoComissaoInternaFim;
	}

	public void setValidacaoComissaoInternaFim(LocalDate validacaoComissaoInternaFim) {
		this.validacaoComissaoInternaFim = validacaoComissaoInternaFim;
	}

	public LocalDate getResultadoInicio() {
		return resultadoInicio;
	}

	public void setResultadoInicio(LocalDate resultadoInicio) {
		this.resultadoInicio = resultadoInicio;
	}

	public LocalDate getResultadoFim() {
		return resultadoFim;
	}

	public void setResultadoFim(LocalDate resultadoFim) {
		this.resultadoFim = resultadoFim;
	}

	public LocalDate getEfetivacaoConcessaoBolsaInicio() {
		return efetivacaoConcessaoBolsaInicio;
	}

	public void setEfetivacaoConcessaoBolsaInicio(LocalDate efetivacaoConcessaoBolsaInicio) {
		this.efetivacaoConcessaoBolsaInicio = efetivacaoConcessaoBolsaInicio;
	}

	public LocalDate getEfetivacaoConcessaoBolsaFim() {
		return efetivacaoConcessaoBolsaFim;
	}

	public void setEfetivacaoConcessaoBolsaFim(LocalDate efetivacaoConcessaoBolsaFim) {
		this.efetivacaoConcessaoBolsaFim = efetivacaoConcessaoBolsaFim;
	}
	
	
	

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public boolean isProcessoIniciado() {
		return LocalDate.now().isAfter(this.divulgacaoEdital.minusDays(1));
	}
	
	public String getVerificarProcesso() {
		if (preenchimentoCadastroInicio.isBefore(LocalDate.now())) {
			return "Iniciado";
		}else if (preenchimentoCadastroFim.isAfter(LocalDate.now())){
			return "Finalizado";
		}else {
			return "Não Iniciado";
		}
	}

//	public List<DetalhesProcessoSeletivo> getDetalhes() {
//		return detalhes;
//	}
//
//	public void setDetalhes(List<DetalhesProcessoSeletivo> detalhes) {
//		this.detalhes = detalhes;
//	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isNovo() {
		return id == null;
	}
	
	public boolean temArquivo() {
		return StringUtils.isEmpty(nomeArquivo);
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public SalarioMinimo getSalarioMinimo() {
		return salarioMinimo;
	}

	public void setSalarioMinimo(SalarioMinimo salarioMinimo) {
		this.salarioMinimo = salarioMinimo;
	}

	public TipoEdital getTipoEdital() {
		return tipoEdital;
	}

	public void setTipoEdital(TipoEdital tipoEdital) {
		this.tipoEdital = tipoEdital;
	}

	public NumeroEdital getNumeroEdital() {
		return numeroEdital;
	}

	public void setNumeroEdital(NumeroEdital numeroEdital) {
		this.numeroEdital = numeroEdital;
	}

	public Long getNumeroEditalPrincipal() {
		return numeroEditalPrincipal;
	}

	public void setNumeroEditalPrincipal(Long numeroEditalPrincipal) {
		this.numeroEditalPrincipal = numeroEditalPrincipal;
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
	
	public boolean isMostraBotaoResultado() {
		
		System.out.println("Nome do Processo " + descricao);
		System.out.println("Data inicio " + getResultadoInicio());
		System.out.println("Data fim " + getResultadoFim());
		System.out.println("Hoje " + LocalDate.now());
		
		if (LocalDate.now().isAfter(resultadoInicio.minusDays(1)) && LocalDate.now().isBefore(resultadoFim.plusDays(1))) {
		    return true;
		}	
		
		return false;
	}	
	
	
//	@PrePersist
//	public void prePersist() {
//		this.dataCriacao = LocalDate.now();
//	}
//	
//	@PreUpdate
//	public void PreUpdate() {
//		this.dataAlteracao = LocalDate.now();
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
		ProcessoSeletivo other = (ProcessoSeletivo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
	
	
}
