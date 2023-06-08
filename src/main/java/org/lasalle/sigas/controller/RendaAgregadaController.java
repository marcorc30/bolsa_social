package org.lasalle.sigas.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.RendaAgregada;
import org.lasalle.sigas.model.TipoRenda;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.RendaAgregadaRepository;
import org.lasalle.sigas.repository.TipoRendaRepository;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.lasalle.sigas.service.RendaAgregadaService;
import org.lasalle.sigas.service.exception.ImpossivelExcluirRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("rendas-agregadas")
public class RendaAgregadaController {
	
	@Resource(name = "dadosIniciais")
	private DadosIniciais dadosIniciais;
	
	@Autowired
	private TipoRendaRepository tipoRendaRepository;
	
	@Autowired
	private RendaAgregadaService rendaAgregadaService;
	
	@Autowired
	private RendaAgregadaRepository rendaAgregadaRepository;
	
	@Autowired
	private DadosInciaisRepository dadosIniciaisRepository;
	
	@Autowired
	private DadosIniciaisService dadosIniciaisService;
	
	@RequestMapping(value="novo", method = RequestMethod.GET)
	public ModelAndView novo(RendaAgregada rendaAgregada) {
		ModelAndView mv = new ModelAndView("informacaoFinanceira/Renda");
		
		DadosIniciais ds = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		if (!ds.getEtapa3() || 
				!ds.getEtapa4() || 
				!ds.getEtapa5() ||
				!ds.getEtapa6() ||
				!ds.getEtapa7() ||
				!ds.getEtapa8() ||
				!ds.getEtapa9() ||
				!ds.getEtapa10()) {
				return new ModelAndView("/EtapaInvalida");
			}		
		
		List<TipoRenda> tiposRenda = tipoRendaRepository.findAll();
		List<RendaAgregada> rendasAgregadas = rendaAgregadaRepository.findByDadosIniciaisId(this.dadosIniciais.getId());
		Double soma = rendasAgregadas.stream().mapToDouble(r -> r.getValor().doubleValue()).sum();
		
		
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());
		mv.addObject("tiposRenda", tiposRenda);
		mv.addObject("rendasAgregadas", rendasAgregadas);
		mv.addObject("soma", formatDecimal(soma.floatValue()));
		
		return mv;
	}
	
	@RequestMapping(value = {"novo", "{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid RendaAgregada rendaAgregada, BindingResult result, RedirectAttributes atrributes) {

		if (result.hasErrors()) {
			return novo(rendaAgregada);
		}
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa11(true);
		dadosIniciaisService.salvar(dadosIniciais);

		rendaAgregada.setDadosIniciais(dadosIniciais);
		
		rendaAgregadaService.salvar(rendaAgregada);
		
		
		return new ModelAndView("redirect:/rendas-agregadas/novo");
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") Long id) {
		
		ModelAndView mv = new ModelAndView("informacaoFinanceira/Renda");

		RendaAgregada rendaAgregada = rendaAgregadaRepository.findById(id);
		
		List<TipoRenda> tiposRenda = tipoRendaRepository.findAll();
		List<RendaAgregada> rendasAgregadas = rendaAgregadaRepository.findByDadosIniciaisId(this.dadosIniciais.getId());
		Double soma = rendasAgregadas.stream().mapToDouble(r -> r.getValor().doubleValue()).sum();
		
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());
		mv.addObject("tiposRenda", tiposRenda);
		mv.addObject("rendasAgregadas", rendasAgregadas);
		mv.addObject("soma", formatDecimal(soma.floatValue()));
		
		mv.addObject(rendaAgregada);
		
		if (!rendasAgregadas.contains(rendaAgregada)) {
			return new ModelAndView("AcessoIndevido");
		}
		
		
		return mv;
		
		
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Long id){
		
		try {
			rendaAgregadaService.excluir(id);
			
		} catch (ImpossivelExcluirRegistro e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();
	}
	
	public String formatDecimal(float number) {
		  float epsilon = 0.004f; // 4 tenths of a cent
		  if (Math.abs(Math.round(number) - number) < epsilon) {
		     return String.format("%10.0f", number); // sdb
		  } else {
		     return String.format("%10.2f", number); // dj_segfault
		  }
		}
	
	@RequestMapping("/direcionar")
	public ModelAndView direcionar() {
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa11(true);
		dadosIniciaisService.salvar(dadosIniciais);

		
		return new ModelAndView("redirect:/beneficios/novo");
		
	}
	
	

}
