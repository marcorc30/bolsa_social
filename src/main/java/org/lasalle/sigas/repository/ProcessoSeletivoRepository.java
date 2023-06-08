package org.lasalle.sigas.repository;

import java.util.List;

import org.lasalle.sigas.model.ProcessoSeletivo;
import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.repository.helper.processoSeletivo.ProcessoSeletivoRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessoSeletivoRepository extends JpaRepository<ProcessoSeletivo, Long>, ProcessoSeletivoRepositoryQueries{

	public ProcessoSeletivo findById(Long id);
	
	public List<ProcessoSeletivo> findByUnidadeId(Long id);
	
	
}
