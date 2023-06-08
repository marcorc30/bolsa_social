package org.lasalle.sigas.service;

import javax.persistence.PersistenceException;

import org.lasalle.sigas.model.Beneficio;
import org.lasalle.sigas.repository.BeneficioRepository;
import org.lasalle.sigas.service.exception.ImpossivelExcluirRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BeneficioService {
	
	@Autowired
	private BeneficioRepository beneficioRepository;
	
	@Transactional
	public void salvar(Beneficio beneficio) {
		beneficioRepository.save(beneficio);
	}
	
	@Transactional
	public void excluir(Long id) {
		try {
			beneficioRepository.delete(id);
			beneficioRepository.flush();
			
		}catch(PersistenceException e) {
			throw new ImpossivelExcluirRegistro("Não foi possível excluir essa informação");
		}
	}

}
