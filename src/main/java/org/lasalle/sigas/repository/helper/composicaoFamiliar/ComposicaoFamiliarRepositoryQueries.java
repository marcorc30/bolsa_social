package org.lasalle.sigas.repository.helper.composicaoFamiliar;

import java.util.List;

import org.lasalle.sigas.model.ComposicaoFamiliar;

public interface ComposicaoFamiliarRepositoryQueries {
	
	public List<ComposicaoFamiliar> componentesAcimaDe18anos(Long idSolicitacao);

}
