package org.lasalle.sigas.service;

import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DadosIniciaisService {
	
	@Autowired
	DadosInciaisRepository dadosIniciaisRepository;
	
	@Transactional
	public DadosIniciais salvar(DadosIniciais dadosIniciais) {
		return dadosIniciaisRepository.save(dadosIniciais);
	}
	
	@Transactional
	public void apagar(DadosIniciais dadosIniciais) {
		dadosIniciaisRepository.delete(dadosIniciais);
	}

}
