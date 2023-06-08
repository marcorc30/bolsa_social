package org.lasalle.sigas.repository;

import java.util.List;
import java.util.Optional;

import org.lasalle.sigas.model.DetalhesProcessoSeletivo;
import org.lasalle.sigas.model.ProcessoSeletivo;
import org.lasalle.sigas.model.Serie;
import org.lasalle.sigas.model.Turno;
import org.lasalle.sigas.repository.helper.detalhesProcessoSeletivo.DetalhesProcessoSeletivoRepositoryQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalhesProcessoSeletivoRepository extends JpaRepository<DetalhesProcessoSeletivo, Long>, DetalhesProcessoSeletivoRepositoryQueries{

	public List<DetalhesProcessoSeletivo> findByProcessoSeletivoIdOrderBySerieDescricao(Long idProcessoSeletivo);

	public List<DetalhesProcessoSeletivo> findByProcessoSeletivoId(Long idProcessoSeletivo);
	
	public DetalhesProcessoSeletivo findById(Long id);
	
	public Optional<DetalhesProcessoSeletivo> 
			findBySerieAndProcessoSeletivoAndTurno(Serie serie, ProcessoSeletivo processo, Turno turno);
	
	
}
