package org.lasalle.sigas.repository.helper.funcionario;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.lasalle.sigas.model.Funcionario;

public class FuncionarioRepositoryImpl implements FuncionarioRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Funcionario> funcionariosAssistente() {
		String jpql = "select f from Funcionario f where f.ehAssistente = true";
		TypedQuery<Funcionario> tQuery = manager.createQuery(jpql, Funcionario.class);
		
		return tQuery.getResultList(); 
	}

	@Override
	public List<Funcionario> funcionariosPorUnidade(Long idUnidade) {
		String jpql = "select f from Funcionario f join fetch f.unidades u where u.id = :idUnidade";
		
		TypedQuery<Funcionario> tQuery = manager.createQuery(jpql, Funcionario.class);
		tQuery.setParameter("idUnidade", idUnidade);
		
		return tQuery.getResultList();
	}

}
