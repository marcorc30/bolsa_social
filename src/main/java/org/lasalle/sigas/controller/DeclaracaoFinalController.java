package org.lasalle.sigas.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.DeclaracaoFinal;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.IrRespostaRepository;
import org.lasalle.sigas.repository.ResponsavelFinanceiroRepository;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.lasalle.sigas.service.DeclaracaoFinalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("declaracao-final")
public class DeclaracaoFinalController {
	
	@Resource(name = "dadosIniciais")
	private DadosIniciais dadosIniciais;
	
	@Autowired
	private IrRespostaRepository irRespostaRepository;
	
	@Autowired
	private ResponsavelFinanceiroRepository responsavelFinanceiroRepository;
	
	@Autowired
	private DeclaracaoFinalService declaracaoFinalService;
	
	@Autowired
	private DadosInciaisRepository dadosIniciaisRepository;
	
	@Autowired
	private DadosIniciaisService dadosIniciaisService;
	
	@RequestMapping(value = "/novo", method = RequestMethod.GET)
	public ModelAndView novo(DeclaracaoFinal declaracaoFinal) {
		ModelAndView mv = new ModelAndView("informacaoFinanceira/DeclaracaoFinal");
		
		DadosIniciais ds = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		if (!ds.getEtapa3() || 
				!ds.getEtapa4() || 
				!ds.getEtapa5() ||
				!ds.getEtapa6() ||
				!ds.getEtapa7() ||
				!ds.getEtapa8() ||
				!ds.getEtapa9() ||
				!ds.getEtapa10()||
				!ds.getEtapa11()||
				!ds.getEtapa12()) {
				return new ModelAndView("/EtapaInvalida");
			}		
				
		
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());		
		mv.addObject("respostas", irRespostaRepository.findAll());
		mv.addObject("responsavel_financeiro", responsavelFinanceiroRepository.findByDadosIniciaisId(this.dadosIniciais.getId()));
		
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid DeclaracaoFinal declaracaoFinal, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return novo(declaracaoFinal);
		}
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa13(true);
		
		declaracaoFinal.setDadosIniciais(dadosIniciais);
		
		dadosIniciais.setConcluido(true);
		dadosIniciaisService.salvar(dadosIniciais);
		
		
		
		
		declaracaoFinalService.salvar(declaracaoFinal);
		


		
		
		
		
		return new ModelAndView("redirect:/documentos-comuns/novo");
	}

}
