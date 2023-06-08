package org.lasalle.sigas.repository.helper.solicitacao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.lasalle.sigas.dto.SolicitacoesConsultaDTO;
import org.lasalle.sigas.dto.UnidadeTotal;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.Unidade;
import org.springframework.util.StringUtils;

public class DadosInciaisRepositoryImpl implements DadosInciaisRepositoryQueries{

	@PersistenceContext
	EntityManager manager;
	
	@Override
	public List<DadosIniciais> solicitacaoPorUsuario(Long idUsuario) {
		
		String jpql = "select d from DadosIniciais ";
		
		TypedQuery<DadosIniciais> tQuery = manager.createQuery(jpql, DadosIniciais.class);
		
		return tQuery.getResultList();
	}
	
	@Override
	public List<DadosIniciais> solicitacaoPorIdUsuario(Long idUsuario){
//		String jpql = "select d from DadosIniciais d join fetch d.candidatos c where d.usuario.id = :id";

		String jpql = "select d from DadosIniciais d join fetch d.candidatos c join fetch d.processoSeletivo ps where d.usuario.id = :id";
		TypedQuery<DadosIniciais> tQuery = manager.createQuery(jpql, DadosIniciais.class);
		tQuery.setParameter("id", idUsuario);
		return tQuery.getResultList();
	}

	
	@Override
	public List<SolicitacoesConsultaDTO> solicitacaoPorProcessoSeletivoConsultaNativa(Long idProcesso) {
		
		String sql2 =
				"select " + 
				"	s.id as id " + 
				"	,protocolo as protocolo " + 
				"	,c.nome as nome " + 
				"	,dps.curso as curso" + 
				"	,s.status as status " + 
				"	,isnull((select sum(salario) from solicitacao_composicao_familiar where solicitacao_id = s.id group by solicitacao_id),0)As rendaBruta " + 
				"	,isnull((select sum(valor) from despesa where solicitacao_id = s.id group by solicitacao_id),0)As despesa " + 
				"	,isnull((select count(solicitacao_id) from solicitacao_composicao_familiar where solicitacao_id = s.id group by solicitacao_id),0)As totalComponentes " + 
				"	,( " + 
				"		select sum(salario) / (select count(solicitacao_id) from solicitacao_composicao_familiar where solicitacao_id = s.id group by solicitacao_id) " + 
				"		from solicitacao_composicao_familiar " + 
				"		where solicitacao_id = s.id " + 
				"		group by solicitacao_id)As rendaPerCapta " + 
				"	,ISNULL(sbi.qtd,0) As qtdIrmaos " + 
				"	,df.inscrito_cad_unico As cadunico " + 
				"	,c.mora_perto_escola As moraperto " + 
				"	,(ISNULL(sbi.qtd,0) + ISNULL(df.inscrito_cad_unico,0) + ISNULL(c.mora_perto_escola,0)) As ic " + 
				" " + 
				"from solicitacao s " + 
				"join candidato c on s.id = c.solicitacao_id " + 
				"join processo_seletivo ps on s.processo_seletivo_id = ps.id " + 
				"Left Join " + 
				"( " + 
				"	Select " + 
				"		dps.id " + 
				"		,Case " + 
				"			When ((ni.sigla='EF')And(Substring(se.descricao,1,1) In ('1','2','3','4','5'))) Then 'EF1 - ' + se.descricao + ' - ' + tu.sigla " + 
				"			When ((ni.sigla='EF')And(Substring(se.descricao,1,1) In ('6','7','8','9'))) Then 'EF2 - ' + se.descricao + ' - ' + tu.sigla " + 
				"			Else ni.sigla + ' - ' + se.descricao + ' - ' + tu.sigla " + 
				"		End As curso " + 
				"		,dps.serie_id " + 
				"		,ni.descricao As nivel " + 
				"		,ni.sigla As siglanivel " + 
				"		,se.descricao As serie " + 
				"		,dps.turno_id " + 
				"		,tu.descricao As turno " + 
				"		,tu.sigla As siglaturno " + 
				"	From detalhes_processo_seletivo dps " + 
				"	Inner Join serie As se On dps.serie_id=se.id " + 
				"	Inner Join nivel As ni On se.nivel_id=ni.id " + 
				"	Inner Join turno As tu On dps.turno_id=tu.id " + 
				" " + 
				")dps On s.detalhes_processo_seletivo_id=dps.id " + 
				"Left Join declaracao_final As df On s.id=df.solicitacao_id " + 
				"Left Join " + 
				"( " + 
				"	Select Count(sbi.id) as qtd, sbi.solicitacao_id " + 
				"	From solicitacao_bolsa_irmao sbi " + 
				"	Group By sbi.solicitacao_id " + 
				")sbi On sbi.solicitacao_id=s.id " + 
				" " + 
				"where ps.id = :id " + 
				"and status in ('PREENCHIDO', 'PENDENCIA_DOCUMENTOS', 'ANALISADO', 'FINALIZADO', 'DOC_ENVIADO', 'EM_PREENCHIMENTO', 'CANCELADO') " + 
				"order by  data_solicitacao " ;
		
		
		String sql =
			"select "+
			"s.id as id, "+
			"protocolo as protocolo, "+ 
			"c.nome as nome, " +
			"s.status as status, "+
	
			"isnull((select sum(salario) from solicitacao_composicao_familiar "+
			"where solicitacao_id = s.id "+
			"group by solicitacao_id ),0) as rendaBruta, "+
	
			"isnull((select sum(valor) from despesa "+
			"where solicitacao_id = s.id "+
			"group by solicitacao_id),0) as despesa, "+
	
			"isnull((select count(solicitacao_id) from solicitacao_composicao_familiar "+
			"where solicitacao_id = s.id "+
			"group by solicitacao_id),0) as totalComponentes, "+
	
			"(select sum(salario) / (select count(solicitacao_id) from solicitacao_composicao_familiar "+
			"where solicitacao_id = s.id "+ 
			"group by solicitacao_id) "+
			"from solicitacao_composicao_familiar "+
			"where solicitacao_id = s.id "+
			"group by solicitacao_id ) as rendaPerCapta "+
	
			"from solicitacao s join candidato c "+
			"on s.id = c.solicitacao_id "+
			"join processo_seletivo ps "+
			"on s.processo_seletivo_id = ps.id "+
			"where ps.id = :id and status in ('PREENCHIDO', 'PENDENCIA_DOCUMENTOS', 'ANALISADO', 'FINALIZADO', 'DOC_ENVIADO') " +
			"order by data_solicitacao ";
		
		Query query = manager.createNativeQuery(sql, "solicitacao.SolicitacoesDTO");
		query.setParameter("id", idProcesso);
		
		return query.getResultList();

	}
	
	@Override
	public List<SolicitacoesConsultaDTO> solicitacaoPorProcessoSeletivoConsultaNativaPorStatus(Long idProcesso, String status, 
			Boolean ehComissao, String nome, String protocolo, String ordem) {
		
		System.out.println("Ordem " + ordem);
		
		
		String fragmento = "";
//		String fragmento = "'EM_PREENCHIMENTO', 'PREENCHIDO', 'PENDENCIA_DOCUMENTOS', 'ANALISADO', 'FINALIZADO', 'DOC_ENVIADO'";
		
		if (!StringUtils.isEmpty(status)) {
			
			if (status.equals("todos")) {
				fragmento = "'PREENCHIDO', 'PENDENCIA_DOCUMENTOS', 'ANALISADO', 'FINALIZADO', 'DOC_ENVIADO', 'EM_PREENCHIMENTO'";
			}else {
				
				fragmento = "'" + status + "'";
			}
			
		}else {
			fragmento = "'PREENCHIDO', 'PENDENCIA_DOCUMENTOS', 'ANALISADO', 'FINALIZADO', 'DOC_ENVIADO', 'EM_PREENCHIMENTO', 'CANCELADO'";
//			if (ehComissao) {
//				fragmento = "'ANALISADO', 'FINALIZADO'";
//			}else {
//				fragmento = "'PREENCHIDO', 'PENDENCIA_DOCUMENTOS', 'ANALISADO', 'FINALIZADO', 'DOC_ENVIADO'";
//				
//			}
			
		}
		
		String fragmento1 = "";
		String fragmento2 = " data_solicitacao ";
		 
		if (!StringUtils.isEmpty(nome)) {
			fragmento1 = fragmento1 + " and c.nome like :nome ";
		}
		
		if (!StringUtils.isEmpty(protocolo)) {
			fragmento1 = fragmento1 + " and protocolo like :protocolo ";
		}
		
		if (!StringUtils.isEmpty(ordem)) {
			if (ordem.equals("Renda Bruta")) {
				fragmento2 = " rendaBruta ";
			}
			if (ordem.equals("IC")) {
				fragmento2 = " ic desc ";
			}
			if (ordem.equals("Nome")) {
				fragmento2 = " nome ";
			}
		}
				
		String sql = 
				"select  " + 
				"	s.id as id, " + 
				"	protocolo as protocolo,   " + 
				"	c.nome as nome, 			 " + 
				"	s.status as status,  " + 
				"	 " + 
				"	isnull( " + 
				"			( " + 
				"				select sum(salario) " + 
				"				from solicitacao_composicao_familiar  " + 
				"				where solicitacao_id = s.id  " + 
				"				group by solicitacao_id " + 
				"			),0 " + 
				"	)As rendaBruta,  " + 
				" " + 
				"	isnull( " + 
				"			(select sum(valor) from despesa  " + 
				"			where solicitacao_id = s.id  " + 
				"			group by solicitacao_id),0 " + 
				"	)As despesa, " + 
				" " + 
				"	isnull( " + 
				"			(select count(solicitacao_id) from solicitacao_composicao_familiar  " + 
				"			where solicitacao_id = s.id  " + 
				"			group by solicitacao_id),0 " + 
				"	)As totalComponentes, " + 
				" " + 
				"	( " + 
				"		select sum(salario) / (select count(solicitacao_id) from solicitacao_composicao_familiar  " + 
				"		where solicitacao_id = s.id   " + 
				"		group by solicitacao_id)  " + 
				"		from solicitacao_composicao_familiar  " + 
				"		where solicitacao_id = s.id  " + 
				"		group by solicitacao_id " + 
				"	)As rendaPerCapta  " + 
				" " + 
				"	,ISNULL(sbi.qtd,0) As qtdIrmaos " + 
				"	,df.inscrito_cad_unico As cadunico " + 
				"	,c.mora_perto_escola As moraperto " + 
				"	,( " + 
				"		ISNULL(sbi.qtd,0) " + 
				"		+ df.inscrito_cad_unico " + 
				"		+ c.mora_perto_escola " + 
				"	) As ic " + 
				" " + 
				"from solicitacao s " + 
				"	join candidato c on s.id = c.solicitacao_id  " + 
				"	join processo_seletivo ps on s.processo_seletivo_id = ps.id " + 
				" " + 
				"	Left Join declaracao_final As df On s.id=df.solicitacao_id " + 
				"	Left Join " + 
				"	( " + 
				"		Select Count(sbi.id) as qtd, sbi.solicitacao_id " + 
				"		From solicitacao_bolsa_irmao sbi " + 
				"		Group By sbi.solicitacao_id " + 
				"	)sbi On sbi.solicitacao_id=s.id " + 
				" " + 
				" " + 
				"where ps.id = :id and status in (" + fragmento + ") " + fragmento1 +
//				"	and	status in ('PREENCHIDO', 'PENDENCIA_DOCUMENTOS', 'ANALISADO', 'FINALIZADO', 'DOC_ENVIADO')  " + 
				"order by " + fragmento2 ;

		Query query = manager.createNativeQuery(sql, "solicitacao.SolicitacoesDTO");
		query.setParameter("id", idProcesso);
		
		if (!StringUtils.isEmpty(nome)) {
			query.setParameter("nome", "%" + nome + "%");
		}
		
		if (!StringUtils.isEmpty(protocolo)) {
			query.setParameter("protocolo", "%" + protocolo + "%");
		}
		
		
		
		return query.getResultList();
		
		
		
//		String sql =
//			"select "+
//			"s.id as id, "+
//			"protocolo as protocolo, "+ 
//			"c.nome as nome, "+
//			
//			"s.status as status, "+
//			"isnull((select sum(salario) from solicitacao_composicao_familiar "+
//			"where solicitacao_id = s.id "+
//			"group by solicitacao_id ),0) as rendaBruta, "+
//	
//			"isnull((select sum(valor) from despesa "+
//			"where solicitacao_id = s.id "+
//			"group by solicitacao_id),0) as despesa, "+
//	
//			"isnull((select count(solicitacao_id) from solicitacao_composicao_familiar "+
//			"where solicitacao_id = s.id "+
//			"group by solicitacao_id),0) as totalComponentes, "+
//	
//			"(select sum(salario) / (select count(solicitacao_id) from solicitacao_composicao_familiar "+
//			"where solicitacao_id = s.id "+ 
//			"group by solicitacao_id) "+
//			"from solicitacao_composicao_familiar "+
//			"where solicitacao_id = s.id "+
//			"group by solicitacao_id ) as rendaPerCapta "+
//	
//			"from solicitacao s join candidato c "+
//			"on s.id = c.solicitacao_id "+
//			"join processo_seletivo ps "+
//			"on s.processo_seletivo_id = ps.id "+
////			"where ps.id = :id and status in ('PREENCHIDO', 'PENDENCIA_DOCUMENTOS', 'ANALISADO', 'FINALIZADO', 'DOC_ENVIADO') " +
//			"where ps.id = :id and status in (" + fragmento + ") " +
//			"order by data_solicitacao ";
//		
//		Query query = manager.createNativeQuery(sql, "solicitacao.SolicitacoesDTO");
//		query.setParameter("id", idProcesso);
		
//		return query.getResultList();

	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<UnidadeTotal> totalPorUnidade() {
		return manager.createNamedQuery("Total.unidade").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnidadeTotal> totalPorUnidade(List<Unidade> unidades) {
		return manager.createNamedQuery("Total.unidade").setParameter("ids", unidades).getResultList();
	}
	
	
	

	
}
