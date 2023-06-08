package org.lasalle.sigas.service;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.lasalle.sigas.model.IrmaoCandidato;
import org.lasalle.sigas.repository.IrmaoCandidatoRepository;
import org.lasalle.sigas.service.exception.ImpossivelExcluirRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IrmaoCandidatoService {
	
	@Autowired
	IrmaoCandidatoRepository irmaoCandidatoRepository;
	
	@Transactional
	public void salvar(IrmaoCandidato irmaoCandidato) {
		irmaoCandidatoRepository.save(irmaoCandidato);
	}
	
	@Transactional
	public void excluir(Long id) {
		try {
			
			irmaoCandidatoRepository.delete(id);
			irmaoCandidatoRepository.flush();
			
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirRegistro("Não foi possível apagar essa informação");
		}
	}

}
