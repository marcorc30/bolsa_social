package org.lasalle.sigas.repository.helper.detalhesProcessoSeletivo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.lasalle.sigas.dto.DetalhesProcessoSeletivoDTO;
import org.lasalle.sigas.dto.UnidadeTotal;

public class DetalhesProcessoSeletivoRepositoryImpl implements DetalhesProcessoSeletivoRepositoryQueries{

	@PersistenceContext
	EntityManager manager;
	

	
	@Override
	public List<DetalhesProcessoSeletivoDTO> listarDetalhes(Long idProcesso) {
		
		
		String sql3 = "select " + 
				" detalhesId = det.id" + 
				" ,psel.id" + 
				" ,curso = Previsto.Ciclo" + 
				" ,serie = Previsto.Ano_Serie" + 
				" ,turno = Previsto.Turno" + 
				" ,'prevista_100' = isnull((Previsto.quantidade_bolsas_100),0)" + 
				" ,'real_100' = isnull((Realizado_100.Qtd_Bolsa_100),0)" + 
				" ,'prevista_50'  = isnull((Previsto.quantidade_bolsas_50),0)" + 
				" ,'real_50'  = isnull((Realizado_50.Qtd_Bolsa_50),0)" + 
				"" + 
				" from processo_seletivo	as psel" + 
				" inner join tipo_processo_seletivo as tpsel  on psel.tipo_processo=tpsel.id" + 
				" inner join tipo_edital	as tedit			on psel.tipo_edital_id=tedit.id" + 
				" inner join unidade		as unid				on psel.unidade_id=unid.id" + 
				" inner join sede								on sede.id=unid.sede_id" + 
				" inner join detalhes_processo_seletivo as det on psel.id=det.processo_seletivo_id" + 
				"" + 
				" left join (" + 
				"			select" + 
				"			 detalhes_processo_seletivo.id" + 
				"			,detalhes_processo_seletivo.processo_seletivo_id" + 
				"			,detalhes_processo_seletivo.serie_id" + 
				"			,Ciclo		= nivel.descricao" + 
				"			,Ano_Serie	= serie.descricao" + 
				"			,detalhes_processo_seletivo.turno_id" + 
				"			,Turno		= turno.descricao" + 
				"			,detalhes_processo_seletivo.quantidade_bolsas_100" + 
				"			,detalhes_processo_seletivo.quantidade_bolsas_50" + 
				"" + 
				"			from detalhes_processo_seletivo" + 
				"			inner join serie   on serie.id=detalhes_processo_seletivo.serie_id" + 
				"			inner join nivel   on nivel.id=serie.nivel_id" + 
				"			inner join turno   on turno.id=detalhes_processo_seletivo.turno_id" + 
				"" + 
				"		  ) as Previsto on Previsto.processo_seletivo_id = psel.id and Previsto.id=det.id" + 
				"" + 
				" left join (" + 
				"			select distinct" + 
				"			 solicitacao.unidade_id" + 
				"			,solicitacao.processo_seletivo_id" + 
				"			,solicitacao.detalhes_processo_seletivo_id" + 
				"			,Parecer_Cibs.concessao" + 
				"			,Parecer_Cibs.percentual_id" + 
				"			,Parecer_Cad.percentual" + 
				"			,Qtd_Bolsa_100   = count(Parecer_Cibs.percentual_id)" + 
				"			" + 
				"			from solicitacao" + 
				"			left  join analise_tecnica_financeira as Parecer_Social		on Parecer_Social.solicitacao_id=solicitacao.id" + 
				"			left  join analise_cibs				  as Parecer_Cibs		on Parecer_Cibs.solicitacao_id=solicitacao.id" + 
				"			inner join percentual_parecer		  as Parecer_Cad		on Parecer_Cad.id=Parecer_Cibs.percentual_id" + 
				"			where status = 'FINALIZADO' " + 
				"			and Parecer_Cibs.percentual_id in  (2)" + 
				"			group by " + 
				"			 solicitacao.unidade_id" + 
				"			,solicitacao.processo_seletivo_id" + 
				"			,solicitacao.detalhes_processo_seletivo_id" + 
				"			,Parecer_Cibs.concessao" + 
				"			,Parecer_Cibs.percentual_id" + 
				"			,Parecer_Cad.percentual" + 
				"" + 
				"		  ) as Realizado_100 on Realizado_100.processo_seletivo_id = psel.id" + 
				"						and Realizado_100.detalhes_processo_seletivo_id = det.id" + 
				"" + 
				" left join (" + 
				"			select distinct" + 
				"			 solicitacao.unidade_id" + 
				"			,solicitacao.processo_seletivo_id" + 
				"			,solicitacao.detalhes_processo_seletivo_id" + 
				"			,Parecer_Cibs.concessao" + 
				"			,Parecer_Cibs.percentual_id" + 
				"			,Parecer_Cad.percentual" + 
				"			,Qtd_Bolsa_50   = count(Parecer_Cibs.percentual_id)" + 
				"			" + 
				"			from solicitacao" + 
				"			left  join analise_tecnica_financeira as Parecer_Social		on Parecer_Social.solicitacao_id=solicitacao.id" + 
				"			left  join analise_cibs				  as Parecer_Cibs		on Parecer_Cibs.solicitacao_id=solicitacao.id" + 
				"			inner join percentual_parecer		  as Parecer_Cad		on Parecer_Cad.id=Parecer_Cibs.percentual_id" + 
				"			where status = 'FINALIZADO' " + 
				"			and Parecer_Cibs.percentual_id in  (1)" + 
				"			group by " + 
				"			 solicitacao.unidade_id" + 
				"			,solicitacao.processo_seletivo_id" + 
				"			,solicitacao.detalhes_processo_seletivo_id" + 
				"			,Parecer_Cibs.concessao" + 
				"			,Parecer_Cibs.percentual_id" + 
				"			,Parecer_Cad.percentual" + 
				"" + 
				"		  ) as Realizado_50 on Realizado_50.processo_seletivo_id = psel.id" + 
				"						and Realizado_50.detalhes_processo_seletivo_id = det.id" + 
				"" + 
				"" + 
				" where psel.id = :idProcesso "  + 
				"" + 
				" group by " + 
				" det.id" + 
				" ,psel.id" + 
				" ,Previsto.Ciclo" + 
				" ,Previsto.Ano_Serie" + 
				" ,Previsto.Turno" + 
				" ,Previsto.quantidade_bolsas_100" + 
				" ,Realizado_100.Qtd_Bolsa_100" + 
				" ,Previsto.quantidade_bolsas_50" + 
				" ,Realizado_50.Qtd_Bolsa_50" + 
				"" + 
				" order by " + 
				" Previsto.Ciclo" + 
				" ,Previsto.Ano_Serie" + 
				" ,Previsto.Turno";
		
		
		String sql2 = "select " + 
				" detalhesId = det.id" + 
				" ,psel.id" + 
				" ,curso = Previsto.Ciclo" + 
				" ,serie = Previsto.Ano_Serie" + 
				" ,turno = Previsto.Turno" + 
				" ,'prevista_100' = isnull((Previsto.quantidade_bolsas_100),0)" + 
				" ,'real_100' = isnull((Realizado_100.Qtd_Bolsa_100),0)" + 
				" ,'prevista_50'  = isnull((Previsto.quantidade_bolsas_50),0)" + 
				" ,'real_50'  = isnull((Realizado_50.Qtd_Bolsa_50),0)" + 
				" from processo_seletivo	as psel" + 
				" inner join tipo_processo_seletivo as tpsel  on psel.tipo_processo=tpsel.id" + 
				" inner join tipo_edital	as tedit			on psel.tipo_edital_id=tedit.id" + 
				" inner join unidade		as unid				on psel.unidade_id=unid.id" + 
				" inner join sede								on sede.id=unid.sede_id" + 
				" inner join detalhes_processo_seletivo as det on psel.id=det.processo_seletivo_id" + 
				" left join (" + 
				"			select" + 
				"			 detalhes_processo_seletivo.id" + 
				"			,detalhes_processo_seletivo.processo_seletivo_id" + 
				"			,detalhes_processo_seletivo.serie_id" + 
				"			,Ciclo		= nivel.descricao" + 
				"			,Ano_Serie	= serie.descricao" + 
				"			,detalhes_processo_seletivo.turno_id" + 
				"			,Turno		= turno.descricao" + 
				"			,detalhes_processo_seletivo.quantidade_bolsas_100" + 
				"			,detalhes_processo_seletivo.quantidade_bolsas_50" + 
				"" + 
				"			from detalhes_processo_seletivo" + 
				"			inner join serie   on serie.id=detalhes_processo_seletivo.serie_id" + 
				"			inner join nivel   on nivel.id=serie.nivel_id" + 
				"			inner join turno   on turno.id=detalhes_processo_seletivo.turno_id" + 
				"" + 
				"		  ) as Previsto on Previsto.processo_seletivo_id = psel.id and Previsto.id=det.id" + 
				"" + 
				" left join (" + 
				"			select distinct" + 
				"			 solicitacao.unidade_id" + 
				"			,solicitacao.processo_seletivo_id" + 
				"			,solicitacao.detalhes_processo_seletivo_id" + 
				"			,Parecer_Cibs.concessao" + 
				"			,Parecer_Cibs.percentual_id" + 
				"			,Parecer_Cad.percentual" + 
				"			,Qtd_Bolsa_50   = case when Parecer_Cibs.percentual_id = 1 Then 1 else 0 end" + 
				"			,Qtd_Bolsa_100  = case when Parecer_Cibs.percentual_id = 2 Then 1 else 0 end" + 
				"			" + 
				"			from solicitacao" + 
				"			left  join analise_tecnica_financeira as Parecer_Social		on Parecer_Social.solicitacao_id=solicitacao.id" + 
				"			left  join analise_cibs				  as Parecer_Cibs		on Parecer_Cibs.solicitacao_id=solicitacao.id" + 
				"			inner join percentual_parecer		  as Parecer_Cad		on Parecer_Cad.id=Parecer_Cibs.percentual_id" + 
				"			where status = 'FINALIZADO' /*and solicitacao.processo_seletivo_id=2103*/" + 
				"			and Parecer_Cibs.percentual_id in  (2)" + 
				"" + 
				"		  ) as Realizado_100 on Realizado_100.processo_seletivo_id = psel.id" + 
				"						and Realizado_100.detalhes_processo_seletivo_id = det.id" + 
				"" + 
				" left join (" + 
				"			select distinct" + 
				"			 solicitacao.unidade_id" + 
				"			,solicitacao.processo_seletivo_id" + 
				"			,solicitacao.detalhes_processo_seletivo_id" + 
				"			,Parecer_Cibs.concessao" + 
				"			,Parecer_Cibs.percentual_id" + 
				"			,Parecer_Cad.percentual" + 
				"			,Qtd_Bolsa_50   = case when Parecer_Cibs.percentual_id = 1 Then 1 else 0 end" + 
				"			,Qtd_Bolsa_100  = case when Parecer_Cibs.percentual_id = 2 Then 1 else 0 end" + 
				"			" + 
				"			from solicitacao" + 
				"			left  join analise_tecnica_financeira as Parecer_Social		on Parecer_Social.solicitacao_id=solicitacao.id" + 
				"			left  join analise_cibs				  as Parecer_Cibs		on Parecer_Cibs.solicitacao_id=solicitacao.id" + 
				"			inner join percentual_parecer		  as Parecer_Cad		on Parecer_Cad.id=Parecer_Cibs.percentual_id" + 
				"			where status = 'FINALIZADO' /*and solicitacao.processo_seletivo_id=2103*/" + 
				"			and Parecer_Cibs.percentual_id in  (1)" + 
				"" + 
				"		  ) as Realizado_50 on Realizado_50.processo_seletivo_id = psel.id" + 
				"						and Realizado_50.detalhes_processo_seletivo_id = det.id" + 
				"" + 
				"" + 
				" where psel.id= :idProcesso " + 
				"" + 
				" group by " + 
				" det.id" + 
				" ,psel.id" + 
				" ,Previsto.Ciclo" + 
				" ,Previsto.Ano_Serie" + 
				" ,Previsto.Turno" + 
				" ,Previsto.quantidade_bolsas_100" + 
				" ,Realizado_100.Qtd_Bolsa_100" + 
				" ,Previsto.quantidade_bolsas_50" + 
				" ,Realizado_50.Qtd_Bolsa_50" + 
				"" + 
				" order by " + 
				" Previsto.Ciclo" + 
				" ,Previsto.Ano_Serie" + 
				" ,Previsto.Turno"; 
		
		String sql = "select  distinct " + 
				" previsto.id as detalhesId "+
				",psel.id  as id " + 
				",Previsto.Ciclo as curso " + 
				",Previsto.Ano_Serie as serie " + 
				",Previsto.Turno as turno " + 
				" " + 
				",'prevista_100' = isnull((Previsto.quantidade_bolsas_100),0) " + 
				",'real_100' = isnull((Realizado.Qtd_Bolsa_100),0) " + 
				" " + 
				",'prevista_50'  = isnull((Previsto.quantidade_bolsas_50),0) " + 
				",'real_50'  = isnull((Realizado.Qtd_Bolsa_50),0) " + 
				" " + 
				"from processo_seletivo	as psel " + 
				"inner join tipo_processo_seletivo as tpsel  on psel.tipo_processo=tpsel.id " + 
				"inner join tipo_edital	as tedit			on psel.tipo_edital_id=tedit.id " + 
				"inner join unidade		as unid				on psel.unidade_id=unid.id " + 
				"inner join sede								on sede.id=unid.sede_id " + 
				" " + 
				"left join ( " + 
				"			select" + 
				"			 detalhes_processo_seletivo.id" + 
				"			,detalhes_processo_seletivo.processo_seletivo_id" + 
				"			,detalhes_processo_seletivo.serie_id" + 
				"			,Ciclo		= nivel.descricao" + 
				"			,Ano_Serie	= serie.descricao" + 
				"			,detalhes_processo_seletivo.turno_id" + 
				"			,Turno		= turno.descricao" + 
				"			,detalhes_processo_seletivo.quantidade_bolsas_100" + 
				"			,detalhes_processo_seletivo.quantidade_bolsas_50" + 
				"" + 
				"			from detalhes_processo_seletivo" + 
				"			inner join serie   on serie.id=detalhes_processo_seletivo.serie_id" + 
				"			inner join nivel   on nivel.id=serie.nivel_id" + 
				"			inner join turno   on turno.id=detalhes_processo_seletivo.turno_id" + 
				"" + 
				"		  ) as Previsto on Previsto.processo_seletivo_id = psel.id " + 
				"" + 
				"left join ( " + 
				"			select distinct" + 
				"			 solicitacao.unidade_id" + 
				"			,solicitacao.processo_seletivo_id" + 
				"			,solicitacao.detalhes_processo_seletivo_id" + 
				"			,Parecer_Cibs.concessao" + 
				"			,Parecer_Cibs.percentual_id" + 
				"			,Parecer_Cad.percentual" + 
				"			,Qtd_Bolsa_50   = case when Parecer_Cibs.percentual_id = 1 Then 1 else 0 end" + 
				"			,Qtd_Bolsa_100  = case when Parecer_Cibs.percentual_id = 2 Then 1 else 0 end" + 
				"" + 
				"			from solicitacao" + 
				"			left  join analise_tecnica_financeira as Parecer_Social		on Parecer_Social.solicitacao_id=solicitacao.id" + 
				"			left  join analise_cibs				  as Parecer_Cibs		on Parecer_Cibs.solicitacao_id=solicitacao.id" + 
				"			inner join percentual_parecer		  as Parecer_Cad		on Parecer_Cad.id=Parecer_Cibs.percentual_id" + 
				"			where status = 'FINALIZADO'" + 
				"" + 
				"		  ) as Realizado on realizado.processo_seletivo_id = Previsto.processo_seletivo_id" + 
				"						and realizado.detalhes_processo_seletivo_id = Previsto.id " + 
				"" + 
				"where psel.id= :idProcesso " + 
				"" + 
				"group by " + 
				" previsto.id " +
				",psel.id " + 
				",Previsto.Ciclo " + 
				",Previsto.Ano_Serie " + 
				",Previsto.Turno " + 
				",Previsto.quantidade_bolsas_100 " + 
				",Realizado.Qtd_Bolsa_100 " + 
				",Previsto.quantidade_bolsas_50 " + 
				",Realizado.Qtd_Bolsa_50 " + 
				"" + 
				"order by " + 
				" Previsto.Ciclo " + 
				",Previsto.Ano_Serie "; 
		
		Query query = manager.createNativeQuery(sql3, "detalhes.DetalhesProcessoSeletivoDTO");
		query.setParameter("idProcesso", idProcesso);
				
		return query.getResultList();
	}

	@Override
	public Integer totalInscricoes(Integer ano, List<Long> unidades) {
		String sql = " Select " + 
				"	Sum(urq.qtdinscricoes) As qtd_inscricoes " + 
				"From uvw_relatorio_quantitativoporprocsel As urq " + 
				"Where urq.anovigencia = :ano and idunidadeeducativa in (:unidade)";
		
		Query query = manager.createNativeQuery(sql);
		query.setParameter("ano", ano);
		query.setParameter("unidade", unidades);
		return (Integer) query.getSingleResult();
	}

	
	@Override
	public Integer total100(Integer ano, List<Long> unidades) {
		String sql = "Select  " + 
				"	Sum(urq.qtdreal100) As qtd_bolsa100 " + 
				"From uvw_relatorio_quantitativoporprocsel As urq " + 
				"Where urq.anovigencia = :ano and idunidadeeducativa in (:unidade)"; 
		
		Query query = manager.createNativeQuery(sql);
		query.setParameter("ano", ano);
		query.setParameter("unidade", unidades);
		return (Integer) query.getSingleResult();
	}
	
	@Override
	public Integer total50(Integer ano, List<Long> unidades) {
		String sql = "Select  " + 
				"	Sum(urq.qtdreal50) As qtd_bolsa50 " + 
				"From uvw_relatorio_quantitativoporprocsel As urq " + 
				"Where urq.anovigencia = :ano and idunidadeeducativa in (:unidade)";
		
		Query query = manager.createNativeQuery(sql);
		query.setParameter("ano", ano);
		query.setParameter("unidade", unidades);
		return (Integer) query.getSingleResult();
	}

	
	@Override
	public Integer totalGratuidade(Integer ano, List<Long> unidades) {
		String sql = "Select " + 
				"	(Sum(urq.qtdreal100) + Sum(urq.qtdreal50)) As qtd_bolsatotal " + 
				"From uvw_relatorio_quantitativoporprocsel As urq " + 
				"Where urq.anovigencia = :ano and idunidadeeducativa in (:unidade)" ; 
		
		Query query = manager.createNativeQuery(sql);
		query.setParameter("ano", ano);
		query.setParameter("unidade", unidades);
		return (Integer) query.getSingleResult();
	}

	@Override
	public List<Integer> anos() {
		String sql = "select distinct ano from processo_seletivo";
		Query query = manager.createNativeQuery(sql);
		return query.getResultList();
	}

	
	
	
}
