package org.lasalle.sigas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name = "series_habilitadas_processo")
public class SeriesHabilitadasProcessoSeletivo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private ProcessoSeletivo processoSeletivo;
	
	private Serie serie;

}
