package org.lasalle.sigas.service;

import org.lasalle.sigas.model.AnaliseDocumental;
import org.lasalle.sigas.repository.AnaliseDocumentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnaliseDocumentalService {
	
	@Autowired
	AnaliseDocumentalRepository analiseDocumentalRepository;

	@Transactional
	public void salvar(AnaliseDocumental analiseDocumental) {
		analiseDocumentalRepository.save(analiseDocumental);
	}
	
}
