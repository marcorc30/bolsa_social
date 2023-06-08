package org.lasalle.sigas.repository.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.lasalle.sigas.model.Grupo;
import org.lasalle.sigas.model.ProcessoSeletivo;
import org.lasalle.sigas.model.Usuario;
import org.lasalle.sigas.model.UsuarioGrupo;
import org.lasalle.sigas.repository.filter.UsuarioFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQueries{

	@PersistenceContext
	EntityManager manager;
	
	@Override
	public Optional<Usuario> porEmailEAtivo(String email) {
		
		return manager.createQuery("from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class)
			.setParameter("email", email).getResultList().stream().findFirst();
		
	}

	@Override
	public List<String> permissoes(Usuario usuario) {
		
		return manager
			.createQuery("select distinct p.nome from Usuario u join u.grupos g join g.permissoes p where u = :usuario", String.class)
			.setParameter("usuario", usuario).getResultList();
	}
	
//	@Override
//	public List<Unidade> unidades(Usuario usuario) {
//		
//		return manager
//			.createQuery("select distinct p.nome from Usuario u join u.unidades un where u = :usuario", Unidade.class)
//			.setParameter("usuario", usuario).getResultList();
//	}	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class, "u");
		criteria.setFetchMode("unidades", FetchMode.SELECT); 
		int paginaAtual = pageable.getPageNumber();
		int registroMaximosPorPagina = pageable.getPageSize();
		int paginaInicial = registroMaximosPorPagina * paginaAtual;
		
		criteria.setFirstResult(paginaInicial);
		criteria.setMaxResults(registroMaximosPorPagina); //registros máximos por página

		
//		Sort sort = pageable.getSort();
//		
//		if (sort != null) {
//			Sort.Order order = sort.iterator().next();
//			String property = order.getProperty();
//			
//			
//			criteria.addOrder(order.isAscending() ? Order.asc(property) : Order.desc(property));
//		}
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		adicionarFiltro(filter, criteria);
		
		List<Usuario> filtrados = criteria.list();
		filtrados.forEach(u -> Hibernate.initialize(u.getGrupos()));
		return new PageImpl<>(filtrados, pageable, total(filter));

	}

	private Long total(UsuarioFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		criteria.setProjection(Projections.rowCount());
		adicionarFiltro(filtro, criteria);
		return (Long) criteria.uniqueResult();
	}

	
	@Transactional(readOnly = true)
	@Override
	public Usuario buscarUsuarioComGrupos(Long id) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);		
	
		criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
		criteria.setFetchMode("unidades", FetchMode.SELECT); 
		
//		criteria.createAlias("unidades", "u", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("id", id));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Usuario) criteria.uniqueResult();
	}

	
	
	private void adicionarFiltro(UsuarioFilter filtro, Criteria criteria) {
		if (!StringUtils.isEmpty(filtro.getCpf())) {
			criteria.add(Restrictions.ilike("cpf", filtro.getCpf(), MatchMode.ANYWHERE));
		}
		
		if (!StringUtils.isEmpty(filtro.getNome())){
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		
		if (!StringUtils.isEmpty(filtro.getEmail())){
			criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.ANYWHERE));
		}
		
//		criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
		if (filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()) {
			
			List<Criterion> subquery = new ArrayList<>();
			
			for (Long codigoGrupo : filtro.getGrupos().stream().mapToLong(Grupo::getId).toArray()) {
				DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
				dc.add(Restrictions.eq("id.grupo.id", codigoGrupo));
				dc.setProjection(Projections.property("id.usuario"));
				
				subquery.add(Subqueries.propertyIn("id", dc));
			}
			
			Criterion[] criterions = new Criterion[subquery.size()];
			criteria.add(Restrictions.and(subquery.toArray(criterions)));
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> buscarUsuarioPeloId(Long id) {
		String jpql = "select u from Usuario u where u.id = :id";
		TypedQuery<Usuario> tQuery = manager.createQuery(jpql, Usuario.class);
		tQuery.setParameter("id", id);
		
		System.out.println("tamanho da lista " + tQuery.getResultList().size());
		
		return tQuery.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario buscarUsuarioPeloHash(String hash) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);		
		
		criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
		criteria.setFetchMode("grupos", FetchMode.SELECT);
		criteria.setFetchMode("unidades", FetchMode.SELECT); 
		criteria.add(Restrictions.eq("hash", hash));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Usuario) criteria.uniqueResult();

	}

	
}
