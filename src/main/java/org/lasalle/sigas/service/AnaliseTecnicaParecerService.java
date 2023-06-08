package org.lasalle.sigas.service;

import org.lasalle.sigas.model.AnaliseTecnicaParecer;
import org.lasalle.sigas.repository.AnaliseTecnicaParecerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnaliseTecnicaParecerService {

	@Autowired
	AnaliseTecnicaParecerRepository analiseTecnicaParecerRepository;
	
	@Transactional
	public void salvar(AnaliseTecnicaParecer analiseTecnicaParecer) {
		analiseTecnicaParecerRepository.save(analiseTecnicaParecer);
	}
}
