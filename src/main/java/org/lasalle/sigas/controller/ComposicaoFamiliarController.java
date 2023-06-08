package org.lasalle.sigas.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.lasalle.sigas.model.Candidato;
import org.lasalle.sigas.model.ComposicaoFamiliar;
import org.lasalle.sigas.model.DadosIniciais;
import org.lasalle.sigas.model.Escolaridade;
import org.lasalle.sigas.model.EstadoCivil;
import org.lasalle.sigas.model.Parentesco;
import org.lasalle.sigas.model.Unidade;
import org.lasalle.sigas.repository.ComposicaoFamiliarRepository;
import org.lasalle.sigas.repository.DadosInciaisRepository;
import org.lasalle.sigas.repository.EscolaridadeRepository;
import org.lasalle.sigas.repository.EstadoCivilRepository;
import org.lasalle.sigas.repository.ParentescoRepository;
import org.lasalle.sigas.service.ComposicaoFamiliarService;
import org.lasalle.sigas.service.DadosIniciaisService;
import org.lasalle.sigas.service.exception.ImpossivelExcluirRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
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
@RequestMapping("/composicao-familiar")
public class ComposicaoFamiliarController {

	@Resource(name = "dadosIniciais")
	private DadosIniciais dadosIniciais;
	
	@Autowired
	private EscolaridadeRepository escolaridadeRepository;
	
	@Autowired
	private EstadoCivilRepository estadoCivilRepository;
	
	@Autowired
	private ParentescoRepository parentescoRepository;
	
	@Autowired
	private ComposicaoFamiliarService composicaoFamiliarService;
	
	@Autowired
	private ComposicaoFamiliarRepository composicaoFamiliarRepository;
	
	@Autowired
	private DadosInciaisRepository dadosIniciaisRepository;
	
	@Autowired
	DadosIniciaisService dadosIniciaisService;
	
	@RequestMapping(value="/novo", method = RequestMethod.GET)
	public ModelAndView novo(ComposicaoFamiliar composicaoFamiliar) {
		
		ModelAndView mv = new ModelAndView("composicaoFamiliar/ComposicaoFamiliar");
		
		DadosIniciais ds = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		
		if (!ds.getEtapa3() || 
			!ds.getEtapa4() || 
			!ds.getEtapa5()) {
			return new ModelAndView("/EtapaInvalida");
		}
		
		
		
		List<Escolaridade> escolaridades = escolaridadeRepository.findAll();
		List<EstadoCivil> estadosCivis = estadoCivilRepository.findAll();
		List<Parentesco> parentescos = parentescoRepository.findAll();
		List<ComposicaoFamiliar> composicoesFamiliares = composicaoFamiliarRepository.findByDadosIniciaisId(this.dadosIniciais.getId());

		boolean naoConferidoGeral = true;
		
		for (ComposicaoFamiliar composicao : composicoesFamiliares) {
			if (!composicao.getConferido()) {
				naoConferidoGeral = true;
				break;
			}else {
				naoConferidoGeral = false;
			}
			
			
		}

		Double soma = composicoesFamiliares.stream().mapToDouble(cf ->  cf.getSalario().doubleValue()).sum();
		Double media = composicoesFamiliares.stream().mapToDouble(cf -> cf.getSalario().doubleValue()).average().getAsDouble();
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());	
		mv.addObject("escolaridades", escolaridades);
		mv.addObject("estadosCivis", estadosCivis);
		mv.addObject("parentescos", parentescos);
		mv.addObject("composicoesFamiliares", composicoesFamiliares);
		mv.addObject("conferido", naoConferidoGeral);
		mv.addObject("soma", formatDecimal(soma.floatValue()));
		mv.addObject("media", formatDecimal(media.floatValue()));
		return mv;
		
	}
	
	
	@RequestMapping(value = {"/novo", "{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid ComposicaoFamiliar composicaoFamiliar, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return novo(composicaoFamiliar);
		}
		
		DadosIniciais dadosIniciais = dadosIniciaisRepository.findById(this.dadosIniciais.getId());
		dadosIniciais.setEtapa6(true);
		dadosIniciaisService.salvar(dadosIniciais);
		
		
		composicaoFamiliar.setDadosIniciais(dadosIniciais);
		
		composicaoFamiliar.setConferido(true);
		composicaoFamiliarService.salvar(composicaoFamiliar);
		
		String mensagem = "Componente familiar cadastrado com sucesso";
		attributes.addFlashAttribute("mensagem", mensagem);
		
		return new ModelAndView("redirect:/composicao-familiar/novo");
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("composicaoFamiliar/ComposicaoFamiliar");

		ComposicaoFamiliar composicaoFamiliar = composicaoFamiliarRepository.findById(id);
		
		List<Escolaridade> escolaridades = escolaridadeRepository.findAll();
		List<EstadoCivil> estadosCivis = estadoCivilRepository.findAll();
		List<Parentesco> parentescos = parentescoRepository.findAll();
		List<ComposicaoFamiliar> composicoesFamiliares = composicaoFamiliarRepository.findByDadosIniciaisId(this.dadosIniciais.getId());
		Double soma = composicoesFamiliares.stream().mapToDouble(cf ->  cf.getSalario().doubleValue()).sum();
		Double percapta = composicoesFamiliares.stream().mapToDouble(cf -> cf.getSalario().doubleValue()).average().getAsDouble();
		mv.addObject("protocolo", this.dadosIniciais.getProtocolo());
		mv.addObject("candidato", this.dadosIniciais.getCandidato());	
		mv.addObject("escolaridades", escolaridades);
		mv.addObject("estadosCivis", estadosCivis);
		mv.addObject("parentescos", parentescos);
		mv.addObject("composicoesFamiliares", composicoesFamiliares);
		mv.addObject("soma", formatDecimal(soma.floatValue()));
		mv.addObject("media", formatDecimal(percapta.floatValue()));
		mv.addObject(composicaoFamiliar);
		
		
		if (!composicoesFamiliares.contains(composicaoFamiliar)) {
			return new ModelAndView("AcessoIndevido");
		}

		
		return mv;

	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Long id){
		
		try {
			composicaoFamiliarService.excluir(id);
		} catch (ImpossivelExcluirRegistro e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();
		
	}
	
	
	@RequestMapping(method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> componentesPorNome(String nome){
		
		ComposicaoFamiliar cf  = composicaoFamiliarRepository.findByNomeAndDadosIniciaisId(nome, this.dadosIniciais.getId());
		
		ComposicaoFamiliar novocf = new ComposicaoFamiliar();
		novocf.setNome(cf.getNome());
		novocf.setDataNascimento(cf.getDataNascimento());
		novocf.setOcupacao(cf.getOcupacao());
		novocf.setEscolaridade(cf.getEscolaridade());
		novocf.setCpf(cf.getCpf());
		novocf.setEstadoCivil(cf.getEstadoCivil());
		novocf.setIdade(cf.getIdade());
		novocf.setParentesco(cf.getParentesco());

//		
//		System.out.println(cf.getNome()); 
//		System.out.println(cf.getIdade());
		
		return ResponseEntity.ok(novocf);
		
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
