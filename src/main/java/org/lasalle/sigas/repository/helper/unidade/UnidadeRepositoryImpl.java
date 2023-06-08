package org.lasalle.sigas.repository.helper.unidade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.security.UsuarioSistema;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public class UnidadeRepositoryImpl implements UnidadeRepositoryQueries{

	@PersistenceContext
	EntityManager manager;
	
	@Override
	public Unidade funcionariosDaUnidade(Long id) {
		String jpql = "select u from Unidade u join fetch u.funcionarios f where u.id = :id";
		TypedQuery<Unidade> tQuery = manager.createQuery(jpql, Unidade.class);
		tQuery.setParameter("id", id);
		
		return tQuery.getSingleResult();
	}

	@Override
	public List<Unidade> unidadesPorUsuario(Long id) {
		String jpql = "select u from Unidade u join fetch u.usuarios us where us.id = :id";
		TypedQuery<Unidade> tQuery = manager.createQuery(jpql, Unidade.class);
		tQuery.setParameter("id", id); 
		
		return tQuery.getResultList();
	}
	
	@Override
	public List<Unidade> unidadesPorUsuarioAndUf(Long id, String uf) {
		String jpql = "select u from Unidade u join fetch u.usuarios us where us.id = :id and u.uf = :uf";
		TypedQuery<Unidade> tQuery = manager.createQuery(jpql, Unidade.class);
		tQuery.setParameter("id", id);
		tQuery.setParameter("uf", uf);
		
		return tQuery.getResultList();
	}

	@Override
	public List<Unidade> unidadePorUf(String uf) {
		String jpql = "select distinct u from Unidade u join fetch u.funcionarios f where u.uf = :uf";
		TypedQuery<Unidade> tQuery = manager.createQuery(jpql, Unidade.class);
		tQuery.setParameter("uf", uf);
		return tQuery.getResultList();
	}
	
	


}
