package org.lasalle.sigas.service;

import javax.persistence.PersistenceException;

import org.lasalle.sigas.model.RendaAgregada;
import org.lasalle.sigas.repository.RendaAgregadaRepository;
import org.lasalle.sigas.service.exception.ImpossivelExcluirRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RendaAgregadaService {
	
	@Autowired
	RendaAgregadaRepository rendaAgregadaRepository;
	
	@Transactional
	public void salvar(RendaAgregada rendaAgregada) {
		rendaAgregadaRepository.save(rendaAgregada);
	}
	
	@Transactional
	public void excluir(Long id) {
		try {
			rendaAgregadaRepository.delete(id);
			rendaAgregadaRepository.flush();
		}catch(PersistenceException e) {
			throw new ImpossivelExcluirRegistro("Não foi possível apagar essa informação");
		}
	}

}
