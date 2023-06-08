package org.lasalle.sigas.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.MoradiaImovel;
import org.lasalle.sigas.model.MoradiaResposta;
import org.lasalle.sigas.model.MoradiaTipo;
import org.lasalle.sigas.model.SituacaoHabitacional;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.MoradiaAreaRepository;
import org.lasalle.sigas.repository.MoradiaCondicaoRepository;
import org.lasalle.sigas.repository.MoradiaImovelRepository;
import org.lasalle.sigas.repository.MoradiaRespostaRepository;
import org.lasalle.sigas.repository.MoradiaTipoRepository;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.lasalle.sigas.service.SituacaoHabitacionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("situacao-habitacional")
public class SituacaoHabitacionalController {

	@Resource(name = "dadosIniciais")
	private DadosIniciais dadosIniciais;
	
	@Autowired
	private MoradiaCondicaoRepository moradiaCondicaoRepository;
	
	@Autowired
	private MoradiaAreaRepository moradiaAreaRepository;
	
	@Autowired
	private MoradiaTipoRepository moradiaTipoRepository;
	
	@Autowired
	private MoradiaRespostaRepository moradiaRespostaRepository;
	
	@Autowired
	private MoradiaImovelRepository moradiaImovelRepository;
	
	@Autowired
	private SituacaoHabitacionalService situacaoHabitacionalService;
	
	@Autowired
	private DadosInciaisRepository dadosIniciaisRepository;
	
	@Autowired
	private DadosIniciaisService dadosIniciaisService;
	
	@RequestMapping(value="/novo", method = RequestMethod.GET)
	public ModelAndView novo(SituacaoHabitacional situacaoHabitacional) {
		ModelAndView mv = new ModelAndView("situacaoHabitacional/SituacaoHabitacional");
		
		DadosIniciais ds = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		
		if (!ds.getEtapa3() || 
			!ds.getEtapa4() || 
			!ds.getEtapa5() ||
			!ds.getEtapa6() ||
			!ds.getEtapa7()) {
			return new ModelAndView("/EtapaInvalida");
		}
		
		
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());		
		mv.addObject("moradiasArea", moradiaAreaRepository.findAll());
		mv.addObject("moradiasCondicao", moradiaCondicaoRepository.findAll());
		mv.addObject("moradiasTipo", moradiaTipoRepository.findAll());
		mv.addObject("moradiasImovel", moradiaImovelRepository.findAll());
		mv.addObject("moradiasResposta", moradiaRespostaRepository.findAll());
		
		return mv;
	}
	
	@RequestMapping(value="/novo", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid SituacaoHabitacional situacaoHabitacional, 
						BindingResult result, RedirectAttributes attributes) {
		
		
		if (result.hasErrors()) {
			return novo(situacaoHabitacional);
		}
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa8(true);
		dadosIniciaisService.salvar(dadosIniciais);

		
		situacaoHabitacional.setDadosIniciais(dadosIniciais);
		
		situacaoHabitacionalService.salvar(situacaoHabitacional);
		
		return new ModelAndView("redirect:/bem-movel/novo");
	}
}
