package org.lasalle.sigas.repository.helper.processoSeletivo;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.ProcessoSeletivo;
import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.repository.filter.ProcessoSeletivoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

public class ProcessoSeletivoRepositoryImpl implements ProcessoSeletivoRepositoryQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	
	public Page<ProcessoSeletivo> filtrar(ProcessoSeletivoFilter filter, Pageable pageable, List<Unidade> unidades) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(ProcessoSeletivo.class, "c");
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("id"), "id")
				.add(Projections.property("descricao"), "descricao")
				.add(Projections.property("unidade"),"unidade")
				.add(Projections.property("tipoProcesso"),"tipoProcesso")
				.add(Projections.property("divulgacaoEdital"),"divulgacaoEdital"))
		.setResultTransformer(Transformers.aliasToBean(ProcessoSeletivo.class));
		
		criteria.setFetchMode("detalhes", FetchMode.SELECT); 
		criteria.setFetchMode("unidade", FetchMode.JOIN);
		
//		criteria.createAlias("unidade", "unidade");

		//passando os parametros da page pelo objeto pageable
		int paginaAtual = pageable.getPageNumber();
		int registroMaximosPorPagina = pageable.getPageSize();
		int paginaInicial = registroMaximosPorPagina * paginaAtual;
		
		criteria.setFirstResult(paginaInicial);
		criteria.setMaxResults(registroMaximosPorPagina); //registros máximos por página

		
		Sort sort = pageable.getSort();
		
		if (sort != null) {
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			
			
			criteria.addOrder(order.isAscending() ? Order.asc(property) : Order.desc(property));
		}
		criteria.add(Restrictions.in("unidade", unidades));

		adicionarFiltro(filter, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filter));
		
		
		
	}
	
	private Long total(ProcessoSeletivoFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(ProcessoSeletivo.class);
		criteria.setProjection(Projections.rowCount());
		adicionarFiltro(filter, criteria);
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(ProcessoSeletivoFilter filtro, Criteria criteria) {
		if (filtro.getAnoVigencia() != null){
			criteria.add(Restrictions.eq("ano", filtro.getAnoVigencia()));
			
		}
		
		if (filtro.getUnidade() != null){
			criteria.add(Restrictions.eq("unidade", filtro.getUnidade()));
		}
		
		
		if (filtro.getTipoProcesso() != null){
			criteria.add(Restrictions.eq("tipoProcesso", filtro.getTipoProcesso()));
		}
		
		if (!StringUtils.isEmpty(filtro.getUf())) {
//			criteria.add(Restrictions.eq("uf", filtro.getUf()));
			criteria.createCriteria("unidade").add(Restrictions.eq("uf", filtro.getUf()));
		}
		
		
		
	}
	
	@Override
	public List<DadosIniciais> solicitacaoPorProcessoSeletivo(Long idProcesso) {
		
		String jpql = "select d from DadosIniciais d "
				+ "join fetch d.candidatos c "
				+ "join d.composicoes comp "
				+ "where d.processoSeletivo.id = :id";
		
		
		
		TypedQuery<DadosIniciais> tQuery = manager.createQuery(jpql, DadosIniciais.class);
		tQuery.setParameter("id", idProcesso);
		
		return tQuery.getResultList();

	}

	@Override
	public List<ProcessoSeletivo> processosPorUnidade(Long unidadeId) {
		String jpql = "select p from ProcessoSeletivo p join fetch p.unidade u where u.id = :unidadeId";
		TypedQuery<ProcessoSeletivo> tQuery = manager.createQuery(jpql, ProcessoSeletivo.class);
		tQuery.setParameter("unidadeId", unidadeId);
		return tQuery.getResultList();
	}

	@Override
	public List<ProcessoSeletivo> processosPorUnidades(List<Unidade> unidades) {
		String jpql = "select p from ProcessoSeletivo p join fetch p.unidade u where p.unidade in (:unidades)";
		TypedQuery<ProcessoSeletivo> tQuery = manager.createQuery(jpql, ProcessoSeletivo.class);
		tQuery.setParameter("unidades", unidades);
		return tQuery.getResultList();
	}

	
	
	@Override
	public List<ProcessoSeletivo> processosPorUnidadeData(Long unidadeId) {
		String jpql = "select p from ProcessoSeletivo p join fetch p.unidade u where u.id = :unidadeId and p.preenchimentoCadastroInicio <= :today and p.preenchimentoCadastroFim >= :today";
		TypedQuery<ProcessoSeletivo> tQuery = manager.createQuery(jpql, ProcessoSeletivo.class);
		tQuery.setParameter("unidadeId", unidadeId);
		tQuery.setParameter("today", LocalDate.now());
		
		return tQuery.getResultList();
	}
	
	
	@Override
	public List<ProcessoSeletivo> processosPorTipoEditalAnoUnidade(Long unidadeId) {
		String jpql = "select p from ProcessoSeletivo p join fetch p.unidade u where u.id = :unidadeId and p.tipoEdital.id = 1 ";
		TypedQuery<ProcessoSeletivo> tQuery = manager.createQuery(jpql, ProcessoSeletivo.class);
		tQuery.setParameter("unidadeId", unidadeId);
		return tQuery.getResultList();
	}


	

	
}
