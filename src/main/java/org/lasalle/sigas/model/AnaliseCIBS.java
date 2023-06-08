package org.lasalle.sigas.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "analise_cibs")
public class AnaliseCIBS {
	
	@Id
	private Long id;
	
	@NotBlank(message = "Concessão é obrigatório")
	private String concessao;
	
	@NotNull(message = "Percentual é obrigatório")
	@OneToOne
	@JoinColumn(name = "percentual_id")
	private PercentualParecer percentual;
	
//	@NotBlank(message = "Parcela Inicial é obrigatória")
	@Column(name = "parcela_inicial")
	private String parcelaInicial;
	
//	@NotBlank(message = "Parcela Final é obrigatória")
	@Column(name = "parcela_final")
	private String parcelaFinal;
	
	@NotBlank(message = "Observação é obrigatória")
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name = "solicitacao_id")
	private DadosIniciais dadosIniciais;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_analise")	
	private LocalDateTime dataAnalise; 
	

//	@ManyToOne
//	@JoinColumn(name = "detalhes_processo_seletivo_id")
//	private DetalhesProcessoSeletivo detalhesProcessoSeletivo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConcessao() {
		return concessao;
	}

	public void setConcessao(String concessao) {
		this.concessao = concessao;
	}


	public PercentualParecer getPercentual() {
		return percentual;
	}

	public void setPercentual(PercentualParecer percentual) {
		this.percentual = percentual;
	}

	public String getParcelaInicial() {
		return parcelaInicial;
	}

	public void setParcelaInicial(String parcelaInicial) {
		this.parcelaInicial = parcelaInicial;
	}

	public String getParcelaFinal() {
		return parcelaFinal;
	}

	public void setParcelaFinal(String parcelaFinal) {
		this.parcelaFinal = parcelaFinal;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public DadosIniciais getDadosIniciais() {
		return dadosIniciais;
	}

	public void setDadosIniciais(DadosIniciais dadosIniciais) {
		this.dadosIniciais = dadosIniciais;
	}

	public LocalDateTime getDataAnalise() {
		return dataAnalise;
	}

	public void setDataAnalise(LocalDateTime dataAnalise) {
		this.dataAnalise = dataAnalise;
	}
	
	@PrePersist
	public void dataAtual() {
		this.dataAnalise = LocalDateTime.now();
	}

	public Boolean isNovo() {
		return !StringUtils.isEmpty(observacao);
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
		AnaliseCIBS other = (AnaliseCIBS) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

	
	
}
