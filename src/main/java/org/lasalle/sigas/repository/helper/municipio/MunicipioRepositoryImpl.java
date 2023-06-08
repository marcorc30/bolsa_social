package org.lasalle.sigas.repository.helper.municipio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.lasalle.sigas.model.Municipio;

public class MunicipioRepositoryImpl implements MunicipioRepositoryQueries{

	@PersistenceContext
	EntityManager manager;
	
	@Override
	public List<Municipio> municipioPorEstado(String uf) {
		
		String jpql = "select m from Municipio m where m.uf = :uf order by m.municipio";
		TypedQuery<Municipio> tQuery = manager.createQuery(jpql, Municipio.class);
		tQuery.setParameter("uf", uf);
		
		return tQuery.getResultList();
	}

}
