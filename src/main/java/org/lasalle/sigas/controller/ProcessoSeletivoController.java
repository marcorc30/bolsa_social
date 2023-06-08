package org.lasalle.sigas.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.lasalle.sigas.controller.page.PageWrapper;
import org.lasalle.sigas.dto.SolicitacoesConsultaDTO;
import org.lasalle.sigas.model.Grupo;
import org.lasalle.sigas.model.ProcessoSeletivo;
import org.lasalle.sigas.model.StatusSolicitacao;
import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.model.Usuario;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.FuncionarioRepository;
import org.lasalle.sigas.repository.GrupoRepository;
import org.lasalle.sigas.repository.NumeroEditalRepository;
import org.lasalle.sigas.repository.ProcessoSeletivoRepository;
import org.lasalle.sigas.repository.TipoEditalRepository;
import org.lasalle.sigas.repository.TipoProcessoSeletivoRepository;
import org.lasalle.sigas.repository.UnidadeRepository;
import org.lasalle.sigas.repository.UsuarioRepository;
import org.lasalle.sigas.repository.filter.ProcessoSeletivoFilter;
import org.lasalle.sigas.repository.filter.RelatorioDeferidoIndeferidoFilter;
import org.lasalle.sigas.repository.filter.SolicitacaoFilter;
import org.lasalle.sigas.security.UsuarioSistema;
import org.lasalle.sigas.service.ProcessoSeletivoService;
import org.lasalle.sigas.service.UnidadeService;
import org.lasalle.sigas.service.exception.DatasIncompativeisException;
import org.lasalle.sigas.service.exception.ImpossivelExcluirProcessoSeletivoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/processos")
public class ProcessoSeletivoController {
	
	@Autowired
	UnidadeService unidadeService;
	
	
	@Autowired
	ProcessoSeletivoService processoSeletivoService;
	
	@Autowired
	ProcessoSeletivoRepository processoSeletivoRepository;
	
	@Autowired
	UnidadeRepository unidadeRepository;
	
	
	@Autowired
	DadosInciaisRepository dadosIniciaisRepository;
	
	@Autowired
	TipoProcessoSeletivoRepository tipoProcessoSeletivoRepository;
	
	@Autowired
	TipoEditalRepository tipoEditalRepository;
	
	@Autowired
	NumeroEditalRepository numeroEditalRepository;
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	GrupoRepository grupoRepository;

	
	
	
	@RequestMapping("/novo")
	public ModelAndView novoProcesso(ProcessoSeletivo processoSeletivo) {
		
		ModelAndView mv = new ModelAndView("/processoSeletivo/NovoProcesso");
//		mv.addObject("tipos", TipoProcesso.values());
		mv.addObject("lista", unidadeService.listarUfs());
		mv.addObject("unidades", unidadeRepository.findAll());
		mv.addObject("tiposProcesso", tipoProcessoSeletivoRepository.findAll());
		mv.addObject("tiposEdital", tipoEditalRepository.findAll());
		mv.addObject("numerosEdital", numeroEditalRepository.findAll());
		mv.addObject("funcionarios", funcionarioRepository.funcionariosAssistente());
		
		return mv;
	}
	
	
	
	@RequestMapping(value= {"/novo", "{\\d+}"}, method = RequestMethod.POST)
	@CacheEvict("unidades")
	public ModelAndView salvar(@Valid ProcessoSeletivo processoSeletivo, BindingResult result, 
								RedirectAttributes attributes) {
		
		
		String mensagem = null;
		
		
		if (result.hasErrors()) {
			return novoProcesso(processoSeletivo);
		}
		
		try {
			
			processoSeletivoService.salvar(processoSeletivo);
		}catch (DatasIncompativeisException e) {
			result.rejectValue("id", e.getMessage(), e.getMessage());
			return novoProcesso(processoSeletivo);
		}
		
		
		mensagem = "Processo Seletivo cadastrado com sucesso";
		attributes.addFlashAttribute("mensagem", mensagem);
//		attributes.addFlashAttribute(processoSeletivo);

		return new ModelAndView("redirect:/processos/novo");
		
	}
	

	
	@RequestMapping(value="edital", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> processosPorTipoEdital(Long idUnidade){
		
		List<ProcessoSeletivo> processos = processoSeletivoRepository.processosPorTipoEditalAnoUnidade(idUnidade);
				
		return ResponseEntity.ok(processos);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> consultarProcessoSeletivo(Long unidadeId){
		
		List<ProcessoSeletivo> processos = new ArrayList<ProcessoSeletivo>();
		
		
		if (unidadeId != null) {
			
//			processos = processoSeletivoRepository.findByUnidadeId(unidadeId);
			processos = processoSeletivoRepository.processosPorUnidadeData(unidadeId);
			
			
//			processos = processoSeletivoService.listarPorCodigoUnidade(unidadeId);
		}
		
		
	
		
		return ResponseEntity.ok(processos);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView pesquisa(ProcessoSeletivoFilter processoSeletivoFilter, 
						BindingResult result, @PageableDefault(size = 10) Pageable pageable, 
						HttpServletRequest request, @AuthenticationPrincipal UsuarioSistema usuarioSistema ) {
		
		ModelAndView mv = new ModelAndView("/processoSeletivo/PesquisarProcessoSeletivo");
		
		List<Unidade> unidades = unidadeRepository.unidadesPorUsuario(usuarioSistema.getUsuario().getId());
		
		if (unidades.isEmpty()) {
			return new ModelAndView("redirect:/warning"); 
		}
		
		mv.addObject("tipos", tipoProcessoSeletivoRepository.findAll());
		mv.addObject("unidades", unidades);
		
		PageWrapper<ProcessoSeletivo> pagina = 
				new PageWrapper<ProcessoSeletivo>(processoSeletivoRepository.filtrar(processoSeletivoFilter, pageable, unidades), request);
		
		
		List<ProcessoSeletivo> processos = pagina.getConteudo().stream().collect(Collectors.toList());

		
		mv.addObject("pagina", pagina);
		
		return mv;
				
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("/processoSeletivo/NovoProcesso");
		
		ProcessoSeletivo processoSeletivo = processoSeletivoRepository.findById(id);
		
		mv.addObject("tiposProcesso", tipoProcessoSeletivoRepository.findAll());
		mv.addObject("lista", unidadeService.listarUfs());
		mv.addObject("unidades", unidadeRepository.findAll());
		mv.addObject("tiposEdital", tipoEditalRepository.findAll());
		mv.addObject("numerosEdital", numeroEditalRepository.findAll());
		mv.addObject("funcionarios", funcionarioRepository.funcionariosAssistente());
		

		List<ProcessoSeletivo> processos = processoSeletivoRepository.processosPorTipoEditalAnoUnidade(processoSeletivo.getUnidade().getId());
		mv.addObject("processos", processos);
		
		mv.addObject(processoSeletivo);
		
		
		return mv;
		
	}
	
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Long id){
		
		try {
			processoSeletivoService.excluir(id);
			
		}catch(ImpossivelExcluirProcessoSeletivoException e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		
		return ResponseEntity.ok().build();
		
	}
	
	@RequestMapping(value = "/avaliar/{id}")
	public ModelAndView avaliarProcessos(@PathVariable("id") Long id, SolicitacaoFilter solicitacaoFilter, @AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		ModelAndView mv = new ModelAndView("processoSeletivo/InscritosProcessoSeletivo");
		
		Usuario usuario = usuarioRepository.buscarUsuarioComGrupos(usuarioSistema.getUsuario().getId());
		
		Boolean ehComissao = usuario.getGrupos().contains(grupoRepository.findById(4l));
		
		List<SolicitacoesConsultaDTO> solicitacoes = dadosIniciaisRepository
				.solicitacaoPorProcessoSeletivoConsultaNativaPorStatus(id, solicitacaoFilter.getStatus(), 
						ehComissao, solicitacaoFilter.getNome(), solicitacaoFilter.getProtocolo(), solicitacaoFilter.getOrdenarPor());
		ProcessoSeletivo processo = processoSeletivoRepository.findById(id);
		
		mv.addObject("solicitacoes", solicitacoes);
		mv.addObject("processo", processo);
		mv.addObject("listaStatus", StatusSolicitacao.values());
		
		
		return mv;
	}
	
	
	@RequestMapping("/imprimir-deferido-indeferido")
	public ModelAndView imprimirRelatorioDeferidoIndeferido(RelatorioDeferidoIndeferidoFilter relatorioDeferidoIndeferidoFilter, 
													@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		List<Unidade> unidades = unidadeRepository.unidadesPorUsuario(usuarioSistema.getUsuario().getId());
		List<ProcessoSeletivo> processosSeletivos = processoSeletivoRepository.processosPorUnidades(unidades);
		
		ModelAndView mv = new ModelAndView("/processoSeletivo/ImpressaoDeferidosIndeferidos");
		mv.addObject("processos", processosSeletivos);
//		mv.addObject("situacoes", Arrays.asList("Deferido", "Indeferido"));
		
		return mv;
	}
	

	@RequestMapping("/imprimir-lista-publicacao")
	public ModelAndView imprimirRelatorioListaPublicacao(RelatorioDeferidoIndeferidoFilter relatorioDeferidoIndeferidoFilter, 
													@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		List<Unidade> unidades = unidadeRepository.unidadesPorUsuario(usuarioSistema.getUsuario().getId());
		List<ProcessoSeletivo> processosSeletivos = processoSeletivoRepository.processosPorUnidades(unidades);
		
		ModelAndView mv = new ModelAndView("/processoSeletivo/ImpressaoListaPublicacao");
		mv.addObject("processos", processosSeletivos);
//		mv.addObject("situacoes", Arrays.asList("Deferido", "Indeferido"));
		
		return mv;
	}	
	
	
	
	
	@RequestMapping("/imprimir-inscritos")
	public ModelAndView imprimirRelatorioDeInscritos(RelatorioDeferidoIndeferidoFilter relatorioDeferidoIndeferidoFilter, 
													@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		List<Unidade> unidades = unidadeRepository.unidadesPorUsuario(usuarioSistema.getUsuario().getId());
		List<ProcessoSeletivo> processosSeletivos = processoSeletivoRepository.processosPorUnidades(unidades);
		
		ModelAndView mv = new ModelAndView("/processoSeletivo/ImpressaoDeInscritos");
		mv.addObject("processos", processosSeletivos);
		
		return mv;
	}
	
	@RequestMapping("/imprimir-acompanhamento-consolidado")
	public ModelAndView imprimirAcompanhamentoConsolidado(RelatorioDeferidoIndeferidoFilter relatorioDeferidoIndeferidoFilter, 
													@AuthenticationPrincipal UsuarioSistema usuarioSistema) {
		
		List<Unidade> unidades = unidadeRepository.unidadesPorUsuario(usuarioSistema.getUsuario().getId());
		List<ProcessoSeletivo> processosSeletivos = processoSeletivoRepository.processosPorUnidades(unidades);
		
		ModelAndView mv = new ModelAndView("/processoSeletivo/ImpressaoDeAcompanhamentoConsolidado");
		mv.addObject("processos", processosSeletivos);
		
		return mv;
	}
	
	
	
//	@RequestMapping(value = "/filtrar/{id}")
//	public ModelAndView filtrarProcessos(@PathVariable("id") Long id, SolicitacaoFilter solicitacaoFilter) {
//		
//		ModelAndView mv = new ModelAndView("processoSeletivo/InscritosProcessoSeletivo");
//		ProcessoSeletivo processo = processoSeletivoRepository.findById(solicitacaoFilter.getProcessoId());
//	
//		List<SolicitacoesConsultaDTO> solicitacoes = dadosIniciaisRepository
//				.solicitacaoPorProcessoSeletivoConsultaNativaPorStatus(
//						2013l, 
//						solicitacaoFilter.getStatus());
//		
//		System.out.println(solicitacaoFilter.getProcessoId());
//		
////		List<SolicitacoesConsultaDTO> solicitacoes = dadosIniciaisRepository.solicitacaoPorProcessoSeletivoConsultaNativa(solicitacaoFilter.getProcessoId());
//		
//		
//		mv.addObject("solicitacoes", solicitacoes);
//		mv.addObject("processo", processo);
//		mv.addObject("listaStatus", StatusSolicitacao.values());
//
//		return mv;
//
//		
//	}
//	

}
