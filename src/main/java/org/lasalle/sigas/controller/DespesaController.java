package org.lasalle.sigas.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.Despesa;
import org.lasalle.sigas.model.TipoDespesa;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.DespesaRepository;
import org.lasalle.sigas.repository.TipoDespesaRepository;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.lasalle.sigas.service.DespesaService;
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
@RequestMapping("despesas")
public class DespesaController {
	
	@Resource(name = "dadosIniciais")
	private DadosIniciais dadosIniciais;
	
	@Autowired
	private TipoDespesaRepository tipoDespesaRepository;
	
	@Autowired
	private DespesaService despesaService;
	
	@Autowired
	private DespesaRepository despesaRepository;
	
	@Autowired
	private DadosInciaisRepository dadosIniciaisRepository;
	
	@Autowired
	private DadosIniciaisService dadosIniciaisService;
	
	@RequestMapping(value="novo", method = RequestMethod.GET)
	public ModelAndView novo(Despesa despesa) {
		ModelAndView mv = new ModelAndView("informacaoFinanceira/Despesa");
		
		DadosIniciais ds = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		if (!ds.getEtapa3() || 
				!ds.getEtapa4() || 
				!ds.getEtapa5() ||
				!ds.getEtapa6() ||
				!ds.getEtapa7() ||
				!ds.getEtapa8() ||
				!ds.getEtapa9()) {
				return new ModelAndView("/EtapaInvalida");
			}
		
		
		List<TipoDespesa> tiposDespesa = tipoDespesaRepository.findAll();
		List<Despesa> despesas = despesaRepository.findByDadosIniciaisId(this.dadosIniciais.getId());
		Double soma = despesas.stream().mapToDouble(d -> d.getValor().doubleValue()).sum();
		
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());	
		mv.addObject("tiposDespesa", tiposDespesa);
		mv.addObject("despesas", despesas);
		mv.addObject("soma", formatDecimal(soma.floatValue()));
		
		
		return mv;
	}
	
	@RequestMapping(value={"novo", "{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Despesa despesa, BindingResult result, RedirectAttributes attributes) {
	
		if (result.hasErrors()) {
			return novo(despesa);
		}
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa10(true);
		dadosIniciaisService.salvar(dadosIniciais);

		
		despesa.setDadosIniciais(dadosIniciais);
		despesaService.salvar(despesa);
		
		
		String mensagem = "Despesa cadastrada com sucesso";
		attributes.addFlashAttribute("mensagem", mensagem);
		
		return new ModelAndView("redirect:/despesas/novo");
	}

	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") Long id) {
		
		ModelAndView mv = new ModelAndView("informacaoFinanceira/Despesa");
		
		List<TipoDespesa> tiposDespesa = tipoDespesaRepository.findAll();
		List<Despesa> despesas = despesaRepository.findByDadosIniciaisId(this.dadosIniciais.getId());
		Double soma = despesas.stream().mapToDouble(d -> d.getValor().doubleValue()).sum();
		
		Despesa despesa = despesaRepository.findById(id);
		
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());	
		mv.addObject("tiposDespesa", tiposDespesa);
		mv.addObject("despesas", despesas);
		mv.addObject("soma", formatDecimal(soma.floatValue()));
		mv.addObject(despesa);
		
		if (!despesas.contains(despesa)) {
			return new ModelAndView("AcessoIndevido");
		}
		
		
		return mv;
		
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> excluir(@PathVariable("id") Long id){
		
		try {
			despesaService.excluir(id);
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

}
