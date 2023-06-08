package org.lasalle.sigas.service;

import javax.persistence.PersistenceException;

import org.lasalle.sigas.model.Documento;
import org.lasalle.sigas.repository.DocumentoRepository;
import org.lasalle.sigas.service.exception.ImpossivelExcluirRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentoService {
	
	@Autowired
	DocumentoRepository documentoRepository;
	
	@Transactional
	public void salvar(Documento documento) {
		documentoRepository.save(documento);
	}
	 
	@Transactional
	public void excluir(Long id) {
		try {
			documentoRepository.delete(id);
			documentoRepository.flush();
			
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirRegistro("Não foi possível apagar essa informação");
		}
	}
	

}
