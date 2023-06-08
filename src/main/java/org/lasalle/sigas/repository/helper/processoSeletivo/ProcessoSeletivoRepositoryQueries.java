package org.lasalle.sigas.repository.helper.processoSeletivo;

import java.util.List;

import javax.persistence.Query;

import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.ProcessoSeletivo;
import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.repository.filter.ProcessoSeletivoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProcessoSeletivoRepositoryQueries {
	
	public Page<ProcessoSeletivo> filtrar(ProcessoSeletivoFilter filter, Pageable pageable, List<Unidade> unidades);
	
	public List<DadosIniciais> solicitacaoPorProcessoSeletivo(Long idProcesso);
	
	public List<ProcessoSeletivo> processosPorUnidade(Long unidadeId);
	
	public List<ProcessoSeletivo> processosPorUnidades(List<Unidade> unidades);
	
	public List<ProcessoSeletivo> processosPorTipoEditalAnoUnidade(Long idUnidade);
	
	public List<ProcessoSeletivo> processosPorUnidadeData(Long unidadeId);
	
	
	

}
