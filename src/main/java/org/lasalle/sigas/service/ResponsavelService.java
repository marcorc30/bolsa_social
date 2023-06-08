package org.lasalle.sigas.service;

import javax.persistence.PersistenceException;

import org.lasalle.sigas.model.Responsavel;
import org.lasalle.sigas.repository.ResponsavelRepository;
import org.lasalle.sigas.service.exception.ImpossivelExcluirRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResponsavelService {
	
	@Autowired
	private ResponsavelRepository responsavelRepository;
	
	@Transactional
	public void salvar(Responsavel responsavel) {
		responsavelRepository.save(responsavel);
		 
	}
	
	@Transactional
	public void excluir(Long id) {
		try {
			responsavelRepository.delete(id);
			responsavelRepository.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirRegistro("Não foi possível excluir a informação");
		}
	}

}
