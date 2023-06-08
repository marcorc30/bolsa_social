package org.lasalle.sigas.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.lasalle.sigas.dto.DetalhesProcessoSeletivoDTO;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;




@SqlResultSetMapping(name = "detalhes.DetalhesProcessoSeletivoDTO", 
classes =  {
		@ConstructorResult(targetClass = DetalhesProcessoSeletivoDTO.class,
				columns = {
						@ColumnResult(name= "detalhesId", type = Long.class ),
						@ColumnResult(name= "id", type = Long.class ),
						@ColumnResult(name= "curso", type = String.class ),
						@ColumnResult(name= "serie", type = String.class ),
						@ColumnResult(name= "turno", type = String.class),
						@ColumnResult(name= "prevista_100", type = Integer.class ),
						@ColumnResult(name= "prevista_50", type = Integer.class ),
						@ColumnResult(name= "real_100", type = Integer.class ),
						@ColumnResult(name= "real_50", type = Integer.class )
						
				})
		
})




@Entity
@Table(name = "detalhes_processo_seletivo")
public class DetalhesProcessoSeletivo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message="Quantidade de Bolsas 50% é obrigatório")
	@Column(name="quantidade_bolsas_50")
	private Integer quantidadeBolsas50;
	
	@NotNull(message= "Quantidade de Bolsas 100% é obrigatório")
	@Column(name="quantidade_bolsas_100")
	private Integer quantidadeBolsas100;
	
	@NotNull(message= "Série é obrigatória")
	@OneToOne
	@JoinColumn(name="serie_id")
//	@JsonManagedReference
	private Serie serie;
	
	@NotNull(message = "Turno é obrigatório")
	@OneToOne
	@JoinColumn(name="turno_id")
	private Turno turno;
	
	@ManyToOne
	@JoinColumn(name="processo_seletivo_id")
	@JsonBackReference
	private ProcessoSeletivo processoSeletivo;
	

	@OneToOne
	@JoinColumn(name = "motivo_alteracao_id")
	private MotivoAlteracao motivoAlteracao;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_alteracao")
	private LocalDate dataAlteracao;
	
	@Column(name = "id_usuario")
	private Integer usuarioCriacao;

	@Column(name = "id_usuario_alteracao")
	private Integer usuarioAlteracao;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantidadeBolsas50() {
		return quantidadeBolsas50;
	}

	public void setQuantidadeBolsas50(Integer quantidadeBolsas50) {
		this.quantidadeBolsas50 = quantidadeBolsas50;
	}

	public Integer getQuantidadeBolsas100() {
		return quantidadeBolsas100;
	}

	public void setQuantidadeBolsas100(Integer quantidadeBolsas100) {
		this.quantidadeBolsas100 = quantidadeBolsas100;
	}

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	public ProcessoSeletivo getProcessoSeletivo() {
		return processoSeletivo;
	}

	public void setProcessoSeletivo(ProcessoSeletivo processoSeletivo) {
		this.processoSeletivo = processoSeletivo;
	}


	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public MotivoAlteracao getMotivoAlteracao() {
		return motivoAlteracao;
	}

	public void setMotivoAlteracao(MotivoAlteracao motivoAlteracao) {
		this.motivoAlteracao = motivoAlteracao;
	}

	public boolean isNovo() {
		return id == null;
	}
	
	
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDate getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(LocalDate dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Integer getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(Integer usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}

	public Integer getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(Integer usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
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
		DetalhesProcessoSeletivo other = (DetalhesProcessoSeletivo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
