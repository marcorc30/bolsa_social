package org.lasalle.sigas.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.lasalle.sigas.model.Beneficio;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.TipoProgramaGovernamental;
import org.lasalle.sigas.repository.BeneficioRepository;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.TipoProgramaGovernamentalRepository;
import org.lasalle.sigas.service.BeneficioService;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.lasalle.sigas.service.exception.ImpossivelExcluirRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("beneficios")
public class BeneficioController {
	
	@Resource(name = "dadosIniciais")
	private DadosIniciais dadosIniciais;
	
	@Autowired
	private BeneficioService beneficioService;
	
	@Autowired
	private BeneficioRepository beneficioRepository;
	
	@Autowired
	private DadosInciaisRepository dadosInciaisRepository;
	

	@Autowired
	private DadosIniciaisService dadosIniciaisService;
	
	@Autowired
	private TipoProgramaGovernamentalRepository tipoProgramaGovernamentalRepository;
	
	@RequestMapping(value="novo", method = RequestMethod.GET)
	public ModelAndView novo(Beneficio beneficio) {
		ModelAndView mv = new ModelAndView("informacaoFinanceira/Beneficio");
		
		DadosIniciais ds = dadosInciaisRepository.findById(this.dadosIniciais.getId());
		if (!ds.getEtapa3() || 
				!ds.getEtapa4() || 
				!ds.getEtapa5() ||
				!ds.getEtapa6() ||
				!ds.getEtapa7() ||
				!ds.getEtapa8() ||
				!ds.getEtapa9() ||
				!ds.getEtapa10()||
				!ds.getEtapa11()) {
				return new ModelAndView("/EtapaInvalida");
			}		
		
		
		List<TipoProgramaGovernamental> tiposProgramas = tipoProgramaGovernamentalRepository.findAll();
		List<Beneficio> beneficios = beneficioRepository.findByDadosIniciaisId(this.dadosIniciais.getId());
		Double soma = beneficios.stream().mapToDouble(b -> b.getValor().doubleValue()).sum();
		
		
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());		
		mv.addObject("tiposProgramas", tiposProgramas);
		mv.addObject("beneficios", beneficios);
		mv.addObject("soma", formatDecimal(soma.floatValue()));
		
		
		return mv;
	}
	
	@RequestMapping(value = {"novo", "{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Beneficio beneficio, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return novo(beneficio);
		}
		
		DadosIniciais dadosIniciais = dadosInciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa12(true);
		dadosIniciaisService.salvar(dadosIniciais);

		beneficio.setDadosIniciais(dadosIniciais);
		
		String mensagem = "Beneficio cadastrado com sucesso!";
		beneficioService.salvar(beneficio);
		attributes.addFlashAttribute("mensagem", mensagem);
		
		return new ModelAndView("redirect:/beneficios/novo");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("informacaoFinanceira/Beneficio");
		
		List<TipoProgramaGovernamental> tiposProgramas = tipoProgramaGovernamentalRepository.findAll();
		List<Beneficio> beneficios = beneficioRepository.findByDadosIniciaisId(this.dadosIniciais.getId());
		Double soma = beneficios.stream().mapToDouble(b -> b.getValor().doubleValue()).sum();
		
		Beneficio beneficio = beneficioRepository.findById(id);
		
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());		
		mv.addObject("tiposProgramas", tiposProgramas);
		mv.addObject("beneficios", beneficios);
		mv.addObject("soma", formatDecimal(soma.floatValue()));
		mv.addObject(beneficio);
		
		if (!beneficios.contains(beneficio)) {
			return new ModelAndView("AcessoIndevido");
		}
		
		
		return mv;
		
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> excluir(@PathVariable("id") Long id){
		
		try {
			beneficioService.excluir(id);
			
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
		
		DadosIniciais dadosIniciais = dadosInciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa12(true);
		dadosIniciaisService.salvar(dadosIniciais);

		
		return new ModelAndView("redirect:/declaracao-final/novo");
		
	}	

}
