package org.lasalle.sigas.repository.helper.detalhesProcessoSeletivo;

import java.util.List;

import javax.persistence.Query;

import org.lasalle.sigas.dto.DetalhesProcessoSeletivoDTO;
import org.lasalle.sigas.dto.UnidadeTotal;

public interface DetalhesProcessoSeletivoRepositoryQueries {
	
	
	public List<DetalhesProcessoSeletivoDTO> listarDetalhes(Long idProcesso);
	
	public Integer totalInscricoes(Integer ano, List<Long> unidades);
	
	public Integer total100(Integer ano, List<Long> unidades);
	
	public Integer total50(Integer ano, List<Long> unidades);
	
	public Integer totalGratuidade(Integer ano, List<Long> unidades);
	
	public List<Integer> anos();
	


	
	
	
}