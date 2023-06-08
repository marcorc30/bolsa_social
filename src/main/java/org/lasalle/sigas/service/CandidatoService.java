package org.lasalle.sigas.service;

import org.lasalle.sigas.model.Candidato;
import org.lasalle.sigas.repository.CandidatoRepository;
import org.lasalle.sigas.service.exception.CandidatoSemLaudoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CandidatoService {

	@Autowired
	private CandidatoRepository candidatoRepository;
	
	@Transactional
	public Candidato salvar(Candidato candidato) {
		
		if (candidato.getPossuiDeficiencia() && StringUtils.isEmpty(candidato.getArquivoLaudo())) {
			throw new CandidatoSemLaudoException("Foi informado que o candidato possui deficiência. Favor fazer o upload do laudo médico");
		}
		
		if (!candidato.getPossuiDeficiencia() && !StringUtils.isEmpty(candidato.getArquivoLaudo())) {
			throw new CandidatoSemLaudoException("Upload do laudo foi feito. Favor alterar o campo Possui Deficiência para SIM");
		}
		
		
		return candidatoRepository.save(candidato);
	}
	
	
}
