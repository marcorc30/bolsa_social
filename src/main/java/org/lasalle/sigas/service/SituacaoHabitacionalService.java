package org.lasalle.sigas.service;

import org.lasalle.sigas.model.SituacaoHabitacional;
import org.lasalle.sigas.repository.SituacaoHabitacionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SituacaoHabitacionalService {
	
	@Autowired
	private SituacaoHabitacionalRepository situacaoHabitacionalRepository;
	
	@Transactional
	public void salvar(SituacaoHabitacional situacaoHabitacional) {
		situacaoHabitacionalRepository.save(situacaoHabitacional);
	}

}
