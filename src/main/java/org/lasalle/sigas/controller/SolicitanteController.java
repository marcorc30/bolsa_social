package org.lasalle.sigas.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.lasalle.sigas.mail.Mailer;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.Grupo;
import org.lasalle.sigas.model.ProcessoSeletivo;
import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.model.Usuario;
import org.lasalle.sigas.model.filter.SolicitanteFilter;
import org.lasalle.sigas.repository.ProcessoSeletivoRepository;
import org.lasalle.sigas.repository.UnidadeRepository;
import org.lasalle.sigas.service.UsuarioService;
import org.lasalle.sigas.service.exception.CpfJaCadastradoException;
import org.lasalle.sigas.service.exception.EmailJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/solicitante")
//@SessionAttributes("dadosIniciaisSolicitante")
public class SolicitanteController {
	
	@Autowired
	Mailer mailer;
	
	@Resource(name = "dadosIniciais")
	private DadosIniciais dadosIniciais;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	private UnidadeRepository unidadeRepository;
	
	@Autowired
	private ProcessoSeletivoRepository processoSeletivoRepository;
	
	@RequestMapping("/acesso")
	public String escolherSolicitante() {
		return "/solicitante/Solicitante";
	}
	
	@RequestMapping("/alunoRede")
	public String alunoRede(SolicitanteFilter filter) {
		return "/solicitante/AlunoRede";
	}

	@RequestMapping("/alunoExterno")
	public ModelAndView alunoExterno(Usuario usuario) {
		return new ModelAndView("/solicitante/AlunoExterno");
	}
	
	@RequestMapping("/termosEdital")
	public String termosEdital() {
		return "/solicitante/TermosEdital";
	}
	

	
	@RequestMapping(value="/alunoExterno", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Usuario usuario, 
			BindingResult result, RedirectAttributes attributes ) {
		
		if (result.hasErrors()) {
			return alunoExterno(usuario);
		}
		
		String mensagem = "Usuario cadastrado com sucesso. "
				+ "Retorne para a tela principal e faça seu acesso.";
		
		
		try {
			
			Grupo grupo = new Grupo();
			grupo.setId(3l);
			List<Grupo> grupos = new ArrayList<>();
			grupos.add(grupo);
			
			usuario.setGrupos(grupos);
			usuario.setAtivo(true);
			mailer.enviarConfirmacao(usuario); 
			usuarioService.salvar(usuario);
			
		}catch(CpfJaCadastradoException e) {
			result.rejectValue("cpf", e.getMessage(), e.getMessage());
			return alunoExterno(usuario);
		}catch(EmailJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return alunoExterno(usuario);
		}
		
		attributes.addFlashAttribute("mensagem", mensagem);
		
		return new ModelAndView("redirect:/solicitante/alunoExterno");
	}
	

	
	
	
	@RequestMapping("/disparaMensagem")
	public ModelAndView mensagem(SolicitanteFilter filter, RedirectAttributes attributes) {
		
		String mensagem = "Usuario cadastrado. Retorne para a tela principal e faça seu acesso.";
		
		attributes.addFlashAttribute("solicitanteFilter", filter);
		attributes.addFlashAttribute("mensagemSucesso", mensagem);
		
		return new ModelAndView("redirect:/solicitante/alunoRede");
		
	}
	
	
	
//	@RequestMapping("/dadosIniciais")
//	public ModelAndView dadosIniciais(@ModelAttribute("dadosIniciaisSolicitante") DadosIniciais dadosIniciaisSolicitante) {
//		
//		ModelAndView mv = new ModelAndView("candidato/DadosIniciais");
//		List<Unidade> listaUnidades = unidadeRepository.findAll();
//		Set<String> ufs = new HashSet<>();
//		
//		for (Unidade unidade : listaUnidades) {
//			ufs.add(unidade.getUf());
//		}
//		
//		List<String> ufsString = new ArrayList<String>(ufs);
//		
//		Collections.sort(ufsString);
//		
////		mv.addObject("dadosIniciaisSolicitante", dadosIniciaisSolicitante);
//		mv.addObject("lista", ufsString);
//	//	mv.addObject("unidades", listaUnidades);
//	
//		return mv;
//	}
//
//	
//	@RequestMapping(value="/dadosIniciais", method = RequestMethod.POST)
//	public ModelAndView edital(@Valid @ModelAttribute("dadosIniciaisSolicitante") DadosIniciais dadosIniciaisSolicitante,
//									BindingResult result, RedirectAttributes attributes) {
//		
//		
//		if (result.hasErrors()) {
//			return dadosIniciais(dadosIniciaisSolicitante);
//		}
//		
//		//populando objeto dadosIniciais na sessão
////		dadosIniciais.setUf(dadosIniciaisSolicitante.getUf());
//		dadosIniciais.setDetalhesProcessoSeletivo(dadosIniciaisSolicitante.getDetalhesProcessoSeletivo());
//		dadosIniciais.setProcessoSeletivo(dadosIniciaisSolicitante.getProcessoSeletivo());
//		dadosIniciais.setUnidade(dadosIniciaisSolicitante.getUnidade());
//		
//		ProcessoSeletivo processoSeletivo = processoSeletivoRepository.findById(dadosIniciaisSolicitante.getProcessoSeletivo().getId());
//		attributes.addFlashAttribute("dadosIniciaisSolicitante", dadosIniciaisSolicitante);
//		attributes.addFlashAttribute("processoSeletivo", processoSeletivo);
//		ModelAndView mv = new ModelAndView("redirect:/edital");
//		return mv;
//	}	
 
	
//	@ModelAttribute("dadosIniciaisSolicitante")
//	public DadosIniciais dados() {
//		return new DadosIniciais();
//	}
	
}
