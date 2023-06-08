package org.lasalle.sigas.repository.helper.composicaoFamiliar;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.lasalle.sigas.model.ComposicaoFamiliar;

public class ComposicaoFamiliarRepositoryImpl implements ComposicaoFamiliarRepositoryQueries  {

	@PersistenceContext
	EntityManager manager;
	
	@Override
	public List<ComposicaoFamiliar> componentesAcimaDe18anos(Long idSolicitacao) {
		String jpql = "select cf from ComposicaoFamiliar cf where cf.dadosIniciais.id = :id "
				+ " and cf.idade >= 18";
		
		TypedQuery<ComposicaoFamiliar> tQuery = manager.createQuery(jpql, ComposicaoFamiliar.class);
		tQuery.setParameter("id", idSolicitacao);
		return tQuery.getResultList();
	}

}
