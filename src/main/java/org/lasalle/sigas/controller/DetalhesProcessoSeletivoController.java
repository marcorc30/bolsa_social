package org.lasalle.sigas.controller;

import java.util.List;

import javax.validation.Valid;

import org.lasalle.sigas.dto.DetalhesProcessoSeletivoDTO;
import org.lasalle.sigas.model.DetalhesProcessoSeletivo;
import org.lasalle.sigas.model.ProcessoSeletivo;
import org.lasalle.sigas.model.Serie;
import org.lasalle.sigas.repository.DetalhesProcessoSeletivoRepository;
import org.lasalle.sigas.repository.MotivoAlteracaoRepository;
import org.lasalle.sigas.repository.ProcessoSeletivoRepository;
import org.lasalle.sigas.repository.SerieRepository;
import org.lasalle.sigas.repository.TurnoRepository;
import org.lasalle.sigas.service.DetalhesProcessoSeletivoService;
import org.lasalle.sigas.service.UnidadeService;
import org.lasalle.sigas.service.exception.MotivoAlteracaoNaoInformadaException;
import org.lasalle.sigas.service.exception.SerieJaCadastradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@RequestMapping("/detalhesProcessos")
public class DetalhesProcessoSeletivoController {
	
	@Autowired
	SerieRepository serieRepository;
	
	@Autowired
	UnidadeService unidadeService;
	
	@Autowired
	DetalhesProcessoSeletivoRepository detalhesProcessoSeletivoRepository;
	
	@Autowired
	DetalhesProcessoSeletivoService detalhesProcessoSeletivoService;
	
	@Autowired
	ProcessoSeletivoRepository processoSeletivoRepository;
	
	@Autowired
	TurnoRepository turnoRepository;
	
	@Autowired
	MotivoAlteracaoRepository motivoAlteracaoRepository;
	
//	@RequestMapping("/novo")
//	public ModelAndView novo(DetalhesProcessoSeletivo detalhesProcessoSeletivo) {
//		ModelAndView mv = new ModelAndView("/processoSeletivo/NovoDetalhesProcesso");
//						
//		mv.addObject("lista", serieRepository.findAll());
//		mv.addObject("ufs", unidadeService.listarUfs());
//		
//		return mv;
//		
//	}
	
	
	@RequestMapping("/novo/processo/{idProcesso}")
	public ModelAndView novo(DetalhesProcessoSeletivo detalhesProcessoSeletivo, 
					@PathVariable("idProcesso") Long idProcesso) {
		ModelAndView mv = new ModelAndView("/processoSeletivo/NovoDetalhesProcesso");
		ProcessoSeletivo processoSeletivo = processoSeletivoRepository.findById(idProcesso);
		detalhesProcessoSeletivo.setProcessoSeletivo(processoSeletivo);

		
		
//		List<DetalhesProcessoSeletivo> lista = 
//				detalhesProcessoSeletivoRepository.findByProcessoSeletivoIdOrderBySerieDescricao(idProcesso);
		
		
		List<DetalhesProcessoSeletivoDTO> lista = detalhesProcessoSeletivoRepository.listarDetalhes(idProcesso);
		
		mv.addObject("series", lista);
		
		//listar as series das unidades
		//mv.addObject("lista", serieRepository.findAll());
		List<Serie> listaDeSeries = serieRepository.seriesPorUnidade(processoSeletivo.getUnidade().getId());
		mv.addObject("lista", listaDeSeries);
		
		mv.addObject("listaTurnos", turnoRepository.findAll());
		mv.addObject("processoSeletivoId", processoSeletivo);
		mv.addObject("motivosAlteracao", motivoAlteracaoRepository.findAll());
		
		return mv;
		
	}


	@RequestMapping(value = { "/novo", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid DetalhesProcessoSeletivo detalhesProcessoSeletivo,
			BindingResult result, RedirectAttributes attributes) {
		
		
		
		
		Long idProcessoSeletivo = detalhesProcessoSeletivo.getProcessoSeletivo().getId();
		
		if (result.hasErrors()) {
			return novo(detalhesProcessoSeletivo,idProcessoSeletivo);
		}
		
		try {
			detalhesProcessoSeletivoService.salvar(detalhesProcessoSeletivo);
			
		}catch (SerieJaCadastradaException e) {
			
			result.rejectValue("id", e.getMessage(), e.getMessage());
		//	result.rejectValue("serie.descricao", e.getMessage(), e.getMessage());
			return novo(detalhesProcessoSeletivo, idProcessoSeletivo);
		}catch (MotivoAlteracaoNaoInformadaException e) {
			System.out.println("Entrou no tratamento de excecao dentro do controller");
			result.rejectValue("motivoAlteracao", e.getMessage(), e.getMessage());
			return novo(detalhesProcessoSeletivo, idProcessoSeletivo);
		}
		
		List<DetalhesProcessoSeletivo> lista = 
				detalhesProcessoSeletivoRepository.findByProcessoSeletivoId(detalhesProcessoSeletivo.getProcessoSeletivo().getId());
		
		String mensagem = "Série do Processo Seletivo cadastrado com sucesso";
		attributes.addFlashAttribute("mensagem", mensagem);
		attributes.addFlashAttribute("series", lista);
		
		return new ModelAndView("redirect:/detalhesProcessos/novo/processo/" + idProcessoSeletivo);
		
	}
	
	
	@RequestMapping(method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> detalhesProcessoPorProcessoSeletivo(Long processoId) {

		List<DetalhesProcessoSeletivo> detalhesProcesso = detalhesProcessoSeletivoRepository.findByProcessoSeletivoIdOrderBySerieDescricao(processoId);
		
		return ResponseEntity.ok(detalhesProcesso); 
		
	}	
	
	@RequestMapping("/{id}/processo/{idProcesso}")
	public ModelAndView editar(@PathVariable("id") Long id, @PathVariable("idProcesso") Long idProcesso) {
		
		ModelAndView mv = new ModelAndView("/processoSeletivo/NovoDetalhesProcesso");
		ProcessoSeletivo processoSeletivo = processoSeletivoRepository.findById(idProcesso);
		DetalhesProcessoSeletivo detalhesProcessoSeletivo = detalhesProcessoSeletivoRepository.findById(id);
		detalhesProcessoSeletivo.setProcessoSeletivo(processoSeletivo);
		
		
//		List<DetalhesProcessoSeletivo> lista = 
//				detalhesProcessoSeletivoRepository.findByProcessoSeletivoIdOrderBySerieDescricao(idProcesso);
						
		List<DetalhesProcessoSeletivoDTO> lista = detalhesProcessoSeletivoRepository.listarDetalhes(idProcesso);
		
		mv.addObject("series", lista);
		mv.addObject("lista", serieRepository.findAll());
		mv.addObject("listaTurnos", turnoRepository.findAll());
		mv.addObject("processoSeletivoId", processoSeletivo);
		mv.addObject("motivosAlteracao", motivoAlteracaoRepository.findAll());
		mv.addObject(detalhesProcessoSeletivo);
		
		return mv;
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Long id){
		
		detalhesProcessoSeletivoService.excluir(id);
		
		
		return ResponseEntity.ok().build();
		
	}
	

	
	
}
