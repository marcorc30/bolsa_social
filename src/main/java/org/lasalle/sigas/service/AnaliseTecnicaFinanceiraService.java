package org.lasalle.sigas.service;

import org.lasalle.sigas.model.AnaliseTecnicaFinanceira;
import org.lasalle.sigas.repository.AnaliseTecnicaFinanceiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnaliseTecnicaFinanceiraService {

	
	@Autowired
	AnaliseTecnicaFinanceiraRepository analiseTecnicaFinanceiraRepository;
	
	@Transactional
	public void salvar(AnaliseTecnicaFinanceira analiseTecnicaFinanceira) {
		analiseTecnicaFinanceiraRepository.save(analiseTecnicaFinanceira);
	}
}
