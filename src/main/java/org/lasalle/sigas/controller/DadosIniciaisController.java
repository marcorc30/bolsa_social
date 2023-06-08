package org.lasalle.sigas.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.lasalle.sigas.dto.UnidadeTotal;
import org.lasalle.sigas.model.AnaliseCIBS;
import org.lasalle.sigas.model.Candidato;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.ProcessoSeletivo;
import org.lasalle.sigas.model.StatusSolicitacao;
import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.repository.AnaliseCIBSRepository;
import org.lasalle.sigas.repository.CandidatoRepository;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.ProcessoSeletivoRepository;
import org.lasalle.sigas.repository.UnidadeRepository;
import org.lasalle.sigas.security.UsuarioSistema;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/solicitacao")
public class DadosIniciaisController {

	
	@Resource(name = "dadosIniciais")
	private DadosIniciais dadosIniciais;
		
	@Autowired
	private UnidadeRepository unidadeRepository;
	
	@Autowired
	private ProcessoSeletivoRepository processoSeletivoRepository;
	
	@Autowired
	private DadosIniciaisService dadosIniciaisService;
	
	@Autowired
	private DadosInciaisRepository dadosIniciaisRepository;
	
	@Autowired
	private CandidatoRepository candidatoRepository;
	
	@Autowired
	private AnaliseCIBSRepository analiseCIBSRepository;
	
	@RequestMapping("/pesquisar")
	public ModelAndView listarCandidatos(@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
	
		ModelAndView mv = new ModelAndView("solicitacao/PesquisaSolicitacao");
		
		List<DadosIniciais> solicitacoes = dadosIniciaisRepository.solicitacaoPorIdUsuario(usuarioSistema.getUsuario().getId());  
//		List<DadosIniciais> solicitacoes = dadosIniciaisRepository.findByUsuarioId(usuarioSistema.getUsuario().getId());
		
		mv.addObject("solicitacoes", solicitacoes);
//		
		
		return mv;
	}
	

	@RequestMapping("/alterarStatus/{id}")
	public ModelAndView solicitarAlteracaoStatusSolicitacao(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("solicitacao/AlterarSolicitacao");
		Candidato candidato = candidatoRepository.findByDadosIniciaisId(id).get(0);
		
		mv.addObject("dadosIniciais", dadosIniciaisRepository.findById(id));
		mv.addObject("candidato", candidato);
		mv.addObject("statusSolicitacao", StatusSolicitacao.values());
		
		return mv;
	}
	
	
	@RequestMapping("/visualizar_motivo_cancelamento/{id}")
	public ModelAndView visualizarMotivoAlteracao(@PathVariable("id") Long id) {
		
		ModelAndView mv = new ModelAndView("solicitacao/VisualizarAlteracaoStatusSolicitacao");
		Candidato candidato = candidatoRepository.findByDadosIniciaisId(id).get(0);
		
		DadosIniciais dadosIniciaisBanco = dadosIniciaisRepository.findById(id);
		mv.addObject("dadosIniciais", dadosIniciaisRepository.findById(id));
		mv.addObject("candidato", candidato);
		mv.addObject("statusSolicitacao", StatusSolicitacao.values());
		mv.addObject("usuario", dadosIniciaisBanco.getUsuarioAlteracaoStatus());
		mv.addObject("data", dadosIniciaisBanco.getDataAlteracaoStatus());
		
		mv.addObject("texto", dadosIniciaisBanco.getMotivoAlteracaoStatus());
		
		return mv;
		

	}
	
	@RequestMapping("/visualizar_motivo_cancelamento_solicitante/{id}")
	public ModelAndView visualizarMotivoAlteracaoSolicitante(@PathVariable("id") Long id) {
		
		ModelAndView mv = new ModelAndView("solicitacao/VisualizarAlteracaoStatusSolicitacaoSolicitante");
		Candidato candidato = candidatoRepository.findByDadosIniciaisId(id).get(0);
		
		DadosIniciais dadosIniciaisBanco = dadosIniciaisRepository.findById(id);
		mv.addObject("dadosIniciais", dadosIniciaisRepository.findById(id));
		mv.addObject("candidato", candidato);
		mv.addObject("statusSolicitacao", StatusSolicitacao.values());
		
		mv.addObject("texto", dadosIniciaisBanco.getMotivoAlteracaoStatus());
		
		return mv;
		

	}
	
	@RequestMapping("/visualizar-resultado/{id}")
	public ModelAndView visualizarResultado(@PathVariable("id") Long id, @AuthenticationPrincipal UsuarioSistema usuario) {
		
		AnaliseCIBS analise = analiseCIBSRepository.findById(id).get();
		
		
		
		String deferido = "Através da análise do perfil socioeconômico realizado, "
				+ "verificou-se o preenchimento dos critérios para a concessão de bolsa de estudos no percentual de " + analise.getPercentual().getPercentual() +  ". "
				+ "Entrar em contato com a Unidade educativa para a efetivação da matrícula.";

		String indeferido = "Seu processo foi analisado. Entre em contato com a unidade educativa para orientação.";
		
		ModelAndView mv = new ModelAndView("solicitacao/VisualizarResultado");
		Candidato candidato = candidatoRepository.findByDadosIniciaisId(id).get(0);
		
		DadosIniciais dadosIniciaisBanco = dadosIniciaisRepository.findById(id);
		mv.addObject("dadosIniciais", dadosIniciaisRepository.findById(id));
		mv.addObject("candidato", candidato);
		mv.addObject("resultado", analise);
		
		if (analise.getPercentual().getPercentual().equals("Indeferido")) {
			mv.addObject("texto", indeferido);
		}else {
			mv.addObject("texto", deferido);
		}
		
		
		if (!dadosIniciaisBanco.getUsuario().getId().equals(usuario.getUsuario().getId())) {
			return new ModelAndView("AcessoIndevido");
		}
		
		
//		mv.addObject("texto", dadosIniciaisBanco.getMotivoAlteracaoStatus());
		
		return mv;
		

	}

	
	
	
	
	@RequestMapping(value = "/alterarStatus", method = RequestMethod.POST)
	public ModelAndView alteracaoStatusSolicitacao(DadosIniciais dadosIniciais, String novo_status, 
			String motivo, BindingResult result,  RedirectAttributes attributes, @AuthenticationPrincipal UsuarioSistema usuario) {
		
		if (StringUtils.isEmpty(novo_status)) {
			result.reject("Favor informar o status.");
			return new ModelAndView("redirect:/solicitacao/alterarStatus/" + dadosIniciais.getId());
		}
		
		if (StringUtils.isEmpty(motivo)) {
			result.reject("Favor preencher o motivo.");
			return new ModelAndView("redirect:/solicitacao/alterarStatus/" + dadosIniciais.getId());
		}

		
		
		DadosIniciais dadosIniciaisBanco = dadosIniciaisRepository.findById(dadosIniciais.getId());
		dadosIniciaisBanco.setStatusAnterior(dadosIniciaisBanco.getStatus());
		dadosIniciaisBanco.setStatus(StatusSolicitacao.valueOf(novo_status));
		dadosIniciaisBanco.setMotivoAlteracaoStatus(motivo);
		dadosIniciaisBanco.setDataAlteracaoStatus(LocalDateTime.now());
		dadosIniciaisBanco.setUsuarioAlteracaoStatus(usuario.getUsuario().getEmail());
		
		
		attributes.addFlashAttribute("mensagem", "Status Alterado com sucesso.");
		
		dadosIniciaisService.salvar(dadosIniciaisBanco);
				
		return new ModelAndView("redirect:/solicitacao/alterarStatus/" + dadosIniciais.getId());
	}
	
	
	@RequestMapping("/dadosIniciaisRetorno")
	public ModelAndView dadosIniciaisRetorno(@ModelAttribute("dadosIniciais") DadosIniciais dadosIniciais) {
		
		DadosIniciais dadosIniciaisBanco = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciaisService.apagar(dadosIniciaisBanco);
		
		
		ModelAndView mv = new ModelAndView("solicitacao/DadosIniciais");
		List<Unidade> listaUnidades = unidadeRepository.findAll();
		Set<String> ufs = new HashSet<>();
		
		for (Unidade unidade : listaUnidades) {
			ufs.add(unidade.getUf());
		}
		
		
		
		List<String> ufsString = new ArrayList<String>(ufs);
		
		Collections.sort(ufsString);
		
		mv.addObject("lista", ufsString);
	
		return mv;
	}
	
	
	@RequestMapping("/dadosIniciais")
	public ModelAndView dadosIniciais(@ModelAttribute("dadosIniciais") DadosIniciais dadosIniciais) {
		
//		DadosIniciais dadosIniciaisBanco = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
//		dadosIniciaisService.apagar(dadosIniciaisBanco);
		
		
		ModelAndView mv = new ModelAndView("solicitacao/DadosIniciais");
		List<Unidade> listaUnidades = unidadeRepository.findAll();
		Set<String> ufs = new HashSet<>();
		
		for (Unidade unidade : listaUnidades) {
			ufs.add(unidade.getUf());
		}
		
		
		
		List<String> ufsString = new ArrayList<String>(ufs);
		
		Collections.sort(ufsString);
		
		mv.addObject("lista", ufsString);
	
		return mv;
	}

	
	@RequestMapping(value="/dadosIniciais", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid @ModelAttribute("dadosIniciais") DadosIniciais dadosIniciais,
									BindingResult result, RedirectAttributes attributes,
									 @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		
		if (result.hasErrors()) {
			return dadosIniciais(dadosIniciais);
		}
		
		
		dadosIniciais.setUsuario(usuarioSistema.getUsuario());
		dadosIniciais.setAno(2023l);
		dadosIniciais.setConcluido(false);
		dadosIniciais.setEtapa1(true);
		dadosIniciais.setEtapa2(false);
		dadosIniciais.setEtapa3(false);
		dadosIniciais.setEtapa4(false);
		dadosIniciais.setEtapa5(false);
		dadosIniciais.setEtapa6(false);
		dadosIniciais.setEtapa7(false);
		dadosIniciais.setEtapa8(false);
		dadosIniciais.setEtapa9(false);
		dadosIniciais.setEtapa10(false);
		dadosIniciais.setEtapa11(false);
		dadosIniciais.setEtapa12(false);
		dadosIniciais.setEtapa13(false);
		dadosIniciais.setEtapa14(false);
		dadosIniciais.setStatus(StatusSolicitacao.EM_PREENCHIMENTO);
		
		
		
		
		dadosIniciais = dadosIniciaisService.salvar(dadosIniciais);
		
		this.dadosIniciais.setAno(dadosIniciais.getAno());
		this.dadosIniciais.setProtocolo(dadosIniciais.getProtocolo());
		this.dadosIniciais.setId(dadosIniciais.getId());
		this.dadosIniciais.setDetalhesProcessoSeletivo(dadosIniciais.getDetalhesProcessoSeletivo());
		this.dadosIniciais.setProcessoSeletivo(dadosIniciais.getProcessoSeletivo());
		this.dadosIniciais.setUnidade(dadosIniciais.getUnidade());
		

//		this.dadosIniciais.setCandidatos(new ArrayList<Candidato>());
		
		ProcessoSeletivo processoSeletivo = processoSeletivoRepository.findById(dadosIniciais.getProcessoSeletivo().getId());
		attributes.addFlashAttribute("dadosIniciais", this.dadosIniciais);
		attributes.addFlashAttribute("processoSeletivo", processoSeletivo);
		ModelAndView mv = new ModelAndView("redirect:/edital");
		return mv;
	}	
	
	
	@RequestMapping(value="/edital",  method = RequestMethod.GET)
	public ModelAndView edital() {
		
		return new ModelAndView("solicitacao/Edital");
	}
	
	@RequestMapping("/alterar/{id}")
	public ModelAndView alterarDadosIniciais(@PathVariable("id") Long id, @AuthenticationPrincipal UsuarioSistema usuario) {      
		ModelAndView mv = new ModelAndView("timeline/TimeLineAlteracao");
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(id);
		
		List<Candidato> candidatos = candidatoRepository.findByDadosIniciaisId(id);
		
		for (Candidato candidato : candidatos) {
			this.dadosIniciais.setCandidato(candidato.getNome());
		}
		
		this.dadosIniciais.setAno(dadosIniciais.getAno());
		this.dadosIniciais.setProtocolo(dadosIniciais.getProtocolo());
		this.dadosIniciais.setId(dadosIniciais.getId());
		this.dadosIniciais.setDetalhesProcessoSeletivo(dadosIniciais.getDetalhesProcessoSeletivo());
		this.dadosIniciais.setProcessoSeletivo(dadosIniciais.getProcessoSeletivo());
		this.dadosIniciais.setUnidade(dadosIniciais.getUnidade());
		
		mv.addObject("dadosIniciais", dadosIniciais);
		
		if (!dadosIniciais.getUsuario().getId().equals(usuario.getUsuario().getId())) {
			return new ModelAndView("AcessoIndevido");
		}
		
		return mv;
	}
	
	
	@RequestMapping("/visualizar-pendencia/{id}")
	public ModelAndView visualizarDadosIniciais(@PathVariable("id") Long id) {      
		ModelAndView mv = new ModelAndView("timeline/TimeLinePendencias");
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(id);
		
		List<Candidato> candidatos = candidatoRepository.findByDadosIniciaisId(id);
		
		for (Candidato candidato : candidatos) {
			this.dadosIniciais.setCandidato(candidato.getNome());
		}
		
		this.dadosIniciais.setAno(dadosIniciais.getAno());
		this.dadosIniciais.setProtocolo(dadosIniciais.getProtocolo());
		this.dadosIniciais.setId(dadosIniciais.getId());
		this.dadosIniciais.setDetalhesProcessoSeletivo(dadosIniciais.getDetalhesProcessoSeletivo());
		this.dadosIniciais.setProcessoSeletivo(dadosIniciais.getProcessoSeletivo());
		this.dadosIniciais.setUnidade(dadosIniciais.getUnidade());
		
		mv.addObject("dadosIniciais", dadosIniciais);
		
		return mv;
	}
	
	@GetMapping("/total-por-unidade")
	public @ResponseBody List<UnidadeTotal> totalInscricoesPorUnidade(@AuthenticationPrincipal UsuarioSistema usuarioSistema){
		
		List<Unidade> unidades = unidadeRepository.unidadesPorUsuario(usuarioSistema.getUsuario().getId());
		
		return dadosIniciaisRepository.totalPorUnidade(unidades);
	}
	
}
