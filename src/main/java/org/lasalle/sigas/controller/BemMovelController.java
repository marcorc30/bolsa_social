package org.lasalle.sigas.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.lasalle.sigas.model.BemMovel;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.repository.BemMovelRepository;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.service.BemMovelService;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.lasalle.sigas.service.exception.ImpossivelExcluirDetalheProcessoSeletivoException;
import org.lasalle.sigas.service.exception.ImpossivelExcluirRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/bem-movel")
public class BemMovelController {
	
	@Resource(name = "dadosIniciais")
	private DadosIniciais dadosIniciais;
	
	@Autowired
	private BemMovelRepository bemMovelRepository;
	
	@Autowired
	private BemMovelService bemMovelService;
	
	@Autowired
	private DadosInciaisRepository dadosIniciaisRepository;
	
	@Autowired
	private DadosIniciaisService dadosIniciaisService;
	
	@RequestMapping(value="novo", method = RequestMethod.GET)
	public ModelAndView novo(BemMovel bemMovel) {
		ModelAndView mv = new ModelAndView("bemMovel/BemMovel");
		
		DadosIniciais ds = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		if (!ds.getEtapa3() || 
				!ds.getEtapa4() || 
				!ds.getEtapa5() ||
				!ds.getEtapa6() ||
				!ds.getEtapa7() ||
				!ds.getEtapa8()) {
				return new ModelAndView("/EtapaInvalida");
			}
		
		
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());		
		
		List<BemMovel> bensMoveis = bemMovelRepository.findByDadosIniciaisId(this.dadosIniciais.getId()); 
		
		mv.addObject("bensMoveis", bensMoveis);
		
		return mv;
		
	}

	@RequestMapping(value = {"/novo", "{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid BemMovel bemMovel, BindingResult result, RedirectAttributes attributes) {
		
		
		if(result.hasErrors()) {
			return novo(bemMovel);
		}
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa9(true);
		dadosIniciaisService.salvar(dadosIniciais);

		
		
		bemMovel.setDadosIniciais(dadosIniciais);
		
		bemMovelService.salvar(bemMovel);
		
		String mensagem = "Bem movel cadastrado com sucesso";
		attributes.addFlashAttribute("mensagem", mensagem);
		
		return new ModelAndView("redirect:/bem-movel/novo");
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") Long id) {
		
		ModelAndView mv = new ModelAndView("bemMovel/BemMovel");
		
		BemMovel bemMovel = bemMovelRepository.findById(id);
		
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());		
		
		List<BemMovel> bensMoveis = bemMovelRepository.findByDadosIniciaisId(this.dadosIniciais.getId()); 
		
		mv.addObject("bensMoveis", bensMoveis);
		mv.addObject(bemMovel);
		
		if (!bensMoveis.contains(bemMovel)) {
			return new ModelAndView("AcessoIndevido");
		}
		
		
		return mv;
		
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Long id){
		
		try {
			
			bemMovelService.excluir(id);
			
		} catch (ImpossivelExcluirRegistro e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();
		
	}
	
	@RequestMapping("/direcionar")
	public ModelAndView direcionar() {
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa9(true);
		dadosIniciaisService.salvar(dadosIniciais);

		
		return new ModelAndView("redirect:/despesas/novo");
		
	}

}
