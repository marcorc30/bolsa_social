package org.lasalle.sigas.service;

import javax.persistence.PersistenceException;

import org.lasalle.sigas.model.ComposicaoFamiliar;
import org.lasalle.sigas.repository.ComposicaoFamiliarRepository;
import org.lasalle.sigas.service.exception.ImpossivelExcluirRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComposicaoFamiliarService {
	
	@Autowired
	private ComposicaoFamiliarRepository composicaoFamiliarRepository;
	
	@Transactional
	public void salvar(ComposicaoFamiliar composicaoFamiliar) {
		composicaoFamiliarRepository.save(composicaoFamiliar);
	}
	
	@Transactional
	public void excluir(Long id) {
		
		try {
			composicaoFamiliarRepository.delete(id);
			composicaoFamiliarRepository.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirRegistro("Não foi possível excluir a informação");
		}
		
	}

}
