package org.lasalle.sigas.service;

import javax.persistence.PersistenceException;

import org.lasalle.sigas.model.Despesa;
import org.lasalle.sigas.repository.DespesaRepository;
import org.lasalle.sigas.service.exception.ImpossivelExcluirRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DespesaService {

	@Autowired
	private DespesaRepository despesaRepository;
	
	@Transactional
	public void salvar(Despesa despesa) {
		despesaRepository.save(despesa);
	}
	
	@Transactional
	public void excluir(Long id) {
		
		try {
			despesaRepository.delete(id);
			despesaRepository.flush();
			
		}catch(PersistenceException e) {
			throw new ImpossivelExcluirRegistro("Não foi possível apagar essa informação");
		}
		
	}
}
