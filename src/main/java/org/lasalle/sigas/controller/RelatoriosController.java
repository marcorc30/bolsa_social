package org.lasalle.sigas.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.filter.RelatorioDeferidoIndeferidoFilter;
import org.lasalle.sigas.security.UsuarioSistema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;


@Controller
@RequestMapping("/relatorios")
public class RelatoriosController {
	
	@Resource(name = "dadosIniciais")
	private DadosIniciais dadosIniciais;	
	
	@Autowired
	private DadosInciaisRepository dadosIniciaisRepository;
	
	
	@RequestMapping(value = "/ficha-inscricao/{id}")
	public ModelAndView relatorioFichaInscricao(@PathVariable("id") Integer id, @AuthenticationPrincipal UsuarioSistema usuario) {
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(Long.valueOf(id));
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("solicitacao_id", id);
		parametros.put("report_irmaos", "/relatorios/relatorio_irmaos.jasper");
		parametros.put("report_responsaveis", "/relatorios/relatorio_responsaveis.jasper");
		parametros.put("report_composicao", "/relatorios/relatorio_componentes.jasper");
		parametros.put("report_bens", "/relatorios/relatorio_BensMoveis.jasper");
		parametros.put("report_despesas", "/relatorios/relatorio_Inf-Financ-Despesas.jasper");
		parametros.put("report_renda_agregada", "/relatorios/relatorio_Inf-Financ-RendaAgregada.jasper");
		parametros.put("report_beneficio", "/relatorios/relatorio_Inf-Financ-Beneficios.jasper");
		
//		if (dadosIniciais.getUsuario().getId() != usuario.getUsuario().getId()) {
//			return new ModelAndView("AcessoIndevido");
//		}
		
		if (!dadosIniciais.getUsuario().getId().equals(usuario.getUsuario().getId())) {
			return new ModelAndView("AcessoIndevido");
		}
		
		return new ModelAndView("relatorio_ficha_inscricao", parametros);
		
		
	}
	
	
	@RequestMapping(value = "/ficha-inscricao-cibs/{id}")
	public ModelAndView relatorioFichaInscricaoCibs(@PathVariable("id") Integer id) {
		
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("solicitacao_id", id);
		parametros.put("report_irmaos", "/relatorios/relatorio_irmaos.jasper");
		parametros.put("report_responsaveis", "/relatorios/relatorio_responsaveis.jasper");
		parametros.put("report_composicao", "/relatorios/relatorio_componentes.jasper");
		parametros.put("report_bens", "/relatorios/relatorio_BensMoveis.jasper");
		parametros.put("report_despesas", "/relatorios/relatorio_Inf-Financ-Despesas.jasper");
		parametros.put("report_renda_agregada", "/relatorios/relatorio_Inf-Financ-RendaAgregada.jasper");
		parametros.put("report_beneficio", "/relatorios/relatorio_Inf-Financ-Beneficios.jasper");
		
		
		
		return new ModelAndView("relatorio_ficha_inscricao", parametros);
		
		
	}
	
	
	
	
	
	@RequestMapping(value = "/ficha-cibs/{id}")
	public ModelAndView relatorioFichaParecerCIBS(@PathVariable("id") Integer id) {
		
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("solicitacaoid", id);
		parametros.put("relatorio_cibs", "/relatorios/relatorio_cibs.jasper");
		
		
		return new ModelAndView("relatorio_ParecerComissao", parametros);
		
		
	}
	
	@RequestMapping(value = "/relatorio-deferido-indeferido")
	public ModelAndView relatorioDeferidosIndeferidosPorRenda(
			RelatorioDeferidoIndeferidoFilter relatorioDeferidoIndeferidoFilter, @AuthenticationPrincipal UsuarioSistema usuario){
		
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("processo_seletivo_id", relatorioDeferidoIndeferidoFilter.getIdProcessoSeletivo());
		parametros.put("concessao", relatorioDeferidoIndeferidoFilter.getConcessao());
		parametros.put("nomeusuario", usuario.getUsuario().getNome());
		parametros.put("relatorio_ListDefIndefPorRendaPerCapitaTotalizadores", "/relatorios/relatorio_ListDefIndefPorRendaPerCapitaTotalizadores.jasper");
		
		return new ModelAndView("relatorio_ListDefIndefPorRendaPerCapita", parametros);
	}
	
	@RequestMapping(value = "/relatorio-lista-publicacao")
	public ModelAndView relatorioListaDePublicacao(
			RelatorioDeferidoIndeferidoFilter relatorioDeferidoIndeferidoFilter, @AuthenticationPrincipal UsuarioSistema usuario){
		
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("processo_seletivo_id", relatorioDeferidoIndeferidoFilter.getIdProcessoSeletivo());
		parametros.put("concessao", relatorioDeferidoIndeferidoFilter.getConcessao());
		parametros.put("nomeusuario", usuario.getUsuario().getNome());
		parametros.put("reportDetalhe", "/relatorios/relatorio_ListaPublicacaoSolicitacao.jasper");
		
		
		return new ModelAndView("relatorio_ListaPublicacao", parametros);
	}
	

	@RequestMapping(value = "/relatorio-inscritos")
	public ModelAndView relatorioInscritos(
			RelatorioDeferidoIndeferidoFilter relatorioDeferidoIndeferidoFilter, @AuthenticationPrincipal UsuarioSistema usuario){
		
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("processo_seletivo_id", relatorioDeferidoIndeferidoFilter.getIdProcessoSeletivo());
		parametros.put("nomeusuario", usuario.getUsuario().getNome());
		
		return new ModelAndView("relatorio_ListInscritos", parametros);
	}
	
	@RequestMapping(value = "/relatorio-acompanhamento-consolidado")
	public ModelAndView relatorioAcompanhamentoConsolidado(
			RelatorioDeferidoIndeferidoFilter relatorioIndeferidoFilter, @AuthenticationPrincipal UsuarioSistema usuario) {
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		parametros.put("processo_seletivo_id", relatorioIndeferidoFilter.getIdProcessoSeletivo());
		parametros.put("nomeusuario", usuario.getUsuario().getNome());
		parametros.put("relatorio_AcompanhamentoDetalhe", "/relatorios/relatorio_AcompanhamentoDetalhe.jasper");
		
		
		return new ModelAndView("relatorio_AcompanhamentoConsolidado", parametros);
		
		
	}
	

}
