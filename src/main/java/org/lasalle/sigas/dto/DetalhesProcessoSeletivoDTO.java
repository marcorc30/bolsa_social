package org.lasalle.sigas.dto;

public class DetalhesProcessoSeletivoDTO {
		
	public DetalhesProcessoSeletivoDTO(Long detalhesId, Long id, String curso, String serie, String turno, Integer prevista_100,
			Integer prevista_50, Integer real_100, Integer real_50) {
		this.detalhesId = detalhesId;
		this.id = id;
		this.curso = curso;
		this.serie = serie;
		this.turno = turno;
		this.prevista_100 = prevista_100;
		this.prevista_50 = prevista_50;
		this.real_100 = real_100;
		this.real_50 = real_50;
	}

	private Long detalhesId;
	
	private Long id;
	
	private String curso;
	
	private String serie;
	
	private String turno;
	
	private Integer prevista_100;
	
	private Integer prevista_50;
	
	private Integer real_100;
	
	private Integer real_50;

	
	
	public Long getDetalhesId() {
		return detalhesId;
	}

	public void setDetalhesId(Long detalhesId) {
		this.detalhesId = detalhesId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public Integer getPrevista_100() {
		return prevista_100;
	}

	public void setPrevista_100(Integer prevista_100) {
		this.prevista_100 = prevista_100;
	}

	public Integer getPrevista_50() {
		return prevista_50;
	}

	public void setPrevista_50(Integer prevista_50) {
		this.prevista_50 = prevista_50;
	}

	public Integer getReal_100() {
		return real_100;
	}

	public void setReal_100(Integer real_100) {
		this.real_100 = real_100;
	}

	public Integer getReal_50() {
		return real_50;
	}

	public void setReal_50(Integer real_50) {
		this.real_50 = real_50;
	}
	
	

}
