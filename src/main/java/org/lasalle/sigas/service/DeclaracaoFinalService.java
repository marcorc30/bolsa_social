package org.lasalle.sigas.service;

import org.lasalle.sigas.model.DeclaracaoFinal;
import org.lasalle.sigas.repository.DeclaracaoFinalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeclaracaoFinalService {
	
	@Autowired
	private DeclaracaoFinalRepository declaracaoFinalRepository;
	
	@Transactional
	public void salvar(DeclaracaoFinal declaracaoFinal) {
		declaracaoFinalRepository.save(declaracaoFinal);
	}

}
