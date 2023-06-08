package org.lasalle.sigas.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "salario_minimo")
public class SalarioMinimo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@Column(name = "data_cadastro")
	public LocalDate dataCadastro;
	
	@Column(name = "valor_mes")
	public BigDecimal valorMes;
	
	@Column(name = "valor_dia")
	public BigDecimal valorDia;
	
	@Column(name = "valor_hora")
	public BigDecimal valorHora;
	
	@Column(name = "data_vigencia_ini")
	public LocalDate dataVigenciaIni;
	
	@Column(name = "base_legal")
	public String baseLegal;
	
	public Boolean atual;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public BigDecimal getValorMes() {
		return valorMes;
	}

	public void setValorMes(BigDecimal valorMes) {
		this.valorMes = valorMes;
	}

	public BigDecimal getValorDia() {
		return valorDia;
	}

	public void setValorDia(BigDecimal valorDia) {
		this.valorDia = valorDia;
	}

	public BigDecimal getValorHora() {
		return valorHora;
	}

	public void setValorHora(BigDecimal valorHora) {
		this.valorHora = valorHora;
	}

	public LocalDate getDataVigenciaIni() {
		return dataVigenciaIni;
	}

	public void setDataVigenciaIni(LocalDate dataVigenciaIni) {
		this.dataVigenciaIni = dataVigenciaIni;
	}

	public String getBaseLegal() {
		return baseLegal;
	}

	public void setBaseLegal(String baseLegal) {
		this.baseLegal = baseLegal;
	}

	public Boolean getAtual() {
		return atual;
	}

	public void setAtual(Boolean atual) {
		this.atual = atual;
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
		SalarioMinimo other = (SalarioMinimo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
