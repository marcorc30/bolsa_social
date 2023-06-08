package org.lasalle.sigas.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnidadeService {
	
	@Autowired
	UnidadeRepository unidadeRepository;
	
	
	public List<String> listarUfs(){
		
		List<Unidade> listaUnidades = unidadeRepository.findAll();
		Set<String> ufs = new HashSet<String>();
		
		for (Unidade unidade : listaUnidades) {
			ufs.add(unidade.getUf());
		}
		
		List<String> ufsString = new ArrayList<String>(ufs);
		
		Collections.sort(ufsString);
		
		return ufsString;
		
	}
	
	@Transactional
	public void salvar(Unidade unidade) {
		unidadeRepository.save(unidade);
	}

}
