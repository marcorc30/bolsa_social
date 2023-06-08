package org.lasalle.sigas.service;

import javax.persistence.PersistenceException;

import org.lasalle.sigas.model.BemMovel;
import org.lasalle.sigas.repository.BemMovelRepository;
import org.lasalle.sigas.service.exception.ImpossivelExcluirRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BemMovelService {

	@Autowired
	BemMovelRepository bemMovelRepository;
	
	@Transactional
	public void salvar(BemMovel veiculo) {
		bemMovelRepository.save(veiculo);
	} 
	
	@Transactional
	public void excluir(Long id) {
		try {
			bemMovelRepository.delete(id);
			bemMovelRepository.flush();
		} catch (PersistenceException e) {
			
			throw new ImpossivelExcluirRegistro("Não foi possível excluir a informação");
		}
	}
	
}
