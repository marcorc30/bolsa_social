package org.lasalle.sigas.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.lasalle.sigas.model.DetalhesProcessoSeletivo;
import org.lasalle.sigas.model.ProcessoSeletivo;
import org.lasalle.sigas.repository.DetalhesProcessoSeletivoRepository;
import org.lasalle.sigas.service.exception.ImpossivelExcluirDetalheProcessoSeletivoException;
import org.lasalle.sigas.service.exception.MotivoAlteracaoNaoInformadaException;
import org.lasalle.sigas.service.exception.SerieJaCadastradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DetalhesProcessoSeletivoService {
	
	@Autowired
	DetalhesProcessoSeletivoRepository detalhesProcessoSeletivoRepository;
	
	@PersistenceContext
	EntityManager manager;
	
	@Transactional
	public void salvar(DetalhesProcessoSeletivo detalhesProcessoSeletivo) {
		
		Optional<DetalhesProcessoSeletivo> detalhesProcesso = 
				detalhesProcessoSeletivoRepository.
					findBySerieAndProcessoSeletivoAndTurno(detalhesProcessoSeletivo.getSerie(), 
												   detalhesProcessoSeletivo.getProcessoSeletivo(),
												   detalhesProcessoSeletivo.getTurno());
		
		
		if (detalhesProcesso.isPresent() && detalhesProcessoSeletivo.isNovo()) {
			throw new SerieJaCadastradaException("Série/Turno já cadastrada nesse Processo Seletivo");
		}
		
		
		if (!detalhesProcessoSeletivo.isNovo() && detalhesProcessoSeletivo.getMotivoAlteracao() == null) {
			throw new MotivoAlteracaoNaoInformadaException("O motivo da alteração é obrigatório");
		}
		
		detalhesProcessoSeletivoRepository.save(detalhesProcessoSeletivo);
	}
	
	public List<ProcessoSeletivo> listarSeriesPorProcessoSeletivo(Long processoSeletivoId){
		

		String jpql = "select ps from ProcessoSeletivo ps join fetch ps.detalhes dp join fetch dp.serie s where ps.id = :pId";
		
		TypedQuery<ProcessoSeletivo> tProcessos = manager.createQuery(jpql, ProcessoSeletivo.class);
		tProcessos.setParameter("pId", processoSeletivoId);
		
	
		return tProcessos.getResultList();
	}

	@Transactional
	public void excluir(Long id) {
		
		try {
			detalhesProcessoSeletivoRepository.delete(id);
			detalhesProcessoSeletivoRepository.flush();
			
		}catch(PersistenceException e) {
			throw new ImpossivelExcluirDetalheProcessoSeletivoException("Não foi possível apagar essa Série/Ano. Podem haver candidatos inscritos nela.");
		}
		
		
	}

}
