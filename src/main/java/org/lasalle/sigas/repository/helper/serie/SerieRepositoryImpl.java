package org.lasalle.sigas.repository.helper.serie;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.lasalle.sigas.model.Serie;

public class SerieRepositoryImpl implements SerieRepositoryQueries{

	@PersistenceContext
	EntityManager manager;
	
	@Override
	public List<Serie> seriesPorUnidade(Long idUnidade) {
		
		String jpql = "select s from Serie s join fetch s.unidades u where u.id = :id";
		TypedQuery<Serie> tQuery = manager.createQuery(jpql, Serie.class);
		tQuery.setParameter("id", idUnidade);
		
		return tQuery.getResultList();
	}
	


}
