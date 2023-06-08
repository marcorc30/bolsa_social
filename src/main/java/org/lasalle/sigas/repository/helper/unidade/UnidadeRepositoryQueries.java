package org.lasalle.sigas.repository.helper.unidade;

import java.util.List;

import org.lasalle.sigas.model.Unidade;

public interface UnidadeRepositoryQueries { 
	
	public Unidade funcionariosDaUnidade(Long id);
	
	public List<Unidade> unidadesPorUsuario(Long id);
	
	public List<Unidade> unidadesPorUsuarioAndUf(Long id, String uf);
	
	public List<Unidade> unidadePorUf(String uf);
	

	


}
