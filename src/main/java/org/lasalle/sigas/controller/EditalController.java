package org.lasalle.sigas.controller;

import javax.annotation.Resource;

import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("edital")
public class EditalController {
	
	
	@Resource(name = "dadosIniciais")
	private DadosIniciais dadosIniciais;
	
	@Autowired
	private DadosIniciaisService dadosIniciaisService;
	
	@Autowired
	private DadosInciaisRepository dadosIniciaisRepository;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView edital() {
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa2(true);
		dadosIniciaisService.salvar(dadosIniciais);
		
		
		return new ModelAndView("solicitacao/Edital");
	}
	
//	@RequestMapping(method = RequestMethod.POST)
//	public ModelAndView enviar(@ModelAttribute("dadosIniciaisSolicitante") DadosIniciais dadosIniciaisSolicitante,
//								RedirectAttributes attributes) {
//		
//		dadosIniciaisRepository.save(dadosIniciaisSolicitante);
//		
//		attributes.addFlashAttribute("dadosIniciaisSolicitante", dadosIniciaisSolicitante);
//		return new ModelAndView("redirect:/candidatos/novo");
//	}
	
	
}
