package org.lasalle.sigas.service;

import javax.persistence.PersistenceException;

import org.lasalle.sigas.model.ComissaoInterna;
import org.lasalle.sigas.repository.ComissaoInternaRepository;
import org.lasalle.sigas.service.exception.ImpossivelExcluirComissaoInternaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComissaoInternaService {

	@Autowired
	ComissaoInternaRepository comissaoInternaRepository;
	
	@Transactional
	public void salvar(ComissaoInterna comissaoInterna) {
		comissaoInternaRepository.save(comissaoInterna);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			comissaoInternaRepository.delete(id);
			comissaoInternaRepository.flush();
			
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirComissaoInternaException("Não foi possível apagar esse integrante da comissão interna");
		}
		
	}
	
	
}
