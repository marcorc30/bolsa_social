package org.lasalle.sigas.service;

import org.lasalle.sigas.model.AnaliseCIBS;
import org.lasalle.sigas.repository.AnaliseCIBSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnaliseCIBSService {

	@Autowired
	AnaliseCIBSRepository analiseCIBSRepository;
	
	
	@Transactional
	public void salvar(AnaliseCIBS analiseCIBS) {
		analiseCIBSRepository.save(analiseCIBS);
	}
}
 