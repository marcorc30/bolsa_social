package org.lasalle.sigas.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.lasalle.sigas.model.ComposicaoFamiliar;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.Documento;
import org.lasalle.sigas.model.Nacionalidade;
import org.lasalle.sigas.model.ResponsavelFinanceiro;
import org.lasalle.sigas.repository.ComposicaoFamiliarRepository;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.DocumentoRepository;
import org.lasalle.sigas.repository.NacionalidadeRepository;
import org.lasalle.sigas.repository.ResponsavelFinanceiroRepository;
import org.lasalle.sigas.repository.UnidadeFederacaoRepository;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.lasalle.sigas.service.ResponsavelFinanceiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/responsavel-financeiro")
public class ResponsavelFinanceiroController {
	
	@Resource(name = "dadosIniciais")
	private DadosIniciais dadosIniciais;
	
	@Autowired
	private NacionalidadeRepository nacionalidadeRepository;
	
	@Autowired
	private ComposicaoFamiliarRepository composicaoFamiliarRepository;
	
	@Autowired
	private DadosInciaisRepository dadosIniciaisRepository;
	
	@Autowired
	private ResponsavelFinanceiroService responsavelFinanceiroService;
	
	@Autowired
	private ResponsavelFinanceiroRepository responsavelFinanceiroRepository;

	@Autowired
	private DadosIniciaisService dadosIniciaisService;
	
	@Autowired
	private UnidadeFederacaoRepository unidadeFederacaoRepository;
	
	
	
	@RequestMapping(value="novo", method = RequestMethod.GET)
	public ModelAndView novo(ResponsavelFinanceiro responsavelFinanceiro) {
		ModelAndView mv = new ModelAndView("/responsavelFinanceiro/ResponsavelFinanceiro");
		
		DadosIniciais ds = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		
		if (!ds.getEtapa3() || 
			!ds.getEtapa4() || 
			!ds.getEtapa5() ||
			!ds.getEtapa6()) {
			return new ModelAndView("/EtapaInvalida");
		}
		
		

		List<ComposicaoFamiliar> componentesFamiliares = composicaoFamiliarRepository.componentesAcimaDe18anos(this.dadosIniciais.getId());

		List<String> nomes = new ArrayList<String>();
		componentesFamiliares.stream().forEach(c -> nomes.add(c.getNome()));
		
		
		
		List<Nacionalidade> nacionalidades = nacionalidadeRepository.findAll();
		
		
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());
		mv.addObject("nomes", nomes);
		mv.addObject("nacionalidades", nacionalidades);
		mv.addObject("ufs", unidadeFederacaoRepository.findAll());
		
		return mv;
	}
	@RequestMapping(value="novo", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid ResponsavelFinanceiro responsavelFinanceiro, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return novo(responsavelFinanceiro);
		}
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa7(true);
		dadosIniciaisService.salvar(dadosIniciais);

		responsavelFinanceiro.setDadosIniciais(dadosIniciais);
		responsavelFinanceiroService.salvar(responsavelFinanceiro);
		
		
		return new ModelAndView("redirect:/situacao-habitacional/novo");
	}
	
	
	
}
