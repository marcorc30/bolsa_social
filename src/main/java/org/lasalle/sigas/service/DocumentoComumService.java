package org.lasalle.sigas.service;

import javax.persistence.PersistenceException;

import org.lasalle.sigas.model.DocumentoComum;
import org.lasalle.sigas.repository.DocumentoComumRepository;
import org.lasalle.sigas.service.exception.ImpossivelExcluirRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentoComumService {
	
	@Autowired
	DocumentoComumRepository documentoComumRepository;
	
	@Transactional
	public void salvar(DocumentoComum documentoComum) {
		documentoComumRepository.save(documentoComum);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			documentoComumRepository.delete(id);
			documentoComumRepository.flush();
			
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirRegistro("Não foi possível apagar essa informação");
		}
	}

}
