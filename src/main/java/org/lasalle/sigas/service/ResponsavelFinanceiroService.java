package org.lasalle.sigas.service;

import org.lasalle.sigas.model.ResponsavelFinanceiro;
import org.lasalle.sigas.repository.ResponsavelFinanceiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResponsavelFinanceiroService {

	@Autowired
	ResponsavelFinanceiroRepository responsavelFinanceiroRepository;
	
	@Transactional
	public void salvar(ResponsavelFinanceiro responsavelFinanceiro) {
		responsavelFinanceiroRepository.save(responsavelFinanceiro);
	}
}
