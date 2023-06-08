package org.lasalle.sigas.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.lasalle.sigas.dto.SolicitacoesConsultaDTO;
import org.lasalle.sigas.dto.SolicitacoesConsultaDTOCIBS;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;


@SqlResultSetMapping(name = "solicitacao.SolicitacoesDTO", 
classes =  {
		@ConstructorResult(targetClass = SolicitacoesConsultaDTO.class,
				columns = {
						@ColumnResult(name= "id", type = Long.class ),
						@ColumnResult(name= "protocolo", type = String.class ),
						@ColumnResult(name= "nome", type = String.class ),
//						@ColumnResult(name= "curso", type = String.class ),
						@ColumnResult(name= "status", type = String.class),
						@ColumnResult(name= "rendaBruta", type = BigDecimal.class ),
						@ColumnResult(name= "despesa", type = BigDecimal.class ),
						@ColumnResult(name= "totalComponentes", type = Integer.class ),
						@ColumnResult(name= "rendaPerCapta", type = BigDecimal.class ),
						@ColumnResult(name= "qtdIrmaos", type = Integer.class ),
						@ColumnResult(name= "cadunico", type = Boolean.class ),
						@ColumnResult(name= "moraperto", type = Boolean.class ),
						@ColumnResult(name= "ic", type = Integer.class )
						
				})
		
})






//@SessionScope
@Entity
@Table(name = "solicitacao")
public class DadosIniciais implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long ano;
	
	private String protocolo;
	
	@Transient
	private String uf;
	
//	@ManyToOne()
//	@JoinColumn(name = "candidato_id")
//	@Transient
//	private Candidato candidato;
	
	@OneToMany(mappedBy = "dadosIniciais")
	private List<Candidato> candidatos;
	
	@OneToMany(mappedBy = "dadosIniciais")
	private List<ComposicaoFamiliar> composicoes;
	
	

	@Transient
	private String candidato;
	
	@ManyToOne
	@JoinColumn(name = "unidade_id")
	@NotNull(message = "É obrigatório informar a unidade")
	private Unidade unidade;
	
	@ManyToOne
	@JoinColumn(name = "processo_seletivo_id")
	@NotNull(message="É obrigatório informar o processo seletivo")
	private ProcessoSeletivo processoSeletivo;
		
	@ManyToOne
	@JoinColumn(name = "detalhes_processo_seletivo_id")
	@NotNull(message = "É obrigatório informar a série/ano")
	private DetalhesProcessoSeletivo detalhesProcessoSeletivo;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_solicitacao")
	private LocalDateTime dataSolicitacao;
	
	private Boolean concluido;
	
	private Boolean etapa1;
	
	private Boolean etapa2;
	
	private Boolean etapa3;
	
	private Boolean etapa4;
	
	private Boolean etapa5;
	
	private Boolean etapa6;
	
	private Boolean etapa7;
	
	private Boolean etapa8;
	
	private Boolean etapa9;
	
	private Boolean etapa10;
	
	private Boolean etapa11;
	
	private Boolean etapa12;
	
	private Boolean etapa13;
	
	private Boolean etapa14;
	
	private Boolean etapa15;
	
	private Boolean etapa16;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusSolicitacao status;

	@Column(name = "status_anterior")
	@Enumerated(EnumType.STRING)
	private StatusSolicitacao statusAnterior;
		
	
	@Column(name = "data_criacao")
	private LocalDate dataCriacao;
	
	@Column(name = "data_alteracao")
	private LocalDate dataAlteracao;
	
	@Column(name = "motivo_alteracao_status")
	private String motivoAlteracaoStatus;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_alteracao_status")
	private LocalDateTime dataAlteracaoStatus;
	
	@Column(name = "usuario_alteracao_status")
	private String usuarioAlteracaoStatus;
	
	public Boolean isTemStatusAnterior() {
		return !StringUtils.isEmpty(statusAnterior);
	}

	@PrePersist
	private void dataAtual() {
		this.dataSolicitacao = LocalDateTime.now();
//		this.dataCriacao = LocalDate.now();
	}
	
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

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public ProcessoSeletivo getProcessoSeletivo() {
		return processoSeletivo;
	}

	public void setProcessoSeletivo(ProcessoSeletivo processoSeletivo) {
		this.processoSeletivo = processoSeletivo;
	}

	public DetalhesProcessoSeletivo getDetalhesProcessoSeletivo() {
		return detalhesProcessoSeletivo;
	}

	public void setDetalhesProcessoSeletivo(DetalhesProcessoSeletivo detalhesProcessoSeletivo) {
		this.detalhesProcessoSeletivo = detalhesProcessoSeletivo;
	}
	
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
	
	
	

	public String getCandidato() {
		return candidato;
	}

	public void setCandidato(String candidato) {
		this.candidato = candidato;
	}

	public List<Candidato> getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(List<Candidato> candidatos) {
		this.candidatos = candidatos;
	}

	public LocalDateTime getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}



	public Boolean getConcluido() {
		return concluido;
	}

	public void setConcluido(Boolean concluido) {
		this.concluido = concluido;
	}

	public Boolean getEtapa1() {
		return etapa1;
	}

	public void setEtapa1(Boolean etapa1) {
		this.etapa1 = etapa1;
	}

	public Boolean getEtapa2() {
		return etapa2;
	}

	public void setEtapa2(Boolean etapa2) {
		this.etapa2 = etapa2;
	}

	public Boolean getEtapa3() {
		return etapa3;
	}

	public void setEtapa3(Boolean etapa3) {
		this.etapa3 = etapa3;
	}

	public Boolean getEtapa4() {
		return etapa4;
	}

	public void setEtapa4(Boolean etapa4) {
		this.etapa4 = etapa4;
	}

	public Boolean getEtapa5() {
		return etapa5;
	}

	public void setEtapa5(Boolean etapa5) {
		this.etapa5 = etapa5;
	}

	public Boolean getEtapa6() {
		return etapa6;
	}

	public void setEtapa6(Boolean etapa6) {
		this.etapa6 = etapa6;
	}

	public Boolean getEtapa7() {
		return etapa7;
	}

	public void setEtapa7(Boolean etapa7) {
		this.etapa7 = etapa7;
	}

	public Boolean getEtapa8() {
		return etapa8;
	}

	public void setEtapa8(Boolean etapa8) {
		this.etapa8 = etapa8;
	}

	public Boolean getEtapa9() {
		return etapa9;
	}

	public void setEtapa9(Boolean etapa9) {
		this.etapa9 = etapa9;
	}

	public Boolean getEtapa10() {
		return etapa10;
	}

	public void setEtapa10(Boolean etapa10) {
		this.etapa10 = etapa10;
	}

	public Boolean getEtapa11() {
		return etapa11;
	}

	public void setEtapa11(Boolean etapa11) {
		this.etapa11 = etapa11;
	}

	public Boolean getEtapa12() {
		return etapa12;
	}

	public void setEtapa12(Boolean etapa12) {
		this.etapa12 = etapa12;
	}

	public Boolean getEtapa13() {
		return etapa13;
	}

	public void setEtapa13(Boolean etapa13) {
		this.etapa13 = etapa13;
	}

	public Boolean getEtapa14() {
		return etapa14;
	}

	public void setEtapa14(Boolean etapa14) {
		this.etapa14 = etapa14;
	}

	public Boolean getEtapa15() {
		return etapa15;
	}

	public void setEtapa15(Boolean etapa15) {
		this.etapa15 = etapa15;
	}

	public Boolean getEtapa16() {
		return etapa16;
	}

	public void setEtapa16(Boolean etapa16) {
		this.etapa16 = etapa16;
	}
	
	


	public StatusSolicitacao getStatus() {
		return status;
	}

	public void setStatus(StatusSolicitacao status) {
		this.status = status;
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

	public String getMotivoAlteracaoStatus() {
		return motivoAlteracaoStatus;
	}

	public void setMotivoAlteracaoStatus(String motivoAlteracaoStatus) {
		this.motivoAlteracaoStatus = motivoAlteracaoStatus;
	}

	public LocalDateTime getDataAlteracaoStatus() {
		return dataAlteracaoStatus;
	}

	public void setDataAlteracaoStatus(LocalDateTime dataAlteracaoStatus) {
		this.dataAlteracaoStatus = dataAlteracaoStatus;
	}

	public String getUsuarioAlteracaoStatus() {
		return usuarioAlteracaoStatus;
	}

	public void setUsuarioAlteracaoStatus(String usuarioAlteracaoStatus) {
		this.usuarioAlteracaoStatus = usuarioAlteracaoStatus;
	}

	public StatusSolicitacao getStatusAnterior() {
		return statusAnterior;
	}

	public void setStatusAnterior(StatusSolicitacao statusAnterior) {
		this.statusAnterior = statusAnterior;
	}

	@PostPersist
	public void preInsert() {
		protocolo = String.format("%04d",  ano) + "." 
				+   String.format("%04d",  unidade.getId()) + "." 
				+   String.format("%04d",  processoSeletivo.getId()) + "." 
				+   String.format("%04d",  id);
		
		
	}
	
	public boolean isMostrarBotaoAnalise() {
		if (this.status.equals(StatusSolicitacao.EM_PREENCHIMENTO)) {
			return false;
		}
		
		return true;
	}
	
	public boolean isMostrarBotaoResultado() {
		if (this.status.equals(StatusSolicitacao.FINALIZADO)) {
			return true;
		}
		
		return false;
	}

	
//	@PreUpdate
//	public void preUpdate() {
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
		DadosIniciais other = (DadosIniciais) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	

}
