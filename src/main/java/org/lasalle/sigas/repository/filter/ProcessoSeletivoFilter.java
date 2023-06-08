package org.lasalle.sigas.repository.filter;

import java.util.List;

import org.lasalle.sigas.model.TipoProcessoSeletivo;
import org.lasalle.sigas.model.Unidade;

public class ProcessoSeletivoFilter {
	
	private Long anoVigencia;
	private Unidade unidade;
	private TipoProcessoSeletivo tipoProcesso;
	private List<Unidade> unidades;
	private String uf;
	
	public Long getAnoVigencia() {
		return anoVigencia;
	}
	public void setAnoVigencia(Long anoVigencia) {
		this.anoVigencia = anoVigencia;
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
	public List<Unidade> getUnidades() {
		return unidades;
	}
	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	
	
	
	
	

}
